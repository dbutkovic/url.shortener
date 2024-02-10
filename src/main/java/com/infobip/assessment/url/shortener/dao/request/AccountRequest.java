package com.infobip.assessment.url.shortener.dao.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    @JsonProperty("AccountId") // first letter is uppercase by documentation
    private String accountId;
}
