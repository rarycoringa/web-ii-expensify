package br.edu.ufrn.expensify.transaction.record;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateTransferRequest(
    String description,
    Double amount,
    LocalDate date,
    @JsonProperty("source_account_id") UUID sourceAccountId,
    @JsonProperty("destination_account_id") UUID destinationAccountId
) {}
