package emorymerryman;

import java.util.Random;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import static org.springframework.boot.SpringApplication.run;

/**
 * The application.
 **/
@SpringBootApplication
public class Application {
    /**
     * The seed of the randomness.
     **/
    private static final long SEED = 19800;

    /**
     * Providing a constructor is necessary for RESTAssured.
     **/
    public Application() {
    }

    /**
     * The entrypoint.
     *
     * @param args ignored
     **/
    public static void main(final String[] args) {
        run(Application.class, args);
    }

    /**
     * This is where the randomness is injected.
     *
     * @return the randomness.
     **/
    @Bean
    public Random random() {
        return new Random(SEED);
    }
}
