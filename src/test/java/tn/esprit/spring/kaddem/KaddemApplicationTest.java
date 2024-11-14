package tn.esprit.spring.kaddem; ;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(classes = KaddemApplication.class)
@ActiveProfiles("test")  // Only if "test" profile exists in your properties
class KaddemApplicationTest {
    @Test
    void contextLoads() {
        // Verifies that the application context loads successfully
    }
}