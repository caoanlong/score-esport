package com.dragon.scorejob.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dragon.scoreapi.model.Match;
import com.dragon.scoreapi.model.Team;
import com.dragon.scoreapi.model.Tournament;
import com.dragon.scoreapi.service.MatchService;
import com.dragon.scoreapi.service.TournamentService;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class MatchSchedule {

    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    private MatchService matchService;

    @Autowired
    private TournamentService tournamentService;

    private final OkHttpClient client = new OkHttpClient();
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private void getList(String time) throws IOException {
        Request.Builder reqBuilder = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://m.shangniu.cn/api/game/user/app/index/matchList").newBuilder();
        urlBuilder.addQueryParameter("gameTypes", "lol,dota,kog,csgo,ow");
        urlBuilder.addQueryParameter("getType", "3");
        urlBuilder.addQueryParameter("time", time);
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
                String string = body.getString("dayMatches");
                List<Match> matches = JSONArray.parseArray(string, Match.class);
                for (Match match: matches) {
                    matchService.save(match);
                    Tournament tournament = new Tournament();
                    tournament.setTournamentId(match.getTournamentId());
                    tournament.setTournamentNameEn(match.getTournamentNameEn());
                    tournament.setTournamentShortName(match.getTournamentShortName());
                    tournamentService.update(tournament);
                }
                log.debug("{} Match 保存完成！", time);
            }
        } else {
            log.error("请求失败: {}", json.getString("message"));
        }
    }


    /**
     * 每5分钟执行一次
     */
    @Scheduled(fixedRate = 300000)
    public void saveList() throws IOException, InterruptedException {
        if ("dev".equals(env)) return;
        Date today = new Date();
        List<String> dates = new ArrayList<>();
        for (int i = 5; i > 0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.add(Calendar.DAY_OF_MONTH, -1 * i);
            Date date = calendar.getTime();
            dates.add(format.format(date));
        }
        dates.add(format.format(today));
        for (int i = 0; i < 5; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.add(Calendar.DAY_OF_MONTH, i + 1);
            Date date = calendar.getTime();
            dates.add(format.format(date));
        }
        for (String date: dates) {
            Thread.sleep(5000);
            getList(date);
        }
    }
}
