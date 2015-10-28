package consumer;

import javax.jms.*;


public class P2PMessageReceiver implements MessageListener{

	Session session=null;
	ConnectionFactory factory;
	QueueConnection connection=null;
	MessageConsumer consumer=null;
	
	P2PMessageReceiver(){

	
	}
	
 public static void main(String[] args){
	  new P2PMessageReceiver();
	}	

 public void onMessage(Message msg){
	 String msgText;
	 try{
	     if (msg instanceof TextMessage){
	             msgText = ((TextMessage) msg).getText();
	            System.out.println("Got from the queue: " + msgText);
	     }else{
	        System.out.println("Got a non-text message");
	     }
	 }
	 catch (JMSException e){
	      System.out.println("Error while consuming a message: " + e.getMessage());
	 } 
 }

 
}
