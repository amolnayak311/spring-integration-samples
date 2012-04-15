/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.integration.outbound.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageHandler;
import org.springframework.integration.core.SubscribableChannel;
import org.springframework.integration.jpa.test.entity.Student;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author Amol Nayak
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/inbound-adapter-test.xml")
public class InboundAdapterSample {

	private static final Logger logger = LoggerFactory.getLogger(InboundAdapterSample.class);
	
	@Autowired
	@Qualifier("inboundChannelAdapterOne")
	private SubscribableChannel channel;
	
	@Before	
	public void setup() {
		channel.subscribe(new MessageHandler() {			
			public void handleMessage(Message<?> message) throws MessagingException {
				Student student = (Student)message.getPayload();
				logger.info("\n\nRetrieved student with roll number: " + student.getRollNumber());
				logger.info(",\tFirst Name: " + student.getFirstName() + ",\tLast Name:" + student.getLastName());
				
			}
		});
	}
	
	@Test
	public void sleep() throws Exception {
		Thread.sleep(10000);
	}
}
