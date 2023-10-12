package me.kktrkkt.springsecurityexample.simple;

import me.kktrkkt.springsecurityexample.simple.account.Account;
import me.kktrkkt.springsecurityexample.simple.account.CurrentUser;
import me.kktrkkt.springsecurityexample.simple.account.UserAccount;
import me.kktrkkt.springsecurityexample.simple.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SimpleController {

    @Autowired
    private BookRepository books;

    @GetMapping("/")
    public String home(Model model, @CurrentUser Account account) {
        if (account != null) {
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
        model.addAttribute("message", "Hello" + account.getUsername());
        return "simple/admin";
    }

    @GetMapping("/user")
    public String user(Model model, @CurrentUser Account account){
        model.addAttribute("message","Hello"+account.getUsername());
        model.addAttribute("books", books.findCurrentUserBooks());
        return "/simple/user";
    }
}
