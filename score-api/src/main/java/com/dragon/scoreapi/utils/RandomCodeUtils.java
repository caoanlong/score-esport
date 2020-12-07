package com.dragon.scoreapi.utils;

import java.util.Random;

public class RandomCodeUtils {
    public static String create(Integer num) {
        String code = "";
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            int r = random.nextInt(10); // 每次随机出一个数字（0-9）
            code = code + r; // 把每次随机出的数字拼在一起
        }
        return code;
    }
}
