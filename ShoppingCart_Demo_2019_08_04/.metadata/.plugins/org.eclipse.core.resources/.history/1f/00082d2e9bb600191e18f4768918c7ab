package com.spring.amq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.spring.amq.model.Content;
import com.spring.amq.util.GsonUtil;

@Component
public class MessageListener implements ChannelAwareMessageListener {
	private Logger logger;
	private MessageConverter messageConverter;
	
	public MessageListener(MessageConverter messageConverter){
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.messageConverter = messageConverter;
	}
	
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		logger.info("Message received.");		
		try {
			logger.info("Cosumer response string {}",this.messageConverter.fromMessage(message).toString());
			Content content = GsonUtil.getFromObject(this.messageConverter.fromMessage(message).toString(), Content.class);
			logger.info("Cosumer response {} - {} - {}", content.getMessage(), content.getMobileNumber(), content.getName());
			// Acknowledge to delete from the queue
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}catch (Exception e) {
			logger.error("Getting producer message failed {}", e.getMessage());
		}
	}

}
