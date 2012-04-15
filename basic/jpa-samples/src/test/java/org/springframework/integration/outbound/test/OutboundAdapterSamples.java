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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.jpa.test.entity.Gender;
import org.springframework.integration.jpa.test.entity.Student;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Amol Nayak
 *
 */
@ContextConfiguration(locations="classpath:spring/outbound-adapter-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class OutboundAdapterSamples {

	private static final Logger logger = LoggerFactory.getLogger(OutboundAdapterSamples.class);
	
	@Autowired
	@Qualifier("entityTypeChannel")
	private MessageChannel entityChannel;
	
	@Autowired
	@Qualifier("jpaQlChannel")
	private MessageChannel jpaQlChannel; 
	
	@Autowired
	@Qualifier("nativeQlChannel")
	private MessageChannel nativeQlChannel;
	
	@Autowired
	@Qualifier("namedQueryChannel")
	private MessageChannel namedQueryChannel;
	
	
	/**
	 * Sends the entity over the channel to the outbound channel adapter for 
	 * persisting it into the database.
	 */
	@Test
	@Transactional
	public void sendUsingEntity() {
		logger.info("\nSending to the adapter using an entity");
		Calendar cal = Calendar.getInstance();
		cal.set(1980, 0, 1);		
		Student student = new Student();
		student.withFirstName("First")
				.withLastName("Last")
				.withGender(Gender.MALE)
				.withDateOfBirth(cal.getTime());
		entityChannel.send(MessageBuilder.withPayload(student).build());		
	}
	
	@Test
	@Transactional
	public void sendUsingeNativeQuery() {
		logger.info("\nSending to the adapter with native query");
		Map<String, String> params = new HashMap<String, String>();
		params.put("updatedLastName", "UpdatedLastName");
		nativeQlChannel.send(MessageBuilder.withPayload(params).build());
	}
	
	/**
	 * Sends a map containing the data to be updated in the student table
	 */
	@Test
	@Transactional
	public void sendUsingJpaQl() {
		logger.info("\nSending to the adapter with JPA QL");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("rollNumber",Long.valueOf(1));
		params.put("firstName", "Updated First");		
		jpaQlChannel.send(MessageBuilder.withPayload(params).build());
	}
	
	@Test
	@Transactional
	public void sendUsingNamedQuery() {
		logger.info("\nSending to the adapter with named query");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("firstName", "Updated First");		
		namedQueryChannel.send(MessageBuilder.withPayload(params).build());
	}
}
