package com.harsh.securebank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "contact_messages")
public class Contact {

    @Id
    private String contactId;

    private String contactName;

    private String contactEmail;

    private String subject;

    private String message;

    private Date createDt;

}