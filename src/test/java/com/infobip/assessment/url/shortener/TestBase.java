package com.infobip.assessment.url.shortener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infobip.assessment.url.shortener.controller.security.request.LoginRequest;
import com.infobip.assessment.url.shortener.controller.security.response.JwtResponse;
import com.infobip.assessment.url.shortener.dao.request.AccountRequest;
import com.infobip.assessment.url.shortener.dao.response.AccountResponse;
import com.infobip.assessment.url.shortener.mapper.AccountMapper;
import com.infobip.assessment.url.shortener.mapper.UrlMapper;
import com.infobip.assessment.url.shortener.persistence.repository.AccountRepository;
import com.infobip.assessment.url.shortener.persistence.repository.UrlRepository;
import com.infobip.assessment.url.shortener.service.AccountService;
import com.infobip.assessment.url.shortener.service.SecurityService;
import com.infobip.assessment.url.shortener.service.UrlService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ContextConfiguration(initializers = {TestBase.Initializer.class})
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestBase {

    protected String token;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected AccountService accountService;

    @Autowired
    protected SecurityService securityService;

    @Autowired
    protected UrlService urlService;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected UrlRepository urlRepository;

    @BeforeEach
    void set() {
        System.out.println("BeforeEach set");
        RestAssuredMockMvc.webAppContextSetup(context);
        RestAssuredMockMvc.postProcessors(csrf().asHeader());
        accountRepository.deleteAll();
        urlRepository.deleteAll();
    }

    @AfterEach
    void reset() {
        RestAssuredMockMvc.reset();
    }

    protected String toPayload(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected AccountResponse createAccount() {
        String user = "user1";
        var account = given()
                .contentType("application/json")
                .body(new AccountRequest(user))
                .post("/account")
                .then()
                .extract()
                .as(AccountResponse.class);
        login(account, user);
        return account;
    }

    protected void login(AccountResponse accountResponse, String user) {
        var loginResponse = given()
            .contentType("application/json")
            .body(new LoginRequest(user, accountResponse.getPassword()))
            .post("/login")
            .then()
            .extract()
            .as(JwtResponse.class);

        this.token = (loginResponse.token());

    }

    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

//    static public class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//        public void initialize(ConfigurableApplicationContext context) {
//            TestPropertyValues.of(
//                    "spring.datasource.url=jdbc:h2:file:/data/demo",
//                    "spring.datasource.username=sa",
//                    "spring.datasource.password=password"
//            ).applyTo(context.getEnvironment());
//        }
//    }
//
//    protected RequestPostProcessor givenAuthentication(String username) {
//        return SecurityMockMvcRequestPostProcessors.user(username).password("password");
//    }

}