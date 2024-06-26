package com.hogwai.spring_jcache_ehcache.repository.impl;

import com.hogwai.spring_jcache_ehcache.model.Customer;
import com.hogwai.spring_jcache_ehcache.repository.CustomerCustomRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CustomerCustomRepositoryImpl implements CustomerCustomRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerCustomRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveAllByBatch(List<Customer> customers) {
        String sql =
                """
                INSERT INTO customer (id, first_name, last_name, address, city, country, creation_date)
                VALUES (nextval('cust_seq'), ?, ?, ?, ?, ?, ?)
                """;

        java.sql.Date creationDate = new java.sql.Date(new java.util.Date().getTime());
        jdbcTemplate.batchUpdate(
                sql,
                customers,
                customers.size(),
                (PreparedStatement ps, Customer customer) -> {
                    ps.setString(1, customer.getFirstName());
                    ps.setString(2, customer.getLastName());
                    ps.setString(3, customer.getAddress());
                    ps.setString(4, customer.getCity());
                    ps.setString(5, customer.getCountry());
                    ps.setDate(6, creationDate);
                });
    }

    @Override
    public void updateAllByBatch(List<Customer> customers) {
        String sql =
                """
                UPDATE customer
                SET
                    city = ?,
                    country = ?,
                    update_date = ?
                WHERE id = ?
                """;
        java.sql.Date updateDate = new java.sql.Date(new java.util.Date().getTime());
        jdbcTemplate.batchUpdate(
            sql,
            customers,
            customers.size(),
            (PreparedStatement ps, Customer customer) -> {
                ps.setString(1, customer.getCity());
                ps.setString(2, customer.getCountry());
                ps.setDate(3, updateDate);
                ps.setLong(4, customer.getId());
            }
        );
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM CUSTOMER";

        return jdbcTemplate.query(
            sql,
            (rs, rowNum) ->
                new Customer(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("country"),
                    rs.getString("address"),
                    rs.getString("city"),
                    rs.getDate("creation_date"),
                    rs.getDate("update_date")
                )
        );
    }

    @Override
    public void deleteAllCustomers() {
        jdbcTemplate.update("DELETE FROM customer");
    }
}
