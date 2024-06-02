package com.hogwai.spring_jcache_ehcache.util;

import com.hogwai.spring_jcache_ehcache.model.Customer;

import java.util.ArrayList;
import java.util.List;

import static com.hogwai.spring_jcache_ehcache.util.StringUtil.generateRandomString;

public class CustomerFactory {

    private CustomerFactory() {
    }

    public static List<Customer> generateCustomers(Integer number) {
        java.sql.Date creationDate = new java.sql.Date(new java.util.Date().getTime());
        List<Customer> customers = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            Customer customer = Customer.builder()
                    .id((long) i + 1)
                    .firstName(generateRandomString())
                    .lastName(generateRandomString())
                    .address(generateRandomString())
                    .city(generateRandomString())
                    .country(generateRandomString())
                    .creationDate(creationDate)
                    .build();
            customers.add(customer);
        }
        return customers;
    }
}
