package com.brave.ActiveMQ;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * 消息监听器
 * @author Brave
 *
 */
public class MessageListener implements javax.jms.MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("消费者-监听方式-收到消息: " + ((TextMessage)message).getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
