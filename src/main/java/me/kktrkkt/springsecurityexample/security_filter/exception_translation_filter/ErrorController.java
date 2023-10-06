package me.kktrkkt.springsecurityexample.security_filter.exception_translation_filter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/access-denied")
    public String error(Model model) {
        model.addAttribute("message", "not allowed");
        return "simple/info";
    }

}
