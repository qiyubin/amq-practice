package com.xiaoyou.amq.aone2many;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.xiaoyou.amq.conf.AmqTestConf.*;

/**
 * Created by qiyubin on 2016/4/6 0006.
 */
public class QueueSub {


    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(user, password, BROKER_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(topicName);
        MessageConsumer consumer = session.createConsumer(destination);
        System.out.println("Received message begin: ");

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

    }
}
