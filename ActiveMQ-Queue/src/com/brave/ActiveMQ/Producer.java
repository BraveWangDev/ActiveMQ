package com.brave.ActiveMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息生产者 
 * @author Brave
 *
 */
public class Producer {
	
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	public static void main(String[] args) {
		
		ConnectionFactory connectionFactory;	//连接工厂
		Connection connection = null;// 连接
		Session session; //会话
		Destination destination; //消息地址
		MessageProducer messageProducer; //消息生产者
		
		// 实例化连接工厂
		connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
			
		try {
			// 创建连接
			connection = connectionFactory.createConnection();
			// 连接开启
			connection.start();
			// 创建Session会话
			// 参数1:是否开启事务
			// 参数2:会话Session
			// 	Session.AUTO_ACKNOWLEDGE - 自动确认 消费者从receive或监听成功返回时,自动确认客户端收到消息
			// 	Session.CLIENT_ACKNOWLEDGE - 客户通过acknowledge方法确认消息(会话层确认),确认一个即确认所有
			// 	Session.DUPS_OK_ACKNOWLEDGE - 重复确认
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建消息队列
			destination = session.createQueue("TestQueue"); 
			// 创建消息生产者
			messageProducer = session.createProducer(destination);
			// 发送消息
			sendMessage(session, messageProducer);
			// 由于加入了Session,需要提交
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("错误类名称 = " + e.getClass().getName());
			System.out.println("错误原因 = " + e.getMessage());
		}finally { //发送完毕后-关闭释放
			if(connection != null){
				try {
					connection.close();
					connection = null;
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 发送消息
	 * @param session			会话
	 * @param messageProducer	消息生产者对象
	 * @throws JMSException 
	 */
	private static void sendMessage(Session session, MessageProducer messageProducer) throws JMSException{
		for(int i = 0; i < 10; i++){
			TextMessage textMessage = session.createTextMessage("TestMessage:index = " + i);
			System.out.println("ActiveMQ-生产者-发送消息-index = " + i);
			messageProducer.send(textMessage);
		}
	}
	
}
