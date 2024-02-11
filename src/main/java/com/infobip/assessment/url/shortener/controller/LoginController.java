package com.infobip.assessment.url.shortener.controller;

import com.infobip.assessment.url.shortener.controller.security.request.LoginRequest;
import com.infobip.assessment.url.shortener.controller.security.response.JwtResponse;
import com.infobip.assessment.url.shortener.security.JwtTokenUtil;
import com.infobip.assessment.url.shortener.security.MyUserDetailsService;
import com.infobip.assessment.url.shortener.security.MyUserPrincipal;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping
    @ApiOperation(value = "User login",
            notes = "user login",
            nickname = "login")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "SERVER_ERROR"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 200, message = "SUCCESSFUL LOGIN", response = JwtResponse.class)})
    public ResponseEntity<?> login(@RequestBody LoginRequest authenticationRequest) throws BadRequestException {

        try {
            authenticate(authenticationRequest.accountId(), authenticationRequest.password());

            MyUserPrincipal userDetails = (MyUserPrincipal) myUserDetailsService.loadUserByUsername(authenticationRequest.accountId());

            String token = JwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            return new ResponseEntity<>("Wrong username or Password!", HttpStatus.UNAUTHORIZED);
        }
    }

    private void authenticate(String accountId, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(accountId, password));
    }
}
