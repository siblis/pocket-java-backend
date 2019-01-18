package ru.geekbrains.pocket.backend.util;

import java.util.Random;

public abstract class RandomStringUtil {

    public static String randomString(int length){
        if (length>0) {
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = length;
            Random random = new Random();
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }
            String generatedString = buffer.toString();
            return generatedString;
        } else throw new ArrayStoreException();
    }
}
