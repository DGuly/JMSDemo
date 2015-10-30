package com.tenaciousd.jmsdemo;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Producer main.
 */
public class Main {

  public static void main(String[] args) throws InterruptedException, JMSException {
    try (Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection()) {
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createQueue("test-queue");
      MessageProducer producer = session.createProducer(destination);
      producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
      int number = 1;
      while (true) {
        producer.send(session.createTextMessage("Test message number: " + number++));
        Thread.sleep(500);
      }
    } catch (Exception e) {
      System.out.println("Failed to send message: " + e.getMessage());
    }
  }
}
