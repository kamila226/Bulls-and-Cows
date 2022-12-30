package bullscows;
import java.util.Scanner;
import java.util.Random;
public class Main {
    public static int checkBulls(String secretCode, String answer) {
        int bulls = 0;
        for (int i = 0; i < secretCode.length(); i++) {
            if (secretCode.charAt(i) == answer.charAt(i)) {
                bulls++;
            }
        }
        return bulls;
    }

    public static int checkCows(String secretCode, String answer) {
        int cows = 0;
        for (int i = 0; i < secretCode.length(); i++) {
            for (int j = 0; j < secretCode.length(); j++) {
                if (secretCode.charAt(i) == answer.charAt(j) && i != j) {
                    cows++;
                }
            }
        }
        return cows;
    }

    public static String generateRandom(int length, int possibleChars) {
        Random rand = new Random();
        char[] symbols = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        int randPosition;
        StringBuilder secretCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randPosition = rand.nextInt(possibleChars);
            while (secretCode.toString().contains(String.valueOf(symbols[randPosition]))) {
                randPosition = rand.nextInt(possibleChars);
            }
            secretCode.append(symbols[randPosition]);
        }
        System.out.printf("The secret code is prepared: %s ", "*".repeat(length));
        String range;
        if (possibleChars <= 10) {
            range = "0-" + String.valueOf(symbols[possibleChars-1]);
        } else {
            range = "0-9, a";
            if (possibleChars > 11) {
                range += "-" + symbols[possibleChars-1];
            }
        }
        System.out.printf(" (%s).%n", range);
        return secretCode.toString();
    }

    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");
        Scanner scan = new Scanner(System.in);
        int length;
        if (scan.hasNextInt()) {
            length = scan.nextInt();
        } else {
            String notValid = scan.next();
            System.out.printf("Error: %s isn't a valid number.", notValid);
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        int possibleChars;
        if (scan.hasNextInt()) {
            possibleChars = scan.nextInt();
        } else {
            String notValid = scan.next();
            System.out.printf("Error: %s isn't a valid number.", notValid);
            return;
        }
        if (possibleChars > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        } else if (possibleChars < 1 || length < 1) {
            System.out.println("Error: the length and the number of possimble symbols must be greater than 0");
            return;
        } else if (length > possibleChars) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d " +
                    "with %d unique symbols.", length, possibleChars);
            return;
        }

        String secretCode = generateRandom(length, possibleChars);

        System.out.println("\nOkay, let's start a game!");
        System.out.println("Turn 1:");
        String guess = scan.next();
        int numCows = checkCows(secretCode, guess);
        int numBulls = checkBulls(secretCode, guess);
        String cows, bulls, and;
        int turnCounter = 1;
        while (numBulls != length) {
            if (numCows != 0 || numBulls != 0) {
                cows = numCows == 0? "" : numCows == 1? numCows + " cow" : numCows + " cows";
                bulls = numBulls == 0? "" : numBulls == 1? numBulls + " bull" : numBulls + " bulls";
                and = numBulls != 0 && numCows != 0? " and " : "";
                System.out.printf("Grade: %s%s%s.", bulls, and, cows);
            } else {
                System.out.println("None.");
            }
            turnCounter++;
            System.out.printf("%nTurn %d:%n", turnCounter);
            guess = scan.next();
            numCows = checkCows(secretCode, guess);
            numBulls = checkBulls(secretCode, guess);
        }
        System.out.printf("Grade: %d bulls%n", numBulls);
        System.out.println("Congratulations! You guessed the secret code.");
        scan.close();
    }
}
