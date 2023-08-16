package EWalletBetaApp.utils;

import java.util.Random;
import java.util.UUID;

public class Generate {
    public static String generateReferenceNumber(){
        Random random = new Random();
       StringBuilder referenceNumber = new StringBuilder();
       String character = "abcdefghijklmnopqrstuvwsyz0123456789";
        for (int i = 0; i < 9; i++) {
            int id = random.nextInt(character.length());
            char reference = character.charAt(id);
            referenceNumber.append(reference);
        }
        return referenceNumber.toString();
    }

}
