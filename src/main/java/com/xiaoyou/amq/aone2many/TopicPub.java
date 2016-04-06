package com.xiaoyou.amq.aone2many;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

import static com.xiaoyou.amq.conf.AmqTestConf.*;

/**
 * Created by qiyubin on 2016/4/6 0006.
 */
public class TopicPub {

    public static void main(String[] args) throws Exception {
        int messages = 10000;
        TopicConnectionFactory factory = new ActiveMQConnectionFactory(user, password, BROKER_URL);
        Connection connection = factory.createConnection(user, password);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(topicName);
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        for (int i = 1; i <= messages; i++) {
            TextMessage msg = session.createTextMessage("#:" + i);
            msg.setIntProperty("id", i);
            producer.send(msg);
            if ((i % 1000) == 0) {
                System.out.println(String.format("Sent %d messages", i));
            }
        }
    }

}
