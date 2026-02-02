package br.edu.ufrn.expensify.transaction.record;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExpenseResponse(
    UUID id,
    String description,
    Double amount,
    LocalDate date,
    @JsonProperty("account_id") UUID accountId
) {}
