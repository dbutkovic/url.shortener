package com.infobip.assessment.url.shortener.mapper;

import com.infobip.assessment.url.shortener.dao.request.UrlRequest;
import com.infobip.assessment.url.shortener.dao.response.ShortUrlResponse;
import com.infobip.assessment.url.shortener.persistence.entity.UrlEntity;

import java.util.UUID;

public class UrlMapper {

    public final static String BASE_URL = "http://localhost:8080/redirect/";

    public static UrlEntity mapRequestToEntity(UrlRequest urlRequest) {

        return new UrlEntity().toBuilder()
                .id(UUID.randomUUID())
                .longUrl(urlRequest.getUrl())
                .shortUrl(BASE_URL + UUID.randomUUID())
                .redirectType(urlRequest.getRedirectType())
                .numberOfCalls(0)
                .build();
    }

    public static ShortUrlResponse mapEntityToRequest(UrlEntity urlRequest) {

        return new ShortUrlResponse().toBuilder()
                .shortUrl(urlRequest.getShortUrl())
                .build();
    }
}
