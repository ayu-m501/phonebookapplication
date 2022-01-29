package com.phonebookapp.service;

import java.util.List;

import com.phonebookapp.entity.Contact;


public interface ContactService {

	public boolean saveContact(Contact contact);

	public Contact getContactBYContactId(Integer contactId);

	public List<Contact> getAllContacts();

	public boolean deleteContactById(Integer contactId);


}
