package br.edu.ufrn.expensify.account.record;

public record CreateAccountRequest(
    String name,
    Double balance
) {}
