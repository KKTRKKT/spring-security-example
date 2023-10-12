package me.kktrkkt.springsecurityexample.simple.book;

import me.kktrkkt.springsecurityexample.simple.account.Account;
import me.kktrkkt.springsecurityexample.simple.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class BookRunner implements ApplicationRunner {

    @Autowired
    private BookRepository books;

    @Autowired
    private AccountRepository accounts;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account user = accounts.findByUsername("user").get();
        String springSecurity = "spring-security";
        Account admin = accounts.findByUsername("admin").get();
        String springData = "spring-data";
        books.save(createBook(user, springSecurity));
        books.save(createBook(admin, springData));
    }

    private Book createBook(Account user, String title) {
        Book book = new Book();
        book.setAuthor(user);
        book.setTitle(title);
        return book;
    }
}
