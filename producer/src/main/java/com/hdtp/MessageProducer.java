package com.hdtp;

import java.util.Date;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hdtp.dome.Employee;

@Component
public class MessageProducer {
	
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
		
		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String cause) {
			System.out.println("消息的附加消息---》" + correlationData);
			System.out.println("消息是否拒收 true接受  false拒收--》" + ack);
			if(!ack) {
				System.err.println("拒收原因--》" + cause);
			}
		}
	};
	
	RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
		
		@Override
		public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
			System.err.println("returnedMessage错误的编码--》" + replyCode + "错误的描述-->" + replyText );
			System.err.println("returnedMessage交换机的名字--》" + exchange + "路由key-->" + routingKey );
			System.err.println("returnedMessage被退回的消息--》" + message);
		}
	};
	
	public void sendMessage(Employee employee) {
		CorrelationData  correlationData = new CorrelationData(employee.getEmpno() + "_" + new Date().getTime());
		rabbitTemplate.setConfirmCallback(confirmCallback);
		rabbitTemplate.setReturnCallback(returnCallback);
		rabbitTemplate.convertAndSend("springboot-exchange","hr.employee",employee,correlationData);
	}
}
