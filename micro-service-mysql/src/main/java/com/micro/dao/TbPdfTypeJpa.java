package com.micro.dao;

import com.micro.api.mongodb.model.TbPdfType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface TbPdfTypeJpa extends JpaRepository<TbPdfType, String>,JpaSpecificationExecutor<TbPdfType> {

}