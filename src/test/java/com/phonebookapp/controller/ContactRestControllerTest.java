package com.phonebookapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonebookapp.config.YAMLConfig;
import com.phonebookapp.entity.Contact;
import com.phonebookapp.serviceimpl.ContactServiceIMPL;

@WebMvcTest(value = ContactController.class)
public class ContactRestControllerTest {

	// isolated unit testing means we will not call direct to real(depended) object
	// we will call mock(dummy) object
	@MockBean
	private ContactServiceIMPL contactServiceIMPL;
	
	@MockBean
	private YAMLConfig ymlConfig;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void test_saveContact() throws Exception {

		// saveContact - i want perform unit testing for saveContact method from
		// controller and this method is depended on service layer
		// defining the behavior of mock object //we will not be call real object ,we
		// will create dummy object then we will perform operation on that
		when(contactServiceIMPL.saveContact(Mockito.any())).thenReturn(true);

		// storing dummy data for testing
		Contact c = new Contact(101, "Ayush", "ayush.mandloi@gmal.com", 100l);

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			// converting java object into json
			json = mapper.writeValueAsString(c);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// to send http request we are using MockHttpServletRequestBuilder //once
		// request is ready then we will call mockmvc.perform()
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/phoneBook/contact")
				// i want to send data in json format
				.contentType("application/json").content(json);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();

		int status = response.getStatus();

		// if actual and expected value both are same then our test is pass
		
		assertEquals(201, status);
	}

	@Test
	public void test_saveContact1() throws Exception {

		when(contactServiceIMPL.saveContact(Mockito.any())).thenReturn(false);

		Contact c = new Contact(100, "vishu", "vishu@gmail.com", 5246l);

		ObjectMapper om = new ObjectMapper();

		String json = null;
		try {
			json = om.writeValueAsString(c);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/phoneBook/contact")
				.contentType("/application/json").content(json);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();

		int status = response.getStatus();

		assertEquals(500, status);

	}

	@Test
	public void test_saveContact2() throws Exception {

		when(contactServiceIMPL.saveContact(Mockito.any())).thenThrow(RuntimeException.class);

		Contact c = new Contact(100, "vishu", "vishu@gmail.com", 5246l);

		ObjectMapper om = new ObjectMapper();

		String json = null;
		try {
			json = om.writeValueAsString(c);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/phoneBook/contact")
				.contentType("/application/json").content(json);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();

		int status = response.getStatus();

		assertEquals(500, status);
	}

	@Test
	public void test_getAllContactList() throws Exception {

		when(contactServiceIMPL.getAllContacts()).thenReturn(Collections.emptyList());

		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/phoneBook/contact");

		MvcResult mvcResult = mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();

		int status = response.getStatus();

		assertEquals(200, status);

	}

	@Test
	public void test_getContactById() throws Exception {

		Contact c = new Contact(100, "vishu", "vishu@gmail.com", 5246l);

		when(contactServiceIMPL.getContactBYContactId(Mockito.anyInt())).thenReturn(c);

		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
				.get("/phoneBook/contact/101");

		MvcResult mvcResult = mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();

		int status = response.getStatus();

		assertEquals(200, status);
	}

	@Test
	public void test_getContactById1() throws Exception {

		when(contactServiceIMPL.getContactBYContactId(Mockito.anyInt())).thenThrow(RuntimeException.class);

		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
				.get("/phoneBook/contact/101");

		MvcResult mvcResult = mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();

		int status = response.getStatus();

		assertEquals(200, status);
	}

//	@Test
//	public void test_getContactById() throws Exception {
//		
//		when(contactServiceIMPL.getContactBYContactId(101)).thenReturn(null);
//		
//		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/phoneBook/contact/101");
//		
//		MvcResult mvcResult = mockMvc.perform(mockHttpServletRequestBuilder).andReturn();
//		
//		MockHttpServletResponse response = mvcResult.getResponse();
//		
//		int status = response.getStatus();
//		
//		System.out.println("**************"+ status + "*****************");
//		
//		assertEquals(200, status);
//	}
}
