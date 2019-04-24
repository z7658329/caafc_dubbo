package com.micro.dao;

import com.micro.api.mongodb.model.TbDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface TbDepartmentJpa extends JpaRepository<TbDepartment, String>,JpaSpecificationExecutor<TbDepartment> {

}