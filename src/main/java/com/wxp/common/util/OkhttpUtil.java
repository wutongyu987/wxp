package com.wxp.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2017/11/13 0013.
 * http请求
 */

public class OkhttpUtil {

    private static OkHttpClient okHttpClient;

    static {
        init();
    }

    /**
     * http get请求
     * @param url
     * @return
     */
    public static String get(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        return sendRequest(request);
    }

    public static String post(String url, Map<String,String> map){
        FormBody.Builder builder = new FormBody.Builder();
        Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> entry = iterator.next();
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
       return sendRequest(request);
    }


    /**
     * http post 请求下载文件
     * @param url
     * @param json
     * @param downPath
     * @return
     */
    public static String postJson(String url, JSONObject json,String downPath){
        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"),json.toJSONString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return downFile(request,downPath);
    }


    /**
     * 发送json数据
     * @param url
     * @param jsonStr
     * @return
     */
    public static String postJson(String url, String jsonStr){
        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"),jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return sendRequest(request);
    }


    /**
     * 具体发送请求，获取返回结果的方法
     * @param request
     * @return
     */
    private static String sendRequest(Request request){
        Call call = okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String  downFile(Request request,String path){
        Call call = okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            InputStream im= response.body().byteStream();
            OutputStream out = new FileOutputStream(path);
            byte[] buff = new byte[1024];
            int len=0;
            while ((len=im.read(buff))>0){
                out.write(buff,0,len);
            }
            out.close();
            im.close();
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "exception";
        }
    }

    //上传图片
    public static String okHttpUpload(String partName, String path,String UPLOAD_URL,String type){
        String MULTIPART_FORM_DATA = "image/jpg";//上传的文件类型
        if(type!=null){
            MULTIPART_FORM_DATA = "image/"+type;
        }
        System.out.println("云平台路径"+UPLOAD_URL+"文件类型"+MULTIPART_FORM_DATA);
        String result=null;
        File file = new File(path);             // 需要上传的文件
        RequestBody requestFile =               // 根据文件格式封装文件
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        // 初始化请求体对象，设置Content-Type以及文件数据流
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)            // multipart/form-data
                .addFormDataPart(partName, file.getName(), requestFile)
                .build();
        // 封装OkHttp请求对象，初始化请求参数
        Request request = new Request.Builder()
                .url(UPLOAD_URL)                // 上传url地址
                .post(requestBody)              // post请求体
                .build();
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            result=response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
//        String s = "D:\\tomcat.8.5.8\\apache-tomcat-8.5.8\\webapps\\news_upload\\19513844-b314-4762-b478-f275a865532e.jpg";
//        String imgUrl = "Y:\\web_upload\\1496395806529.jpg";//图片原地址
//        System.out.println(get("http://wwww.baidu.com"));
//            String imgUrl =  img;//图片原地址
//        String imageHost = MyConfig.IMG_UPLOAD_HOST_PATH+MyConfig.IMG_UPLOAD_INTERFACE;
//        String str = okHttpUpload("file",imgUrl,imageHost,"jpg");
//        System.out.println(str);

        String jsonstring = "{\n" +
                "    \"scene\":\"1234\",\n" +
                "    \"page\":\"pages/index/index\",\n" +
                "    \"width\":440,\n" +
                "    \"auto_color\":false,\n" +
                "    \"line_color\":{\"r\":\"0\",\"g\":\"0\",\"b\":\"0\"},\n" +
                "    \"is_hyaline\":false\n" +
                "}";
        JSONObject jsonObject = JSON.parseObject(jsonstring);
        String url= "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=12_DNj-Q_qQyFi2VCmOe1rqn0FMMGL06RGUmmhhpB6gqi-JL9VI5TYwYxySM0KlzfqM2vaXfSTj_odFIy-Dl8gBDGx5QJ6rdW3qmEmzpJU7gfaKHiUUu0eN_cj9cOCwvrmQm-GfIjfAlKQGRyvrBMPcAJAWHK";
        String str = postJson(url,jsonObject,"E:/3.jpeg");
        System.out.println(str);
    }


    private static void init(){
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        okHttpClient  = httpBuilder
                .connectTimeout(800, TimeUnit.SECONDS)          // 设置请求超时时间
                .readTimeout(800, TimeUnit.SECONDS)    //读取超时时间
                .build();
    }
}
