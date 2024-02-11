package com.infobip.assessment.url.shortener.controller;

import com.infobip.assessment.url.shortener.TestBase;
import com.infobip.assessment.url.shortener.dao.request.UrlRequest;
import com.infobip.assessment.url.shortener.dao.response.ShortUrlResponse;
import com.infobip.assessment.url.shortener.mapper.UrlMapper;
import io.restassured.http.Header;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AllArgsConstructor
class UrlControllerIntegrationTest extends TestBase {

    @Test
    void registerUrlSuccess() {
        // given
        createAccount();

        // when
        var url = "https://github.com/dbutkovic/url.shortener/tree/main";
        var redirectType = "301";
        var result = sendRequest(url, redirectType);

        // then
        assertNotNull(result);
        assertNotNull(result.getShortUrl());
        assertTrue(result.getShortUrl().startsWith(UrlMapper.BASE_URL));
    }


    @Test
    void registerUrlFail() {
        // given

        // when
        var url = "https://github.com/dbutkovic/url.shortener/tree/main";
        String redirectType = "301";
        var result = given()
                .contentType("application/json")
                .body(new UrlRequest(url, redirectType))
                .post("/register")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void redirectUrlSuccess() {
        // given
        createAccount();
        var url = "https://github.com/dbutkovic/url.shortener/tree/main";
        var redirectType = "301";
        var registerUrlResult = sendRequest(url, redirectType);

        // when
        var redirectUrlResult = sendRedirectRequest(registerUrlResult.getShortUrl());

        // then
        assertNotNull(redirectUrlResult);
        assertEquals(url, redirectUrlResult);
    }

    @Test
    void statisticUrlSuccess() {
        // given
        createAccount();
        var testUrl1 = "https://github.com/dbutkovic/url.shortener/tree/main";
        var redirectType301 = "301";
        var registerUrlResult1 = sendRequest(testUrl1, redirectType301);

        var testUrl2 = "https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html";
        var redirectType302 = "302";
        var registerUrlResult2 = sendRequest(testUrl2, redirectType302);

        // when
        sendRedirectRequest(registerUrlResult1.getShortUrl());
        sendRedirectRequest(registerUrlResult1.getShortUrl());

        sendRedirectRequest(registerUrlResult2.getShortUrl());
        sendRedirectRequest(registerUrlResult2.getShortUrl());
        sendRedirectRequest(registerUrlResult2.getShortUrl());

        var statisticResponse =
            given()
                .contentType("application/json")
                .header(new Header("Authorization", "Bearer " + token))
                .get("/statistic/user1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .getMap(".");

        // then
        assertEquals(2, statisticResponse.get(testUrl1));
        assertEquals(3, statisticResponse.get(testUrl2));
    }

    private String sendRedirectRequest(String url) {
        return given()
                .contentType("application/json")
                .header(new Header("Authorization", "Bearer " + token))
                .post(url)
                .then()
                .extract()
                .headers()
                .get("Location").getValue();
    }

    private ShortUrlResponse sendRequest(String url, String redirectType) {
        return given()
                .contentType("application/json")
                .body(new UrlRequest(url, redirectType))
                .header(new Header("Authorization", "Bearer " + token))
                .post("/register")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ShortUrlResponse.class);
    }

}