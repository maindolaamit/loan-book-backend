package org.hayo.finance.loanbook.utils;

import java.util.Random;

public class RegistrationUtility {
    private static final String VERIFICATION_URL = "http://localhost:8080/api/auth/register/verify?email=";
    private static final String REGISTERED_USER_URL = "http://localhost:8080/user?id=";

    public static String generateVerificationCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            codeBuilder.append(randomChar);
        }
        return codeBuilder.toString();
    }

//    public static String getVerificationCode() {
//        // random text code for verification
//        return generateVerificationCode();
//    }

    public static String getVerificationUrl(String email, String token) {
        return VERIFICATION_URL + email + "&token=" + token;
    }

//    public static String getRegistredUserUrl(String id) {
//        return REGISTERED_USER_URL + id;
//    }
}
