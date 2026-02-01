package br.edu.ufrn.expensify.account.record;

import java.util.UUID;

public record AccountResponse(
    UUID id,
    String name,
    Double balance
) {}
