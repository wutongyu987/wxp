package com.wxp.common.util;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

public class ExcelUtil {

    public static void generate(String template, String outFile, Map<String,Object> model) throws IOException {
        Context context = new Context(model);
        InputStream is =  ExcelUtil.class.getResourceAsStream(template);
        OutputStream os = new FileOutputStream(outFile);
//        JxlsHelper.getInstance().processTemplate(is, os, context);
        JxlsHelper.getInstance().processTemplateAtCell(is, os, context,"兑奖记录!A1");
    }

    public static void generate(String template, OutputStream os, Map<String,Object> model) throws IOException {
        Context context = new Context(model);
        InputStream is =  ExcelUtil.class.getResourceAsStream(template);
        JxlsHelper.getInstance().processTemplateAtCell(is, os, context,"兑奖记录!A1");
    }

//    public static void generate2(String template, String outFile, List list) throws IOException {
//        InputStream is =  ExcelUtil.class.getResourceAsStream(template);
//        OutputStream os = new FileOutputStream(outFile);
//        Context context = PoiTransformer.createInitialContext();
//        context.putVar("employees", list);
//        context.putVar("sheetNames", Arrays.asList("sheet1","sheet2","sheet3"));
//        // with multi sheets it is better to use StandardFormulaProcessor by disabling the FastFormulaProcessor
//        JxlsHelper.getInstance().setUseFastFormulaProcessor(false).processTemplate(is, os, context);
//    }

    public static void initDownResponse(String fileName, HttpServletResponse response) {
        try {
            response.reset();
            // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        response.addHeader("Content-Length", "" + length);
        response.setContentType("application/octet-stream");
    }

}
