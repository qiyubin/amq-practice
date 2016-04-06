package com.xiaoyou.amq.publisher;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;
import org.apache.qpid.amqp_1_0.jms.impl.TopicImpl;

import javax.jms.*;

/**
 * Created by qiyubin on 2016/4/6 0006.
 */
public class TopicPublisher {
    //openwire
    public static final String BROKER_URL = "tcp://localhost:61616";

    public static void main(String[] args) throws Exception {

        String user = "admin";
//        env("ACTIVEMQ_USER", "admin");
        String password = "test";
//        env("ACTIVEMQ_PASSWORD", "password");
        String destination = arg(args, 0, "topic://event");

        int messages = 10000;
        int size = 256;


        TopicConnectionFactory factory = new ActiveMQConnectionFactory(user, password, BROKER_URL);


        Connection connection = factory.createConnection(user, password);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建话题
        Topic topic = session.createTopic("yubin.messages");
        MessageProducer producer = session.createProducer(topic);
//        MessageConsumer consumer = session.createDurableSubscriber(topic,"bbb"); //持久订阅

        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        for (int i = 1; i <= messages; i++) {
            TextMessage msg = session.createTextMessage("#:" + i);
            msg.setIntProperty("id", i);
            producer.send(msg);
            if ((i % 1000) == 0) {
                System.out.println(String.format("Sent %d messages", i));
            }
        }

        producer.send(session.createTextMessage("SHUTDOWN"));
        Thread.sleep(1000 * 3);
        connection.close();
        System.exit(0);
    }

    private static String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if (rc == null)
            return defaultValue;
        return rc;
    }

    private static String arg(String[] args, int index, String defaultValue) {
        if (index < args.length)
            return args[index];
        else
            return defaultValue;
    }


}
