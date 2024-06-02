package com.hogwai.spring_jcache_ehcache.repository;

import com.hogwai.spring_jcache_ehcache.model.Customer;

import java.util.List;

public interface CustomerCustomRepository  {
    void saveAllByBatch(List<Customer> customers);

    void updateAllByBatch(List<Customer> customers);

    List<Customer> getAllCustomers();

    void deleteAllCustomers();
}
