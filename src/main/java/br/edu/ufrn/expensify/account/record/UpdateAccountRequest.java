package br.edu.ufrn.expensify.account.record;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateAccountRequest(
    @JsonProperty(required = false) String name
) {}
