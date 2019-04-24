package com.micro.dao;

import com.micro.api.mongodb.model.TbPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface TbPersonJpa extends JpaRepository<TbPerson, String>,JpaSpecificationExecutor<TbPerson> {

}