package com.citi.training.spring.servlet;

import com.citi.training.spring.util.DownloadUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sxj on 2017/3/1.
 */
public class EmailController extends BasicServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);


        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

//        EmailerClient client = wac.getBean(EmailerClient.class);
//        String message = request.getParameter("msg");
//        client.run(message);

//        TestDao testDao = wac.getBean(TestDao.class);
//        testDao.selectAllCount();

        boolean result = DownloadUtil.download(request.getParameter("fileName"), request, response);
        System.out.println("download: [" + result + "]");

        String address = "showResult.jsp";
//        request.setAttribute("msg",message);

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);


    }
}
