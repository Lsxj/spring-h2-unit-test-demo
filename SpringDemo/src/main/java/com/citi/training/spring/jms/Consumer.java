package com.citi.training.spring.jms;

import com.citi.training.spring.util.JMSUtils;

import javax.jms.*;

/**
 * Created by sxj on 2017/2/28.
 */
public class Consumer {

    public static void main(String[] args) {
        Connection connection = JMSUtils.getConnection();

        if (connection != null) {
            System.out.println("connected to ActiveMQ...");
            try {
                connection.start();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                Destination dest = session.createTopic("email");
                MessageConsumer consumer = session.createConsumer(dest);

                System.out.println("receiving message from ActiveMQ...");

                TextMessage msg = (TextMessage)consumer.receive();

                System.out.println(msg.getText());

                consumer.close();
                session.close();

            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

        JMSUtils.closeConnection(connection);
    }
}
