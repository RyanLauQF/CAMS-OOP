package com.cmas.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles password security checking.
 *
 * @author Shao Chong
 * @version 1.0
 * @since 2023-11-24
 */
public class PasswordChecker {

    /**
     * @param password User inputted password
     * @return returns true if password is secure
     * @throws Exception Corresponding error messages that identify the problems in the given password
     */
    public static boolean isSecurePassword(String password) throws Exception {
        List<String> errors = new ArrayList<>();

        // Check length
        if (password.length() < 8) {
            errors.add("Password is too short. It must be at least 8 characters.");
        }

        // Check for at least one special character
        Pattern specialCharPattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher specialCharMatcher = specialCharPattern.matcher(password);
        if (!specialCharMatcher.find()) {
            errors.add("Password must contain at least one special character.");
        }

        // Check for a mix of alphanumeric characters
        boolean hasDigit = false;
        boolean hasLetter = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isLetter(c)) {
                hasLetter = true;
            }
        }

        if (!(hasDigit && hasLetter)) {
            errors.add("Password must contain a mix of alphanumeric characters.");
        }

        // If there are errors, throw a single exception with all error messages
        if (!errors.isEmpty()) {
            throw new Exception(String.join("\n", errors));
        }

        // Password meets all criteria
        return true;
    }
}