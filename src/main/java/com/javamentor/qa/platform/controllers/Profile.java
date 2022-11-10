package com.javamentor.qa.platform.controllers;


import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.impl.model.ReputationServiceImpl;
import com.javamentor.qa.platform.service.impl.model.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class Profile {

    private final UserServiceImpl userService;
    private final ReputationServiceImpl reputationService;


    public Profile(UserServiceImpl userService, ReputationServiceImpl reputationService) {
        this.userService = userService;
        this.reputationService = reputationService;

    }


    @GetMapping
    public String showProfile(Model model) {
        List<User> users = this.userService.getAll();
        User user = users.get(0);
        model.addAttribute("user", user);
        model.addAttribute("reputation", reputationService);
        return "profile";
    }
}