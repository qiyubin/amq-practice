package com.xiaoyou.amq.listener;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by qiyubin on 2016/4/6 0006.
 */
public class TopicSubscriber {

    public static void main(String[] args) throws JMSException {
        String user = "admin";
        String password = "test";
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(user, password, "tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("yubin.messages");
        Destination queue = session.createQueue("aGroup");
        MessageConsumer consumer = session.createConsumer(topic);
        final AtomicInteger atomicInteger = new AtomicInteger();

        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                atomicInteger.incrementAndGet();
                TextMessage tm = (TextMessage) message;
                try {
                    System.out.println("Received message: " + atomicInteger.get() + " time - " + tm.getText());
                    if (((TextMessage) message).getText().equals("SHUTDOWN")) {
                        atomicInteger.set(0);
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
//      session.close();
//      connection.stop();
//      connection.close();
    }

}
