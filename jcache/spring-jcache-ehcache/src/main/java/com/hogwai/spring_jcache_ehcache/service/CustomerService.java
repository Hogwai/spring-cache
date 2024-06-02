package com.hogwai.spring_jcache_ehcache.service;

import com.hogwai.spring_jcache_ehcache.model.Customer;

import java.util.List;

public interface CustomerService {
    void saveAllByBatch(Integer number);

    void updateAllByBatch();

    List<Customer> getAllCustomers();

    void saveAllByBatchHibernate(Integer number);

    void updateAllByBatchHibernate();

    void deleteAll();
}
