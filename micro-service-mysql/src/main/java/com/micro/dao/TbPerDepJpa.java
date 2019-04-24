package com.micro.dao;

import com.micro.api.mongodb.model.TbPerDep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface TbPerDepJpa extends JpaRepository<TbPerDep, String>,JpaSpecificationExecutor<TbPerDep> {

}