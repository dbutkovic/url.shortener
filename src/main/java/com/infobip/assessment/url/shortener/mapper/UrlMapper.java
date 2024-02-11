package com.infobip.assessment.url.shortener.mapper;

import com.infobip.assessment.url.shortener.dao.request.UrlRequest;
import com.infobip.assessment.url.shortener.dao.response.ShortUrlResponse;
import com.infobip.assessment.url.shortener.enums.RedirectType;
import com.infobip.assessment.url.shortener.persistence.entity.UrlEntity;

import java.util.UUID;

public class UrlMapper {

    public final static String BASE_URL =
            "http://localhost:8080/redirect/"; // Should be in application.yaml

    public static UrlEntity mapRequestToEntity(UrlRequest urlRequest) {

        return new UrlEntity().toBuilder()
                .id(UUID.randomUUID()) // Can be same for short url
                .longUrl(urlRequest.getUrl())
                .shortUrl(BASE_URL + UUID.randomUUID()) // Should not save BASE_URL in database
                .redirectType(RedirectType.fromStatusCode(urlRequest.getRedirectType()))
                .numberOfCalls(0)
                .build();
    }

    public static ShortUrlResponse mapEntityToRequest(UrlEntity urlRequest) {

        return new ShortUrlResponse().toBuilder()
                .shortUrl(urlRequest.getShortUrl())
                .build();
    }
}

