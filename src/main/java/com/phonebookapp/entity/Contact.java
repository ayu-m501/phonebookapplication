  package com.phonebookapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contact_dtls")
@Data
@NoArgsConstructor
public class Contact {

	@Id
	@Column(name = "contact_id")
	@GeneratedValue
	private Integer contactId;

	@Column(name = "contact_name")
	private String contactName;

	@Column(name = "contact_email")
	private String contactEmail;

	@Column(name = "contact_number")
	private Long contacNumber;

	public Contact(Integer contactId, String contactName, String contactEmail, Long contacNumber) {
		super();
		this.contactId = contactId;
		this.contactName = contactName;
		this.contactEmail = contactEmail;
		this.contacNumber = contacNumber;
	}

}
