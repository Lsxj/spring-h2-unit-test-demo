package com.citi.training.spring.emailer;

import com.citi.training.spring.rmi.RemoteEmailer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import java.rmi.RemoteException;

/**
 * Created by sxj on 2017/3/1.
 */
public class Emailer implements RemoteEmailer{
    private SpellChecker spellChecker;

    private Topic topic;
    private JmsTemplate jmsTemplate;

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public SpellChecker getSpellChecker() {
        return spellChecker;
    }

    public void setSpellChecker(SpellChecker spellChecker) {
        this.spellChecker = spellChecker;
    }

    private void sendToTopic(String msg) {
        jmsTemplate.send(topic, new MessageCreator() {
            public Message createMessage(Session session)
                    throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }

    public void sendMessage(String msg) throws RemoteException {
        this.spellChecker.check();
        System.out.println("sending message : [" + msg + "]");
        sendToTopic(msg);
    }

    @Override
    public void sendRemoteMessage(String msg) throws RemoteException {
        this.spellChecker.check();
        System.out.println("sending remote message : [" + msg + "]");
        sendToTopic(msg);
    }

//
//    private void sendToTopic(String text) {
//        Connection connection = JMSUtils.getConnection();
//
//        if (connection != null) {
//            System.out.println("connected to ActiveMQ...");
//            try {
//                connection.start();
//                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//                Destination dest = session.createTopic("email");
//                MessageProducer producer = session.createProducer(dest);
//
//                Message msg = session.createTextMessage(text);
//
//                producer.send(msg);
//                System.out.println("message sent to topic!");
//                producer.close();
//                session.close();
//
//            } catch (JMSException e) {
//                e.printStackTrace();
//            }
//        }
//        JMSUtils.closeConnection(connection);
//    }
}
