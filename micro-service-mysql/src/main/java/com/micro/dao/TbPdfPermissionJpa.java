package com.micro.dao;

import com.micro.api.mysql.model.TbDepartment;
import com.micro.api.mysql.model.TbPdfPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface TbPdfPermissionJpa extends JpaRepository<TbPdfPermission, String>,JpaSpecificationExecutor<TbPdfPermission> {

}