package com.org.shoppingcart.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.shoppingcart.core.config.GsonUtil;
import com.org.shoppingcart.core.request.ProductRequest;
import com.org.shoppingcart.core.service.ShoppingCartService;
import com.rabbitmq.client.Channel;

@Component
public class ShoppingCartMessageListener implements ChannelAwareMessageListener {
	private Logger logger;

	@Autowired
	private ShoppingCartService shoppingCartService;

	public ShoppingCartMessageListener(MessageConverter messageConverter, ShoppingCartService shoppingCartService) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.shoppingCartService = shoppingCartService;
	}

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		logger.info("Message received from the queue.");
		try {
			ProductRequest request = GsonUtil.getFromObject(new String(message.getBody()), ProductRequest.class);
			logger.info("Message received from the queue : {}", request.toString());
			boolean result = shoppingCartService.saveAll(request);
			// Acknowledge to delete from the queue
			if (result) {
				logger.info("Message executed successfully. \\n Channel clossing..");
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				logger.info("Channel closed..");
			}

		} catch (Exception e) {
			logger.error("Error occured while message processing. : {}", e.getMessage());
		}
	}

}