package me.kktrkkt.springsecurityexample.security_filter.base_authentication_filter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SimpleController {

    @GetMapping("/")
    public String simplePage(Model model, Principal principal) {
        model.addAttribute("message", "hello, " + principal.getName());
        return "simple/index";
    }

}
