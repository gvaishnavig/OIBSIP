import java.util.*;

class User {
  private String username;
  private String password;
  private String fullname;
  
  public User(String username, String password, String fullname) { // Initializing user
    this.username = username;
    this.password = password;
    this.fullname = fullname;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public String getFullName()
  {
    return fullname;
  }
}

class Question {
  private String questions;
  private List<String> options;
  private int correctOptionIndex;
  
  public Question(String questions, List<String> options, int correctOptionIndex) // Initialize questions
    {
        this.questions = questions;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestions()
    {
        return questions;
    }

    public List<String> getOptions()
    {
        return options;
    }

    public int getCorrectOptionIndex()
    {
      return correctOptionIndex;
    }
}

public class OnlineExam {
  private static User currentUser;
  private static List<Question> questions;
  private static List<Integer> selectedAnswers;
  private static Timer timer;
  private static int remainingTimeInSecs = 900; // 15 minutes
  
  public static void main(String args[]) {
    initializeQuestions();
    login();
  }

  private static void login() { // User login
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter your full name: ");
    String fullname = scanner.nextLine();
    System.out.print("Enter your username: ");
    String username = scanner.nextLine();
    System.out.print("Enter your password: ");
    String password = scanner.nextLine();
    currentUser = new User(username, password, fullname);

    if (currentUser != null) {
      showMainMenu(scanner);
    }
    else {
      System.out.println("Login failed. Please try again.");
      login();
    }
  }

  private static void initializeQuestions() { // Questions with options and answers
    questions = new ArrayList<>();
    questions.add(new Question("The tropic of cancer does not pass through which of these Indian states ?", List.of("Madhya Pradesh", "West Bengal", "Rajasthan", "Odisha"), 3));
    questions.add(new Question("Fathometer is used to measure:", List.of("Earthquakes", "Rainfall", "Ocean depth", "Sound intensity"), 2));
    questions.add(new Question("The nuclear radiation unit is:", List.of("Pascal", "Rankine", "Reaumur", "Roentgen"),3));
    questions.add(new Question("The human heart is:", List.of("Neurogenic", "Myogenic", "Pulsating", "Ampullary"),1));
    questions.add(new Question("Which material is used in solar cells?", List.of("Silicon", "Copper", "Zinc", "Silver"),0));
    questions.add(new Question("The metal whose salts are sensitive to light is?", List.of("Zinc", "Silver", "Aluminium", "Copper"),3));
    questions.add(new Question("MS-Word is an example of:", List.of("Operating system", "Processing device", "Application software", "Input device"),2));
    questions.add(new Question("Which of the following is the most dangerous type of radiation?", List.of("Alpha", "Beta", "Gamma", "None"),0));
    questions.add(new Question("The Pyranometer measures:", List.of("Direct radiation", "Diffusion radiaton", "Both", "None"),2));
    questions.add(new Question("The source of energy used for satellites is:", List.of("Edison cells", "Fuel cells", "Solar cells", "Cryogenic cells"),2));
  }

  private static void showMainMenu(Scanner scanner) { // Function for main menu
    System.out.println("Welcome, " + currentUser.getFullName() + "!");
    System.out.println("1. Start Exam");
    System.out.println("2. Update Profile");
    System.out.println("3. Change Password");
    System.out.println("4. Logout");

    int choice = scanner.nextInt();
    scanner.nextLine();

    switch (choice)
    {
      case 1:
        startExam(scanner);
        break;
      case 2:
        updateProfile(scanner);
        break;
      case 3:
        changePassword(scanner);
        break;
      case 4:
        logout(scanner);
        break;
      default:
        System.out.println("Invalid choice. Please try again.");
        showMainMenu(scanner);
        break;
    }
  }

  private static void startExam(Scanner scanner) { // Function to start exam
    selectedAnswers = new ArrayList<>();
    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask()
    {
      @Override
      public void run() {
        remainingTimeInSecs--;
        if (remainingTimeInSecs <= 0) { // If timer is completed, exam gets auto submitted
          autoSubmit();
        }
      }
    }, 1000, 1000);
        
    System.out.println("You have got 15 minutes to complete the exam.");
    for (int i = 0; i < questions.size(); i++) {
      Question question = questions.get(i);
      System.out.println("Question " + (i + 1) + ": " + question.getQuestions());
      List<String> options = question.getOptions();
      for (int j = 0; j < options.size(); j++) {
        System.out.println((j + 1) + ". " + options.get(j));
      }
      System.out.print("Select an answer (1-" + options.size() + "): ");
      int answer = scanner.nextInt();
      selectedAnswers.add(answer - 1);
    }
    autoSubmit();
  }

  private static void autoSubmit() { // Auto submits after timer ends
    if (timer != null) {
      timer.cancel();
    }
    System.out.println("Your time's up! Submitting your answers.");
    showResult();
  }

  private static void showResult() { // Display results
    int score = 0;
    for (int i = 0; i < questions.size(); i++) {
      Question question = questions.get(i);
      int selectedAnswerIndex = selectedAnswers.get(i);
      if (selectedAnswerIndex == question.getCorrectOptionIndex()) {
        score++;
      }
    }

    System.out.println("You scored " + score + " out of " + questions.size() + " questions."); //display results
    logout(new Scanner(System.in)); // Automatic logout
  }

  private static void updateProfile(Scanner scanner) {
    System.out.print("Enter your new full name: ");
    String newFullName = scanner.nextLine();
    currentUser = new User(currentUser.getUsername(), currentUser.getPassword(), newFullName);
    System.out.println("Profile updated successfully.");
    showMainMenu(scanner);
  }

  private static void changePassword(Scanner scanner) {
    System.out.print("Enter your current password: ");
    String currentPassword = scanner.nextLine();
    if (currentPassword.equals(currentUser.getPassword())) {
      System.out.print("Enter your new password: ");
      String newPassword = scanner.nextLine();
      currentUser = new User(currentUser.getUsername(), newPassword, currentUser.getFullName());
      System.out.println("Password changed successfully.");
    }
    else {
      System.out.println("Incorrect current password. Please try again.");
    }
    showMainMenu(scanner);
  }

  private static void logout(Scanner scanner) {
    System.out.println("Logging out. Thank you for taking the exam!");
    scanner.close();
    System.exit(0);
  }
}
