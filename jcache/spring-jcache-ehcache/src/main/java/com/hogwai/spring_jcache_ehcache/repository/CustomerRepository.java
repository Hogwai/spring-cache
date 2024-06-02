package com.hogwai.spring_jcache_ehcache.repository;

import com.hogwai.spring_jcache_ehcache.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository
        extends HibernateRepository<Customer>, JpaRepository<Customer, Long> {
}
