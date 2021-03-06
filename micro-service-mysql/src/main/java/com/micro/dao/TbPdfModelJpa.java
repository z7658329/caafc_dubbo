package com.micro.dao;

import com.micro.api.mongodb.model.TbPdfModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface TbPdfModelJpa extends JpaRepository<TbPdfModel, String>,JpaSpecificationExecutor<TbPdfModel> {

}