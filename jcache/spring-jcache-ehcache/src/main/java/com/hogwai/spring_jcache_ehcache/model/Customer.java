package com.hogwai.spring_jcache_ehcache.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cust_seq")
    @SequenceGenerator(name = "cust_seq", sequenceName = "cust_seq", allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;

    private String country;
    private String address;
    private String city;

    private Date creationDate;
    private Date updateDate;
}