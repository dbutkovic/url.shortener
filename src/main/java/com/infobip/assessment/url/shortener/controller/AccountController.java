package com.infobip.assessment.url.shortener.controller;

import com.infobip.assessment.url.shortener.dao.request.AccountRequest;
import com.infobip.assessment.url.shortener.dao.response.AccountResponse;
import com.infobip.assessment.url.shortener.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping
    @ApiOperation(value = "Create account",
            notes = "Create account.",
            nickname = "create")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "SERVER_ERROR"),
            @ApiResponse(code = 404, message = "SERVICE_NOT_FOUND"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class, responseContainer = "Class")})
    public ResponseEntity<AccountResponse> createAccount(@RequestBody() AccountRequest account) {
        var accountRes = accountService.createAccount(account);
        return accountRes.isSuccess() ?
                new ResponseEntity<AccountResponse>(accountRes, HttpStatus.CREATED)
                :
                ResponseEntity.badRequest().body(accountRes);
    }
}
