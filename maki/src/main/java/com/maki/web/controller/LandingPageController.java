package com.maki.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingPageController {

  @GetMapping("/")
  public String landingPage() {
    return "landing_page";
  }
}
