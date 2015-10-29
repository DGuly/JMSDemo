package com.tenaciousd.jmsdemo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by dmytryguly on 10/29/15.
 */
public class P2PMessageReceiver implements Runnable, ExceptionListener {


    public ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost:7677");
        return factory;
    }

    public void initializeReceiver() throws JMSException {

        ConnectionFactory connectionFactory = getConnectionFactory();
        Connection connection = connectionFactory.createConnection("admin", "admin");
        connection.start();

        connection.setExceptionListener(this);

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("TestQueue");

        // Create a MessageConsumer from the Session to the Topic or Queue
        MessageConsumer consumer = session.createConsumer(destination);

        // Wait for a message
        System.out.println("Waiting for messages");
        Message message = consumer.receive(1000);


    }

    public void onException(JMSException exception) {
        System.out.println("JMS Exception occured.  Shutting down client.");
    }

    public void run() {
        try {
            initializeReceiver();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
