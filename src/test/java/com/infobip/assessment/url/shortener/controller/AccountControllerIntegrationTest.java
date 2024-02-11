package com.infobip.assessment.url.shortener.controller;

import com.infobip.assessment.url.shortener.TestBase;
import com.infobip.assessment.url.shortener.dao.request.AccountRequest;
import com.infobip.assessment.url.shortener.dao.response.AccountResponse;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AllArgsConstructor
class AccountControllerIntegrationTest extends TestBase {

    @Test
    void createAccountSuccess() {
        // given

        // when
        var result = given()
                .contentType("application/json")
                .body(new AccountRequest("user1"))
                .post("/account")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(AccountResponse.class);

        // then
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("User created with account id: 'user1'", result.getDescription());
        assertEquals(8, result.getPassword().length());

        var accountEntity = accountRepository.findByAccountId("user1");
        assertNotNull(accountEntity);
        assertNotNull(accountEntity.getId());
        assertEquals("user1",accountEntity.getAccountId());
        assertEquals(result.getPassword(), accountEntity.getPassword());
    }

    @Test
    void createAccountFail() {
        // given
        var result = given()
                .contentType("application/json")
                .body(new AccountRequest("user1"))
                .post("/account")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(AccountResponse.class);

        // when
        result = given()
                .contentType("application/json")
                .body(new AccountRequest("user1"))
                .post("/account")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .as(AccountResponse.class);

        // then
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertEquals("User with account id: 'user1' already exists!", result.getDescription());
        assertNull(result.getPassword());
    }
}