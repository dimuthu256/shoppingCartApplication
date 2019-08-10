package com.org.shoppingcart.rest.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.org.shoppingcart.rest.request.ItemsRequest;

@Service
public class QueueMessageServiceImpl {

	private Logger logger;

	public QueueMessageServiceImpl() {
		super();
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Value("${rabbitmq.exchange}")
	private String exchange;

	@Value("${rabbitmq.routingkey}")
	private String routingkey;

	public void send(String message) {
		logger.info("Message is ready to send : {}", message);
		amqpTemplate.convertAndSend(exchange, routingkey, message);
		logger.info("Message sent to the queue : {}", message);

	}

	public void send(ItemsRequest products) {
		logger.info("Message is ready to send : {}", products.toString());
		amqpTemplate.convertAndSend(exchange, routingkey, products);
		logger.info("Message sent to the queue : {}", products.toString());

	}
}