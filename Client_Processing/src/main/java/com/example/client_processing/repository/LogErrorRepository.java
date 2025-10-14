package com.example.client_processing.repository;

import com.example.client_processing.entite.aop.LogErrorEntity;
import org.springframework.data.repository.CrudRepository;

public interface LogErrorRepository extends CrudRepository<LogErrorEntity, String> {
}
