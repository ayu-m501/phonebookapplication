package com.phonebookapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phonebookapp.config.YAMLConfig;
import com.phonebookapp.constant.ContactContants;
import com.phonebookapp.entity.Contact;
import com.phonebookapp.exception.RecordNotFoundException;
import com.phonebookapp.serviceimpl.ContactServiceIMPL;

@RestController
@RequestMapping(value = "/phoneBook/contact")
public class ContactController {

	@Autowired
	private YAMLConfig ymlConfig;

	private ContactServiceIMPL contactServiceIMPL;

	public ContactController(ContactServiceIMPL contactServiceIMPL) {
		this.contactServiceIMPL = contactServiceIMPL;
	}

	private static final Logger log = LoggerFactory.getLogger(ContactServiceIMPL.class);

	@PostMapping
	public ResponseEntity<String> saveContact(Contact contact) {
		log.debug("saveContact method started");
		try {
			boolean savedContact = contactServiceIMPL.saveContact(contact);
			if (savedContact) {
				log.info("contact saved successfully");
				String saved = ymlConfig.getMessages().get(ContactContants.contact_saved_Y);
				return new ResponseEntity<String>(saved, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			log.error("error ocurred in saveContact method" + e.getMessage());
		}
		log.info("contact not saved successfully");
		String saved = ymlConfig.getMessages().get(ContactContants.contact_saved_N);
		return new ResponseEntity<String>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping
	public ResponseEntity<List<Contact>> getAllContacts() {
		log.debug("getAllContacts method started");
		List<Contact> allContacts = null;
		try {
			allContacts = contactServiceIMPL.getAllContacts();
			if (allContacts.isEmpty()) {
				log.info("contacts is not available...plz check database");
			}
		} catch (Exception e) {
			log.error("getAllContacts method not working as expected" + e.getMessage());
		}
		log.debug("getAllContacts method ended");
		return new ResponseEntity<List<Contact>>(allContacts, HttpStatus.OK);
	}

	@GetMapping("/{contactId}")
	public ResponseEntity<?> getContactById(@PathVariable(name = "contactId") Integer contactId) {
		log.debug("getContactById method started");
		Contact contact = null;
		try {
			contact = contactServiceIMPL.getContactBYContactId(contactId);
			if (contact == null) {
				log.info("no contact found");
				throw new RecordNotFoundException("no content found");
			}
		} catch (Exception e) {
			log.error("getContactById not working fine as expected");
		}
		log.debug("getContactById method ended");
		return new ResponseEntity<>(contact, HttpStatus.OK);
	}

	@DeleteMapping("/deleteContact")
	public ResponseEntity<String> deleteContactById(Integer contactId) {
		log.debug("** deleteContactById() -  method started ** ");
		ResponseEntity<String> responseEntity = null;
		try {
			boolean isDeleted = contactServiceIMPL.deleteContactById(contactId);
			if (isDeleted)
				log.info("**deleteContactById()**- record deleted successfullu");
			responseEntity = new ResponseEntity<>("deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			log.error("**Exception occured**" + e.getMessage());
		}
		log.debug("** deleteContactById() -  method ended ** ");
		responseEntity = new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}
}
