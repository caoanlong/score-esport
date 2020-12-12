package com.dragon.scorejob.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dragon.scoreapi.model.Team;
import com.dragon.scoreapi.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Slf4j
@Component
public class TeamSchedule {

    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    private TeamService teamService;

    private final OkHttpClient client = new OkHttpClient();

    private void getList(String gameType) throws IOException {
        Request.Builder reqBuilder = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://www.shangniu.cn/api/game/user/team/getTeamPageList").newBuilder();
        urlBuilder.addQueryParameter("pageIndex", "1");
        urlBuilder.addQueryParameter("pageSize", "10000");
        urlBuilder.addQueryParameter("gameType", gameType);
        reqBuilder.url(urlBuilder.build());
        reqBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
        reqBuilder.addHeader("Host", "www.shangniu.cn");
        reqBuilder.addHeader("Referer", "https://www.shangniu.cn/match");
        Request request = reqBuilder.build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response.message());
        String res = response.body().string();
        JSONObject json = (JSONObject) JSONObject.parse(res);
        Integer code = json.getInteger("code");
        if (code == 200) {
            JSONObject body = json.getJSONObject("body");
            if (null != body) {
                JSONArray rows = body.getJSONArray("rows");
                if (null != rows && rows.size() > 0) {
                    for (Object row: rows) {
                        JSONObject data = (JSONObject) row;
                        String teamId = data.getString("teamId");
                        String teamName = data.getString("teamName");
                        String teamLogo = data.getString("teamLogo");
                        Team team = new Team();
                        team.setId(teamId);
                        team.setFullName(teamName);
                        team.setNameZh(teamName);
                        team.setNameEn(teamName);
                        team.setShortName(teamName);
                        team.setLogo(teamLogo);
                        teamService.save(team);
                    }
                }
                log.info("{} Team 保存完成！", gameType);
            }
        } else {
            log.error("请求失败: {}", json.getString("message"));
        }
    }

    /**
     * 每2小时分钟执行一次，初始启动延时40秒执行
     */
    @Scheduled(fixedRate = 7200000, initialDelay = 40000)
    public void saveList() throws IOException, InterruptedException {
        if ("dev".equals(env)) return;
        String[] gameTypes = {"lol", "dota", "kog", "csgo"};
        for (int i = 0; i < gameTypes.length; i++) {
            Thread.sleep(10000);
            getList(gameTypes[i]);
        }
    }
}
