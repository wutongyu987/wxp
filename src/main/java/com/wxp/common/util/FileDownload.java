package com.wxp.common.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created on 2017/8/2.
 */

public class FileDownload {


    public static void download(HttpServletResponse response, String fileAbsPath){
        try {
            File file = new File(fileAbsPath);// path是根据日志路径和文件名拼接出来的
            String filename = file.getName();// 获取日志文件名称
            InputStream fis = new BufferedInputStream(new FileInputStream(fileAbsPath));
            byte[] buffer = new byte[fis.available()];

            fis.read(buffer);
            fis.close();
            response.reset();
            // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            os.write(buffer);// 输出文件
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
