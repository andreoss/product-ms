package org.sdf;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Integration test.
 */
@SpringBootTest
@ActiveProfiles("tc")
@Testcontainers
class ProductMsApplicationITCase {

    @Test
    void contextLoads() {
    }

}
