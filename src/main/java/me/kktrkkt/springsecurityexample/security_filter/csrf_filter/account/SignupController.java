package me.kktrkkt.springsecurityexample.security_filter.csrf_filter.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String signupForm(Model model) {
        model.addAttribute("account", new Account());
        return "security_filter/csrf_filter/signup";
    }

    @PostMapping
    public String signup(@ModelAttribute Account account) {
        account.setRole("USER");
        accountService.createUser(account);
        return "redirect:/";
    }
}
