package org.example.service;

import org.example.entite.aop.LogErrorEntity;
import org.example.repository.LogErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogErrorService {

    @Autowired
    LogErrorRepository repository;

    public void save(LogErrorEntity entity) {
        repository.save(entity);
    }

}
