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
public class AuthRequest {

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("password")
    private String password;
}
