package me.kktrkkt.springsecurityexample.simple;

import me.kktrkkt.springsecurityexample.simple.account.Account;
import me.kktrkkt.springsecurityexample.simple.account.CurrentUser;
import me.kktrkkt.springsecurityexample.simple.account.UserAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SimpleController {

    @GetMapping("/")
    public String home(Model model, @CurrentUser Account account) {
        if(account != null) {
            model.addAttribute("message", "Weclome back, " + account.getUsername());
        }

        return "simple/index";
    }

    @GetMapping("/info")
    public String info(Model model) {
        return "simple/info";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, @CurrentUser Account account) {
        model.addAttribute("message", "Hello" + account.getUsername());
        return "simple/dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model, @CurrentUser Account account) {
        model.addAttribute("message", "Hello" + account.getName());
        return "simple/admin";
    }
}
