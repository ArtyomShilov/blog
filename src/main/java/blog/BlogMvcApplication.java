package blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "blog")
@SpringBootApplication
public class BlogMvcApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogMvcApplication.class, args);
    }
}