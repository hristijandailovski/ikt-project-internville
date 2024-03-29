package finki.ikt.tim1.internville;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class InternvilleApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternvilleApplication.class, args);
    }

}
