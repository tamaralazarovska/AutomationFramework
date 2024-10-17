package utils;

import java.util.Random;

public class HelpersMethods {
    public static String generateRandomNumber(int length){
        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();

        // Generate the first digit separately to avoid leading zero
        randomNumber.append(random.nextInt(9) + 1); // 1-9 for the first digit

        // Generate the rest of the digits
        for (int i = 1; i < length; i++) {
            randomNumber.append(random.nextInt(10)); // 0-9 for the remaining digits
        }

        String number = randomNumber.toString();
        return number;
    }

    public static String generateRandomEmail(){
        String email = "tamara_@";
        String number = generateRandomNumber(3);
        email = email.concat(number).concat("mail.com");
        return email;
    }
}

