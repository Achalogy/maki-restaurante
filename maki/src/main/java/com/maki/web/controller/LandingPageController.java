package com.maki.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LandingPageController {

  @GetMapping("/test")
  public String landingPage() {
    return "landing_page";
  }
}
