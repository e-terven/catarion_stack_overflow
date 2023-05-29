package com.javamentor.qa.platform.webapp.controllers.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class View {
    @GetMapping("/profile")
    public String showProfile (){
        return "profile";
    }
}
