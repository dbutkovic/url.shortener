package com.infobip.assessment.url.shortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelpController {

    @RequestMapping("/help")
    public String index() {
        return "index";
    }

    @RequestMapping("/help/account")
    public String account() {
        return "account";
    }

    @RequestMapping("/help/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/help/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/help/statistic")
    public String statistic() {
        return "statistic";
    }
}
