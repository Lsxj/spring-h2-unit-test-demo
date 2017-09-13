package com.citi.training.spring.servlet;

import com.citi.training.spring.dao.BowlingDao;
import com.citi.training.spring.dao.BowlingDaoImpl;
import com.citi.training.spring.entity.Bowling;
import com.citi.training.spring.entity.BowlingImpl;
import com.citi.training.spring.util.CalculatorImpl;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by sxj on 2017/2/27.
 */
public class BowlingController extends BasicServlet {
    private static final long serialVersionUID = 1L;
    private static Connection connection;

    public BowlingController() {
        super();

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);


        if(request.getParameter("calculateButton") != null) {
            getScore(request, response);
        } else if(request.getParameter("saveButton") != null) {
            try {
                DataSource ds = InitialContext.doLookup("java:comp/env/jdbc/mydatasource");

                if(ds != null) {
                    try (Connection connection = ds.getConnection()){
                        System.out.println("Go connection: " + connection);
                        saveBowling(request, response, connection);
                    } catch (SQLException e) {
                        System.out.println("failed to get connection...");
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println("connection closed");
                }
                else {
                    System.out.println("fail to retrieve datasource from JNDI...");
                }
            } catch (NamingException e) {
                e.printStackTrace();
            }

        }
    }

    public void getScore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("in BowlingServlet do post");
        String address = "error.jsp";
        if (request.getParameter("pins") != null) {
            try {
                Bowling bowling = initBowling(request.getParameter("pins"));
                int score = CalculatorImpl.getInstance().calculateTotalScore(bowling);
                request.setAttribute("score", score);
                request.setAttribute("data", request.getParameter("pins"));
                address = "showResult.jsp";
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    private Bowling initBowling(String data) throws NumberFormatException{
        String[] pinstr = data.split(",");
        int[] pins = new int[pinstr.length];

        for (int i = 0; i < pinstr.length; i++) {
            pins[i] = Integer.valueOf(pinstr[i]);
        }

        Bowling bowling = new BowlingImpl(pins.length);
        bowling.addPins(pins);

        return bowling;
    }

    public void saveBowling(HttpServletRequest request, HttpServletResponse response, Connection connection) throws Exception {
        Bowling bowling = initBowling(request.getParameter("pins"));
        String address = "error.jsp";
        try {
            BowlingDao bowlingDao = new BowlingDaoImpl(connection);
            int result = bowlingDao.save(bowling);
            if(result > 0) //save success, return index.jsp
                address="index.jsp";
        }catch (Exception e){
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);

    }

}
