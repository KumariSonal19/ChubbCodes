package com.chubb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chubb.request.Order1;

@Repository
public interface OrderRepository extends CrudRepository<Order1, Integer>{

}