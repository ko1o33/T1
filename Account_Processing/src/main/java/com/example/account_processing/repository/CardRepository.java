package com.example.account_processing.repository;

import com.example.account_processing.entite.card.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, String> {
    Card getByCardId(String id);
}
