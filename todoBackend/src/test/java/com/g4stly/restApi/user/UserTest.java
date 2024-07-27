package com.g4stly.restApi.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUser() {
        User user = new User(1, "valid_username", "password", false);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isEmpty();
    }

    @Test
    void testUsernameTooShort() {
        User user = new User(1, "ab", "password", false);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Username must be between 3 and 20 characters long.");
    }

    @Test
    void testUsernameTooLong() {
        User user = new User(1, "a".repeat(21), "password", false);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Username must be between 3 and 20 characters long.");
    }

    @Test
    void testUsernameInvalidCharacters() {
        User user = new User(1, "invalid_username!", "password", false);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Username can only contain letters, numbers, underscores, or dots");
    }

    @Test
    void testUsernameConsecutiveUnderscores() {
        User user = new User(1, "invalid__username", "password", false);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Username must not contain consecutive dots or underscores");
    }

    @Test
    void testUsernameStartsWithUnderscore() {
        User user = new User(1, "_invalidusername", "password", false);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Username must not start or end with a dot or underscore");
    }

    @Test
    void testPasswordNotNull() {
        User user = new User(1, "valid_username", null, false);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("boş değer olamaz");
    }
}
