package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Random;

// BEGIN
import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.RequestScope;
// END

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @Bean
    @RequestScope
    public Daytime getDaytime() {
        //var hour = new Random().nextInt(24) + 1;
        var hour = LocalDateTime.now().getHour();
        System.out.println("Hour: " + hour);
        if (hour > 6 && hour < 22) return new Day();
        else return new Night();
    }
    // END
}
