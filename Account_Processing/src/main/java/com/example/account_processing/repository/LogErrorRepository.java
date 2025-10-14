package com.example.account_processing.repository;


import com.example.account_processing.entite.aop.LogErrorEntity;
import org.springframework.data.repository.CrudRepository;

public interface LogErrorRepository extends CrudRepository<LogErrorEntity, String> {
}
