package com.dragon.scoreapi.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传工具包
 */
public class FileUtils {

    /**
     * @param file     文件
     * @param path     文件存放路径
     * @param fileName 源文件名
     * @return
     */
    public static void upload(MultipartFile file, String path, String fileName) throws IOException {
        /*
        // 生成新的文件名
        String newFileName = FileNameUtils.getFileName(fileName);
        String realPath = path + "/" + newFileName;
        */

        //使用原文件名
        String realPath = path + "/" + fileName;

        File dest = new File(realPath);

        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        if ("thumbnail.png".equals(fileName)) {
            Thumbnails.of(dest).scale(0.02).toFile(path + "/" + "thumbnail_s.png");
            Thumbnails.of(dest).scale(0.15).toFile(path + "/" + "thumbnail_m.png");
        }
//        // 保存文件
//        try {
//            file.transferTo(dest);
//            return fileName;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    public static boolean delDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = delDir(new File(dir, children[i]));
                if (!success) return false;
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
