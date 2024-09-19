package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@SpringBootApplication
@RestController
class Application  {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/about1")
    public String hello1() {
        return "Welcome to Hexlet!!!!!!";

    }

    @GetMapping("/about2")
    public String hello2() {
        return "Welcome to Hexlet!!!";

    }

    @GetMapping("/about")
    public String hello() {
        return "Welcome to Hexlet!";

    }
}
// END
