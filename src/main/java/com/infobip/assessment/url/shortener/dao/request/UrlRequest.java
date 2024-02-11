package com.infobip.assessment.url.shortener.dao.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.infobip.assessment.url.shortener.enums.RedirectType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UrlRequest {

    @JsonProperty("url")
    private String url;

    @JsonProperty("redirectType")
    private String redirectType = "302";
}
