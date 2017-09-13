package com.citi.training.spring.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * Created by sxj on 2016/8/20.
 */
public class DownloadUtil {

    //文件存储在应用上下文里
    public static boolean download(String filename, HttpServletRequest request, HttpServletResponse response){
        try {
            String serverPath = request.getServletContext().getRealPath("/");
            InputStream in = new FileInputStream(serverPath +"/" + filename);
            response.reset();
            String suffix = filename.substring(filename.lastIndexOf(".")+1);
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            response.addHeader("Cache-Control", "no-cache");
            response.setContentType(getContentType(suffix));

            ServletOutputStream out = response.getOutputStream();
            byte[] buffer = new byte[10 * 1024];
            int nbytes = 0;
            while ((nbytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, nbytes);
            }
            out.flush();
            out.close();
            in.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public static String getContentType(String suffix){
        if(suffix != null) {
            suffix = suffix.toLowerCase();
            if (suffix.equals("xls") || suffix.equals("xlsx")) {
                return "application/vnd.ms-excel";
            }
            if (suffix.equals("doc") || suffix.equals("docx")) {
                return "application/msword";
            }
            if (suffix.equals("txt")) {
                return "text/plain";
            }
            if (suffix.equals("pdf")) {
                return "application/pdf";
            }
            if (suffix.equals("jpg") || suffix.equals("jpeg")) {
                return "image/jpeg";
            }
            if (suffix.equals("ppt") || suffix.equals("pptx")) {
                return "application/vnd.ms-powerpoint";
            }
            if (suffix.equals("gif")) {
                return "image/gif";
            }
        }
        return "text/html";
    }

}
