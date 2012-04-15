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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.jpa.test.entity.Gender;
import org.springframework.integration.jpa.test.entity.Student;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * @author Amol Nayak
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/outbound-gateway-test.xml")
public class JpaOutboundGatewayTest {

	private static final Logger logger = LoggerFactory.getLogger(JpaOutboundGatewayTest.class);
	
	@Autowired
	@Qualifier("jpaOutboundGateway")
	private JpaOutboundGatewayService service;
	
	@Test
	@Transactional
	public void createUsingEntity() {
		logger.info("\n\n Creating a new student using entity");
		Student student = new Student()
							.withFirstName("First")
							.withLastName("Last")
							.withGender(Gender.MALE);
		student = service.persistStudent(student);
		logger.info("Created student with roll number " + student.getRollNumber() + "\n");
							
	}
	
	@Test
	@Transactional
	public void updateLastNameUsingJpaQl() {
		logger.info("\n\nUpdating last name of the student using JPA QL");
		int recordUpdated = service.updateLastNameUsingJpaQl("Last Name", 2);
		logger.info(recordUpdated + ", record(s) updated\n\n");
		
	}
	
	@Test
	@Transactional
	public void updateLastNameUsingNamedQuery() {
		logger.info("\n\nUpdating last name of the student using named query");
		int recordUpdated = service.updateLastNameUsingNamedQuery("Last Name", 2);
		logger.info(recordUpdated + ", record(s) updated\n\n");
		
	}
}
