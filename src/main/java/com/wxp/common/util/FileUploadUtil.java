package com.wxp.common.util;


import org.apache.shiro.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FileUploadUtil {
    private static final List<String> ALLOW_TYPES = Arrays.asList("image/jpg", "image/jpeg", "image/png", "image/gif", "image/*");


    /**
     * 图片上传
     *
     * @param file      图片文件
     * @param targetDir 图片存储的目标路径
     * @return 返回图片在线访问地址，如果为空则上传失败
     */
    public static String upload(MultipartFile file, String targetDir) {
        String imgUrl = StringUtils.EMPTY_STRING;
        if (file != null) {
            String myFileName = file.getOriginalFilename();
            if (!Objects.equals(myFileName.trim(), "")) {
                String fileName = rename(myFileName);
                String path = targetDir + "/" + fileName;
                File localFile = new File(path);
                try {
                    file.transferTo(localFile);
                    saveSpic(fileName);
                    imgUrl = buildUrl(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imgUrl;
    }

    /**
     * 构建返回的图片路径
     * @param fileName
     * @return
     */
    private static String buildUrl(String fileName) {
//        return MyConfig.SERVER + MyConfig.RESOURCE_DIR + fileName;
        return fileName;
    }

    private static String rename(String fileName) {
        int i = fileName.lastIndexOf(".");
        String str = fileName.substring(i);
        return System.currentTimeMillis() / 1000 + "" + new Random().nextInt(99999999) + str;
    }

    /**
     * 校验文件类型是否是被允许的
     *
     * @param contentType 文件类型
     * @return true：允许上传，false：不允许上传
     */
    public static boolean allowUpload(String contentType) {
        return ALLOW_TYPES.contains(contentType);
    }

    /**
     * 保存缩略图。加前缀为s_
     */
    public static void saveSpic(String fileName){
//        SimpleImageUtils.scaleNormal(MyConfig.UPLOAD_DIR+fileName, MyConfig.UPLOAD_DIR+"s_"+fileName, 80, 80);
    }
}
