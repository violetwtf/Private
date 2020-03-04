package wtf.violet.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring application behind bot. Please don't ever touch this.
 * @author Violet M. vi@violet.wtf
 */
@SpringBootApplication
@EnableJpaRepositories
public class BotApplication {

  public static void main(String[] args) {
    SpringApplication.run(BotApplication.class, args);
  }

}
