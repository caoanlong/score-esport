package com.dragon.scorejob.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dragon.scoreapi.model.Match;
import com.dragon.scoreapi.model.Tournament;
import com.dragon.scoreapi.service.MatchService;
import com.dragon.scoreapi.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class TournamentSchedule {

    @Autowired
    private TournamentService tournamentService;

    private final OkHttpClient client = new OkHttpClient();
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private void getList(String gameType) throws IOException {
        Request.Builder reqBuilder = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://www.shangniu.cn/api/game/user/tournament/getTournamentVoPage").newBuilder();
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
                String string = body.getString("rows");
                List<Tournament> tournaments = JSONArray.parseArray(string, Tournament.class);
                for (Tournament tournament: tournaments) {
                    tournament.setGameType(gameType);
                    tournamentService.save(tournament);
                }
                log.info("{} Tournament 保存完成！", gameType);
            }
        } else {
            log.error("请求失败: {}", json.getString("message"));
        }
    }

    /**
     * 每1小时分钟执行一次，初始启动延时20秒执行
     */
    @Scheduled(fixedRate = 3600000, initialDelay = 20000)
    public void save() throws IOException {

        String[] gameTypes = {"lol", "dota", "kog", "csgo"};
        for (int i = 0; i < gameTypes.length; i++) {
            getList(gameTypes[i]);
        }
    }
}
