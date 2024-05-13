import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {
  private static int start = 1;
  private static int end = 100;
  private static int rounds = 0;
  private static int maxAttempts = 5;
  private static int numAttempts = 0;
  private static int points = 0;

  private static int generateRandomNumber(int start, int end) {
    Random random = new Random();
    int randomNumber = random.nextInt(end) + start;
    return randomNumber;
  }

  private static int getUserGuess(Scanner sc) {
    System.out.print("Enter your guess: ");
    try {
      return sc.nextInt();
    } catch (Exception e) {
      System.out.println("\nInvalid input! Please enter a valid number.\n");
      sc.next();
      return getUserGuess(sc);
    }
  }

  private static int play(int number, int maxAttempts, Scanner sc) {
    int attempts = 0;

    while (attempts < maxAttempts) {               
        int userGuess = getUserGuess(sc);
        attempts++;

        String result = compareGuess(number, userGuess);
        System.out.println(result);

        if (result.equals("Correct")) {
            System.out.println("\nCongratulations! You guessed the correct number in " + attempts + " attempts.");
            return attempts;
        }
    }

    System.out.println(
                "\nSorry, you've reached the maximum number of attempts. The correct number was " + number + ".");
        return attempts;
    }

    private static String compareGuess(int number, int userGuess) {
      if (userGuess == number) {
          points++;
          return "Correct";
      } else if (userGuess < number)
          return "Too Low";
      else
          return "Too High";
  }

  private static void displayScore() {
    System.out.println("\n=====GAME OVER=====");
    System.out.println("Score Points: " + (points * 10));
    System.out.println("Total rounds played: " + rounds);
    System.out.println("Total attempts taken: " + numAttempts);
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean playAgain = true;

    while (playAgain) {
        rounds++;
        int number = generateRandomNumber(start, end);
        System.out.println("\n===== Round " + rounds + " =====");

        int totalAttempts = play(number, maxAttempts, scanner);
        numAttempts += totalAttempts;

        System.out.println("\nDo you want to play again? : (yes/no)");
        String response = scanner.next();
        playAgain = response.equalsIgnoreCase("yes");
    }

    displayScore();
    scanner.close();
  }

}
