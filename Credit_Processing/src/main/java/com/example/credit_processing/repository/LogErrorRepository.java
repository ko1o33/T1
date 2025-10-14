package com.example.credit_processing.repository;



import com.example.credit_processing.entite.aop.LogErrorEntity;
import org.springframework.data.repository.CrudRepository;

public interface LogErrorRepository extends CrudRepository<LogErrorEntity, String> {
}
