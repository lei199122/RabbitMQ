package com.hdtp;

import java.io.IOException;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.hdtp.dome.Employee;
import com.rabbitmq.client.Channel;


@Component
public class MessageConsumer {

	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "springboot-queue",durable = "true"),
			exchange = @Exchange(name = "springboot-exchange" ,durable = "true" , type = "topic"),
			key = "#"
			))
	@RabbitHandler
	public void handleMessage(@Payload Employee employee,Channel channel,@Headers Map<String, Object> headers) {
		
		System.out.println("empno:" + employee.getEmpno() +",name:"+ employee.getName() +",age:"+employee.getAge());
		
		long tag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);// DELIVERY_TAG == amqp_deliveryTag
		try {
			channel.basicAck(tag, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
