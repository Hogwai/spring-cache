package com.hogwai.spring_jcache_ehcache.service.impl;

import com.hogwai.spring_jcache_ehcache.model.Customer;
import com.hogwai.spring_jcache_ehcache.repository.CustomerCustomRepository;
import com.hogwai.spring_jcache_ehcache.repository.CustomerRepository;
import com.hogwai.spring_jcache_ehcache.service.CustomerService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.List;

import static com.hogwai.spring_jcache_ehcache.util.CustomerFactory.generateCustomers;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerCustomRepository customerCustomRepository;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerCustomRepository customerCustomRepository,
                               CustomerRepository customerRepository) {
        this.customerCustomRepository = customerCustomRepository;
        this.customerRepository = customerRepository;
    }

    //region JDBC template
    @Override
    @Transactional
    public void saveAllByBatch(Integer number){
        List<Customer> customers = generateCustomers(number);
        System.out.println("Generated customers: " + customers.size());
        customerCustomRepository.saveAllByBatch(customers);
    }

    @Override
    @Transactional
    @CacheEvict(value = "customers", allEntries = true)
    public void updateAllByBatch(){
        List<Customer> customersToUpdate = customerCustomRepository.getAllCustomers();
        System.out.println("Retrieved customers : " + customersToUpdate.size());

        StopWatch updateWatch = new StopWatch();
        updateWatch.start();
        customerCustomRepository.updateAllByBatch(customersToUpdate);
        System.out.println("Updated customers : " + customersToUpdate.size());
        updateWatch.stop();

        System.out.println("Time elapsed for update: " + updateWatch.getTotalTimeSeconds());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "customers", key = "#root.methodName")
    public List<Customer> getAllCustomers() {
        return customerCustomRepository.getAllCustomers();
    }
    //endregion

    //region Custom Hibernate repository
    @Override
    @Transactional
    public void saveAllByBatchHibernate(Integer number) {
        List<Customer> customers = generateCustomers(number);
        System.out.println("Generated customers: " + customers.size());
        System.out.printf("Saved customers: %d %n", customerRepository.mergeAll(customers).size());
    }

    @Override
    @Transactional
    public void updateAllByBatchHibernate(){
        List<Customer> customersToUpdate = customerRepository.findAll();
        System.out.printf("Retrieved customers : %d %n", customersToUpdate.size());

        StopWatch updateWatch = new StopWatch();
        updateWatch.start();
        System.out.printf("Updated customers: %d %n ", customerRepository.updateAll(customersToUpdate).size());
        updateWatch.stop();

        System.out.printf("Time elapsed for update: %.0f %n ", updateWatch.getTotalTimeSeconds());
    }

    @Override
    @Transactional
    public void deleteAll() {
        customerRepository.deleteAll();
    }
    //endregion
}
