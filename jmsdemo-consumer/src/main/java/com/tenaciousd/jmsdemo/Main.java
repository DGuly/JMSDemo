package com.tenaciousd.jmsdemo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Consumer main.
 */
public class Main implements ExceptionListener {

    private static final String QUEUE = "test-queue";

    public static void main(String[] args) throws JMSException {
        new Main().consume();
    }

    public void consume() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.2.117:61616");

        Session session = null;
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            connection.setExceptionListener(this);

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(QUEUE);

            MessageConsumer consumer = session.createConsumer(destination);

            System.out.println("Waiting for messages");
            while (true) {
                Message message = consumer.receive(1000);
                if (message != null) {
                    System.out.println(message);
                    TextMessage transformedMessage = (TextMessage)message;
                    System.out.println(transformedMessage.getText());
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occured while trying to get message from " + QUEUE);
        } finally {
            session.close();
            connection.close();

        }
    }

    public void onException(JMSException exception) {
        System.out.println("JMS Exception occurred. Shutting down client.");
    }
}
