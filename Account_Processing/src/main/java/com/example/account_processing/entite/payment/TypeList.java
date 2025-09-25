package com.example.account_processing.entite.payment;

public enum TypeList {
    DEPOSIT,        // Внесение наличных (isCredit = true)
    WITHDRAWAL,     // Снятие наличных (isCredit = false)
    TRANSFER,       // Перевод между счетами
    PAYMENT,        // Платеж за услуги (ЖКХ, интернет)
    FEE,            // Комиссия банка (isCredit = false)
    INTEREST,       // Начисление процентов (isCredit = true)
    REFUND,         // Возврат средств (isCredit = true)
    LOAN_PAYMENT    // Платеж по кредиту
}
