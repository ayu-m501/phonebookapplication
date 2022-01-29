package com.phonebookapp.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.phonebookapp.entity.Contact;
import com.phonebookapp.exception.RecordNotFoundException;
import com.phonebookapp.repository.ContactRepository;
import com.phonebookapp.service.ContactService;

@Service
public class ContactServiceIMPL implements ContactService {

	public static final String ProviveNameEmailNo = "name,contactNo. can't give empty";

	private static final Logger log = LoggerFactory.getLogger(ContactServiceIMPL.class);

	private ContactRepository contactRepository;

	public ContactServiceIMPL(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	@Override
	public boolean saveContact(Contact contact) {
		log.info("saveContact method started");
		Contact contactSaved = contactRepository.save(contact);
		return contactSaved.getContactId() != null && contactSaved.getContacNumber() != null;
	}

	@Override
	public Contact getContactBYContactId(Integer contactId) {
		log.info("getContactBYContactId method started");
		Optional<Contact> findById = contactRepository.findById(contactId);
		if (findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	@Override
	public List<Contact> getAllContacts() {
		log.info("getAllContacts method started");
		return contactRepository.findAll();
	}

	@Override
	public boolean deleteContactById(Integer contactId) {
		log.info("deleteContactById method started");
		try {
			contactRepository.deleteById(contactId);
			return true;
		} catch (Exception e) {
			throw new RecordNotFoundException("no record found to delete");
		}

	}

}
