package com.example.email.utils;

public class EmailUtils {
    public static String getEmailMessage(String firstName, String lastName, String host) {
        return "Hello " + firstName + " " + lastName + ",\n\nThis is a test of the EmailUtils. I hope that this works how you wanted it to.\n\n" +
                "Drussie.";
    }

    //not using the below method..Add to above method for verification with token as an parameter
    private static String getVerificationUrl(String host) {
        return host + "api/vq/clients";
    }
}
