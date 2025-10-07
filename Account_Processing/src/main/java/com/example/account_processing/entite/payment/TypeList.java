package com.example.account_processing.entite.payment;

public enum TypeList {
    DEPOSIT,        // Внесение наличных
    WITHDRAWAL,     // Снятие наличных
    LOAN_PAYMENT,   // Платеж по кредиту
    PAYED_AT,       // Оплатил кредит(ДОБИ СВОБОДЕН)
    TRANSFER,       // Перевод между счетами
    CREATED,        //Был создан
    EXPIRED         // Ожидание
}
