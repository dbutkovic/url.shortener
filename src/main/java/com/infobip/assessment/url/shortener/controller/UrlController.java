package com.infobip.assessment.url.shortener.controller;

import com.infobip.assessment.url.shortener.dao.request.UrlRequest;
import com.infobip.assessment.url.shortener.dao.response.ShortUrlResponse;
import com.infobip.assessment.url.shortener.service.SecurityService;
import com.infobip.assessment.url.shortener.service.UrlService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UrlController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UrlService urlService;

    @PostMapping("/register")
    @ApiOperation(value = "Create short url",
            notes = "Create short url.",
            nickname = "create")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "SERVER_ERROR"),
            @ApiResponse(code = 404, message = "SERVICE_NOT_FOUND"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class, responseContainer = "Class")})
    public ResponseEntity<ShortUrlResponse> createShortenUrl(Authentication authentication,
                                                             @RequestBody UrlRequest urlRequest) {

        securityService.getAuthenticatedAccount(authentication.getName());

        return ResponseEntity.ok(urlService.createUrl(urlRequest, authentication.getName()));
    }

    @GetMapping("/redirect/{shortUrl}")
    @RequestMapping("/redirect/{shortUrl}")
    @ApiOperation(value = "Redirect from short url",
            notes = "Redirect from short url.",
            nickname = "redirect")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "SERVER_ERROR"),
            @ApiResponse(code = 404, message = "SERVICE_NOT_FOUND"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class, responseContainer = "Class")})
    public ResponseEntity<Void> redirect(@ApiParam(value = "shortUrl", required = true) @PathVariable(name = "shortUrl") String shortUrl) {
        var originalUrlEntity = urlService.getOriginalUrl(shortUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrlEntity.getLongUrl()));
        HttpStatus status = HttpStatus.TEMPORARY_REDIRECT;
        if (originalUrlEntity.getRedirectType().equals(Short.parseShort("301"))) {
            status = HttpStatus.PERMANENT_REDIRECT;
        }

        return new ResponseEntity<>(headers, status);
    }

    @GetMapping("/statistic/{AccountId}")
    @ApiOperation(value = "Statistic for url",
            notes = "Statistic for url",
            nickname = "statistic")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "SERVER_ERROR"),
            @ApiResponse(code = 404, message = "SERVICE_NOT_FOUND"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 200, message = "OK", response = ResponseEntity.class, responseContainer = "Class")})
    public ResponseEntity<Map<String, Integer>> getStatistic(Authentication authentication,
                                                             @ApiParam(value = "AccountId", required = true) @PathVariable(name = "AccountId") String accountId) {

        securityService.getAuthenticatedAccount(authentication.getName());

        return ResponseEntity.ok(urlService.getStatistic(accountId));
    }

}
