package me.kktrkkt.springsecurityexample.simple;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SimpleController {

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        if(principal != null) {
            model.addAttribute("message", "Weclome back, " + principal.getName());
        }

        return "simple/index";
    }
}
