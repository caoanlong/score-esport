package com.dragon.scorejob.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dragon.scoreapi.model.TeamTournament;
import com.dragon.scoreapi.model.Tournament;
import com.dragon.scoreapi.service.TournamentService;
import com.dragon.scoreapi.utils.ScriptMirrorToObj;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TournamentSchedule {

    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    private TournamentService tournamentService;

    private final OkHttpClient client = new OkHttpClient();

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

    private void getInfo(String gameType, String id) throws IOException, ScriptException {
        log.debug("gameType: {}, id: {}", gameType, id);
        Request.Builder reqBuilder = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://www.shangniu.cn/esports/" + gameType + "-match-" + id + ".html")
                .newBuilder();
        reqBuilder.url(urlBuilder.build());
        reqBuilder.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        reqBuilder.addHeader("Host", "www.shangniu.cn");
        reqBuilder.addHeader("Referer", "https://www.shangniu.cn/match");
        Request request = reqBuilder.build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response.message());
        String html = response.body().string();
        Document document = Jsoup.parse(html);
        Element nextData = document.getElementById("__nuxt").nextElementSibling();
        String scriptStr = nextData.data();
        String script = scriptStr.replace("window.", "var ");
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine js = sem.getEngineByName("js");
        js.eval(script);
        ScriptObjectMirror nuxt__ = (ScriptObjectMirror) js.get("__NUXT__");
        if (null != nuxt__) {
            Object nuxt = ScriptMirrorToObj.convertIntoJavaObject(nuxt__);
            String json = JSONObject.toJSONString(nuxt);
            JSONObject jsonObject = JSONObject.parseObject(json);
            JSONObject data = (JSONObject) jsonObject.getJSONArray("data").get(0);
            JSONArray leagueTeamList = data.getJSONArray("leagueTeamList");

            List<TeamTournament> teamTournaments = new ArrayList<>();
            for (int i = 0; i < leagueTeamList.size(); i++) {
                TeamTournament teamTournament = leagueTeamList.getObject(i, TeamTournament.class);
                teamTournaments.add(teamTournament);
            }
            tournamentService.insertTeamTournament(teamTournaments);
            log.debug("teamTournaments: 完成");
        }
    }

    /**
     * 每1小时分钟执行一次，初始启动延时20秒执行
     */
    @Scheduled(fixedRate = 3600000, initialDelay = 20000)
    public void saveList() throws IOException, InterruptedException {
        if ("dev".equals(env)) return;
        String[] gameTypes = {"lol", "dota", "kog", "csgo"};
        for (int i = 0; i < gameTypes.length; i++) {
            Thread.sleep(5000);
            getList(gameTypes[i]);
        }
    }

    /**
     * 每12小时分钟执行一次，初始启动延时3秒执行
     */
    @Scheduled(fixedRate = 43200000, initialDelay = 3000)
    public void saveInfo() throws InterruptedException {
        if ("dev".equals(env)) return;
        List<Tournament> matches = tournamentService.findAll();
        for (int i = 0; i < matches.size(); i++) {
            Thread.sleep(3000);
            Tournament tournament = matches.get(i);
            new Thread(() -> {
                try {
                    getInfo(tournament.getGameType(), tournament.getTournamentId());
                } catch (IOException | ScriptException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
