package com.citi.training.spring.util;
/**
 * Created by sxj on 2017/2/28.
 */

import org.apache.activemq.ActiveMQConnectionFactory;


import javax.jms.*;

public class JMSUtils {
    private static String url = "tcp://localhost:61616";
    private static ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
        } catch (JMSException e) {
            connection = null;
        }
        return connection;
    }

    public static void closeConnection(Connection connection){
        if(connection != null){
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
