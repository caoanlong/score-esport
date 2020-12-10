package com.dragon.scorejob.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dragon.scoreapi.model.Match;
import com.dragon.scoreapi.service.MatchService;
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
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class MatchSchedule {

    @Autowired
    private MatchService matchService;

    private final OkHttpClient client = new OkHttpClient();

    /**
     * 每2小时执行一次
     */
    @Scheduled(fixedRate = 7200000)
    public void saveMatches() throws IOException {
        Request.Builder reqBuilder = new Request.Builder();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://m.shangniu.cn/api/game/user/app/index/matchList").newBuilder();
        urlBuilder.addQueryParameter("gameTypes", "lol,dota,kog,csgo,ow");
        urlBuilder.addQueryParameter("getType", "3");
        urlBuilder.addQueryParameter("time", format.format(new Date()));
        reqBuilder.url(urlBuilder.build());
        reqBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");

        Request request = reqBuilder.build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response.message());
        String res = response.body().string();
        JSONObject json = (JSONObject) JSONObject.parse(res);
        Integer code = json.getInteger("code");
        if (code == 200) {
            String string = json.getJSONObject("body").getString("dayMatches");
            List<Match> matches = JSONArray.parseArray(string, Match.class);
            for (Match match: matches) {
                matchService.save(match);
            }
            log.info("Match 保存完成！");
        } else {
            log.error("请求失败: {}", json.getString("message"));
        }
    }
}
