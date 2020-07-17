package com.hdtp;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hdtp.dome.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageProducerTest {
	
	@Resource
	private MessageProducer messageProducer;
	
	@Test
	public void sendMessageTest() {
		messageProducer.sendMessage(new Employee("2541","bibi",29));
	}
}
