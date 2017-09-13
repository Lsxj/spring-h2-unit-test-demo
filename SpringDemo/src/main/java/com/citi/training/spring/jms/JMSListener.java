package com.citi.training.spring.jms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by sxj on 2017/2/28.
 */
public class JMSListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage msg = (TextMessage)message;
        try {
            System.out.println("received message: ["+msg.getText() + "]");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("jms.xml");

        ((AbstractApplicationContext) ctx).registerShutdownHook();
    }
}
