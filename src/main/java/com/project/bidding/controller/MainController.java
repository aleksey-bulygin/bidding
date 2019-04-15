package com.project.bidding.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @GetMapping
    public String main(Model model) {
        HashMap<String, Object> data = new HashMap<>();

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

            data.put("profile", userDetails);
        } catch (Exception e) {
            data.put("profile", null);
        }

        model.addAttribute("frontendData", data);

        model.addAttribute("isDevMode", true);

        return "index";
    }
}
