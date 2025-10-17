package com.prabhas;

import com.prabhas.models.entity.User;
import com.prabhas.repositories.UserRepository;
import com.prabhas.service.UserService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpringSecurityJwtDemoApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    static String username = "ci_testuser";
    static String email = "ci_testuser@example.com";

    static AtomicInteger testCounter = new AtomicInteger(0);
    static int totalTests = 4;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Total Test Cases to run: " + totalTests);
    }

    @Test
    @Order(1)
    void testAddUser() {
        int currentTest = testCounter.incrementAndGet();
        System.out.println("Running Test Case-" + currentTest + ": testAddUser");
        try {
            // Ensure no duplicate user exists from previous test runs
            userRepository.findByUsername(username).ifPresent(userRepository::delete);

            Set<String> roles = Set.of("ROLE_USER");

            User user = userService.createUser(
                    username,
                    "testpassword",
                    email,
                    "First",
                    "Last",
                    "9876543211",
                    roles
            );

            assertNotNull(user, "User should not be null after creation");
            assertEquals(username, user.getUsername(), "Username should match");

            System.out.println("Test Case-" + currentTest + " PASSED");
        } catch (AssertionError | Exception e) {
            System.out.println("Test Case-" + currentTest + " FAILED with error: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Order(2)
    void testUpdateUser() {
        int currentTest = testCounter.incrementAndGet();
        System.out.println("Running Test Case-" + currentTest + ": testUpdateUser");
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AssertionError("User not found for update"));

            user.setFirstName("Changed");
            user.setLastName("Name");
            User updated = userRepository.save(user);

            assertThat(updated.getFirstName()).isEqualTo("Changed");
            assertThat(updated.getLastName()).isEqualTo("Name");

            System.out.println("Test Case-" + currentTest + " PASSED");
        } catch (AssertionError | Exception e) {
            System.out.println("Test Case-" + currentTest + " FAILED with error: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Order(3)
    void testSearchUser() {
        int currentTest = testCounter.incrementAndGet();
        System.out.println("Running Test Case-" + currentTest + ": testSearchUser");
        try {
            Optional<User> userOpt = userRepository.findByUsername(username);

            assertTrue(userOpt.isPresent(), "User should exist in the database");
            assertEquals(username, userOpt.get().getUsername(), "Searched user should match username");

            System.out.println("Test Case-" + currentTest + " PASSED");
        } catch (AssertionError | Exception e) {
            System.out.println("Test Case-" + currentTest + " FAILED with error: " + e.getMessage());
            throw e;
        }
    }

    @Test
    @Order(4)
    void testDeleteUser() {
        int currentTest = testCounter.incrementAndGet();
        System.out.println("Running Test Case-" + currentTest + ": testDeleteUser");
        try {
            userRepository.deleteByUsername(username);
            Optional<User> deleted = userRepository.findByUsername(username);

            assertTrue(deleted.isEmpty(), "User should be deleted from the database");

            System.out.println("Test Case-" + currentTest + " PASSED");
        } catch (AssertionError | Exception e) {
            System.out.println("Test Case-" + currentTest + " FAILED with error: " + e.getMessage());
            throw e;
        }
    }
}
