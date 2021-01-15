import java.util.*;
import java.text.SimpleDateFormat;

/**
 * The MenuController class is used to handle inputs/outputs and display menus to the user.
 * Essentially, the MenuController is the 'Driver' of the application which brings all the classes and methods together.
 *
 * @author Tony Naughton
 * @version 1.0 (06.Jun.2020)
 */

public class MenuController {

    private Scanner input;
    private GymAPI gym;
    private Member member;
    private StudentMember studentMember;
    private Trainer trainer;
    private LinkedHashMap<String, String> packages;
    private ArrayList<Member> members;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");

    /**
     * main method which calls the MenuController constructor and runs it.
     *
     * @param args
     */
    public static void main(String[] args) {
        MenuController app = new MenuController();
        app.run();
    }

    /**
     * Constructor for the MenuController class.
     * Creates instances of Scanner, GymAPI and LinkedHashMap classes.
     */
    public MenuController() {
        input = new Scanner(System.in);
        gym = new GymAPI();
        packages = new LinkedHashMap<String, String>();
        members = gym.getMembers();
    }

    /**
     * Method which is called to run the program. Contains calls to many methods from within the MenuController class
     * as well as methods from external classes.
     */
    private void run() {

        //Method which loads from XML file
        loadXML();
        //Method which adds packages to the packages field
        addPackages();

        /* The registrationStatus method is assigned to the isRegistered variable
        which checks if the user is already registered or if they are a new user. */
        boolean isRegistered = registrationStatus();
        /* The userType method is assigned to the userType variable which checks if the user is a Member or a Trainer. */
        String userType = userType();

        /*If else statement directs the user to the correct menu depending on whether they are registered or not,
        and whether they are a member or a trainer.*/
        if (isRegistered) {
            login(userType);
        } else {
            register(userType);
        }

        if (userType == "Trainer") {
            int option = trainerMenu();
            //As long as the user does not input 0, continue to call the trainerMenu method
            while(option != 0) {
                //switch statement for handling the user input
                switch (option) {
                    case 1:
                        addMember();
                        break;
                    case 2:
                        System.out.println(gym.listMembers());
                        break;
                    case 3:
                        System.out.println("Enter member email: ");
                        input.nextLine(); //scanner bug
                        String memberEmail = input.nextLine();
                        System.out.println("");
                        Member searchedMember = gym.searchMembersByEmail(memberEmail);
                        //If a member was found, print the members details using the toString method
                        if (searchedMember != null) {
                            System.out.println(searchedMember.toString());
                        } else {
                            System.out.println("No member found");
                        }
                        break;
                    case 4:
                        int subOption = trainerSubMenu();
                        //As long as the user does not input 0, continue to call the trainerSubMenu method
                        while(subOption != 0) {
                            //switch statement for handling the user input
                            switch (subOption) {
                                case 1: //Adding a new Assessment
                                    //Initialising Assessment fields
                                    String assessedMemberEmail = "";
                                    float assessmentWeight = 0.00f;
                                    float assessmentThigh = 0.00f;
                                    float assessmentWaist = 0.00f;
                                    String assessmentComment = "";

                                    boolean goodInput = false;
                                    //Only proceed if goodInput is true i.e. a Member exists with the entered email
                                    while (!goodInput) {
                                        try {
                                            System.out.println("Enter member email: ");
                                            input.nextLine(); //scanner bug
                                            assessedMemberEmail = input.nextLine();
                                            Member member = gym.searchMembersByEmail(assessedMemberEmail);
                                            if (member == null) {
                                                System.out.println("This not a valid member email - please enter another");
                                            } else {
                                                goodInput = true;
                                            }
                                        }
                                        catch (Exception e) {
                                            System.out.println("Error searching for Member by email: " + e);
                                        }
                                    }
                                    //https://www.javatpoint.com/java-simpledateformat [1]
                                    Date currentDate = new Date();
                                    /* Assigning the current date to the date variable
                                    'formatter' allows for the date to be formatted to DD/MM/YY */
                                    String date = formatter.format(currentDate);

                                    //Only proceed if goodWeight is true
                                    boolean goodWeight = false;
                                    while (!goodWeight) {
                                        System.out.println("Weight (kg): ");
                                        try {
                                            assessmentWeight = input.nextFloat();
                                            if ((assessmentWeight < 35.0) || (assessmentWeight > 250.0)) {
                                                System.out.println("Starting Weight must be between 35kg and 250kg.");
                                            } else {
                                                goodWeight = true;
                                            }
                                        }
                                        catch (Exception e) {
                                            System.out.println("Error setting weight value: " + e);
                                            input.nextLine();
                                        }
                                    }

                                    //Only proceed if goodThigh is true
                                    boolean goodThigh = false;
                                    while (!goodThigh) {
                                        try {
                                            System.out.println("Thigh (cm): ");
                                            input.nextLine(); //scanner bug
                                            assessmentThigh = input.nextFloat();
                                            goodThigh = true;
                                        }
                                        catch (Exception e) {
                                            System.out.println("Error setting thigh value: " + e);
                                        }
                                    }

                                    //Only proceed if goodWaist is true
                                    boolean goodWaist = false;
                                    while (!goodWaist) {
                                        input.nextLine(); //scanner bug
                                        try {
                                            System.out.println("Waist (cm): ");
                                            assessmentWaist = input.nextFloat();
                                            goodWaist = true;
                                        }
                                        catch (Exception e) {
                                            System.out.println("Error setting waist value: " + e);
                                        }
                                    }

                                    //Only proceed if okComment is true
                                    boolean okComment = false;
                                    while (!okComment) {
                                        input.nextLine();
                                        try {
                                            System.out.println("Comment: ");
                                            assessmentComment = input.nextLine();
                                            //Ensuring the new comment contains something
                                            if (assessmentComment == "") {
                                                System.out.println("Your comment must contain something!");
                                            } else {
                                                okComment = true;
                                            }
                                        }
                                        catch (Exception e) {
                                            System.out.println("Error entering comment: " + e);
                                        }
                                    }
                                    //Create new Assessment object from the inputted data
                                    Assessment assessment = new Assessment(assessmentWeight, assessmentThigh, assessmentWaist, assessmentComment);
                                    //Search for the member from the email provided
                                    Member assessedMember = gym.searchMembersByEmail(assessedMemberEmail);
                                    //Add the Assessment to the member's assessment HashMap
                                    assessedMember.assessments.put(date, assessment);
                                    System.out.println("Assessment added successfully");
                                    System.out.println("");
                                    break;
                                case 2: //Updating a comment on an Assessment
                                    Member specifiedMember = null;
                                    boolean goodEmail = false;
                                    while (!goodEmail) {
                                        System.out.println("Enter member email: ");
                                        input.nextLine(); //scanner bug
                                        specifiedMember = gym.searchMembersByEmail(input.nextLine());
                                        if (specifiedMember != null) {
                                            goodEmail = true;
                                        } else {
                                            System.out.println("Email invalid - enter another email");
                                        }
                                    }
                                    //Checking if the member already has assessments
                                    if (specifiedMember.getAssessments().size() == 0) {
                                        System.out.println("This member has no assessments recorded yet");
                                        System.out.println("");
                                    } else {
                                        HashMap memberAssessments = specifiedMember.getAssessments();
                                        SortedSet<String> sortedDates = specifiedMember.sortedAssessmentDates();
                                        String assessmentDate = "";
                                        String updatedComment = "";
                                        //Print out sortedDates set so that Trainer can decide which Assessment they want to update a comment for
                                        System.out.println(sortedDates);
                                        boolean goodDate = false;
                                        while (!goodDate) {
                                            System.out.println("Enter the date of the assessment you would like to update (DD/MM/YY): ");
                                            assessmentDate = input.nextLine();
                                            //Ensure the date entered matches an existing assessment
                                            if (memberAssessments.get(assessmentDate) != null) {
                                                goodDate = true;
                                            } else {
                                                System.out.println("Invalid date - enter an existing assessment date");
                                            }
                                        }
                                        //Get the chosen Assessment using the date specified (assessmentDate)
                                        Assessment chosenAssessment = specifiedMember.assessments.get(assessmentDate);
                                        boolean goodComment = false;
                                        //Ensure comment contains something
                                        while (!goodComment) {
                                            System.out.println("Enter new comment for assessment: ");
                                            updatedComment = input.nextLine();
                                            if (updatedComment == "") {
                                                System.out.println("Your comment must contain something!");
                                            } else {
                                                goodComment = true;
                                            }
                                        }
                                        try {
                                            //Update the comment in the chosenAssessment to the newly entered comment
                                            chosenAssessment.setComment(updatedComment);
                                            //Put the chosenAssessment back in the memberAssessments HashMap
                                            memberAssessments.put(assessmentDate, chosenAssessment);
                                            System.out.println("Comment updated successfully");
                                            System.out.println("");
                                        }
                                        catch (Exception e) {
                                            System.out.println("Error updating comment: " + e);
                                            System.out.println("");
                                        }
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid option selected");
                            }
                            subOption = trainerSubMenu();
                        }
                }
                System.out.println("");
                option = trainerMenu();
            }
        } else if (userType == "Member") {
            int option = memberMenu(); //Displaying Member menu
            while (option != 0) { //As long as the user does not input 0, continue to call the memberMenu method
                switch (option) {
                    case 1:
                        //Outputting the Member's details as a String
                        System.out.println(member.toString());
                        break;
                    case 2: //Updating the Member's details
                        try {
                            System.out.println("Please enter your name: ");
                            input.nextLine();
                            member.setName(input.nextLine());
                            System.out.println("Please enter your address: ");
                            member.setAddress(input.nextLine());
                            System.out.println("Please enter your gender ('M' or 'F'): ");
                            member.setGender(input.nextLine().toUpperCase());
                            System.out.println("Please enter your height (m): ");
                            member.setHeight(input.nextFloat());
                            System.out.println("Please enter your weight (kg): ");
                            member.setStartWeight(input.nextFloat());
                            member.setChosenPackage(displayPackages(packages));
                            System.out.println("");
                            System.out.println("Details updated successfully");
                        }
                        catch (Exception e) {
                            System.out.println("Error updating details: " + e);
                        }
                        break;
                    case 3:
                        int choice = memberSubMenu(); //Display member sub menu
                        while (choice != 0) {
                            switch(choice) {
                                case 1:
                                    //Displaying Assessments in order of weight
                                    System.out.println(member.getAssessmentsByWeight());
                                    break;
                                case 2:
                                    //Displaying Assessments in order of waist size
                                    System.out.println(member.getAssessmentsByWaist());
                                    break;
                            }
                            choice = memberSubMenu();
                        }
                        System.out.println("");
                        break;
                }
                System.out.println("");
                option = memberMenu();
            }
        }
        saveXML(); //Save to XML file upon exiting the application
    }

    /**
     * Method used to check if the user is already registered
     * or if they are required to create a new account
     *
     * @return whether the user is already registered or not
     */
    private boolean registrationStatus() {
        boolean result = false;
        boolean goodValue = false;

        while (!goodValue) {
            try {
                System.out.println("Welcome to the Gym App");
                System.out.println("Press 'l' to login");
                System.out.println("Press 'r' to register");
                System.out.println("===> ");
                char login = input.nextLine().charAt(0);
                if (login == 'l') {
                    result = true;
                    goodValue = true;
                } else if (login == 'r') {
                    result = false;
                    goodValue = true;
                }
            }
            catch (Exception e) {
                input.nextLine();
                System.out.println("Characters 'l' (Login) or 'r' (Register) expected.");
            }
        }
        return result;
    }

    /**
     * Method used to determine whether the user is a Member or a Trainer
     *
     * @return String of "Member" or "Trainer"
     */
    private String userType() {
        String userType = "";
        boolean goodValue = false;

        while (!goodValue) {
            try {
                System.out.println("Enter 'm' if you are a member");
                System.out.println("Enter 't' if you are a trainer");
                char type = input.nextLine().charAt(0);
                if (type == 'm') {
                    userType = "Member";
                    goodValue = true;
                } else if (type == 't') {
                    userType = "Trainer";
                    goodValue = true;
                } else {
                    System.out.println("Characters 'm' (Member) or 't' (Trainer) expected.");
                }
            }
            catch (Exception e) {
                input.nextLine();
                System.out.println("Characters 'm' (Member) or 't' (Trainer) expected.");
            }
        }
        return userType;
    }

    /**
     * Method used to log in a member or a trainer
     *
     * @param userType Takes in "Member" or "Trainer" as a String
     */
    private void login(String userType) {

        System.out.println("Please enter your email address: ");
        String email = input.nextLine();

        if (userType == "Member") {
            Member result = gym.searchMembersByEmail(email);
            if (result != null) {
                member = result;
            } else {
                System.out.println("Access Denied");
                System.exit(0);
            }
        } else if (userType == "Trainer") {
            Trainer result = gym.searchTrainersByEmail(email);
            if (result != null) {
                trainer = result;
            } else {
                System.out.println("Access Denied");
                System.exit(0);
            }
        }
        System.out.println("Login successful");
        System.out.println("");
    }

    /**
     * Method used to register a new user
     *
     * @param userType Takes in "Member" or "Trainer" as a String
     */
    private void register(String userType) {

        //emailEntry method called to get the user's email address
        String userEmail = emailEntry();
        System.out.println("Enter name: ");
        String name = "";
        boolean goodName = false;
        while (!goodName) {
            try {
                name = input.nextLine();
                goodName = true;
            }
            catch (Exception e) {
                System.out.println("Unexpected email input - String expected.");
                input.nextLine(); //Scanner bug
            }
        }
        System.out.println("Enter address: ");
        String address = "";
        boolean goodAddress = false;
        while (!goodAddress) {
            try {
                address = input.nextLine();
                goodAddress = true;
            }
            catch (Exception e) {
                System.out.println("Unexpected address input - String expected. ");
                input.nextLine(); //Scanner bug
            }
        }
        System.out.println("Enter gender ('M' or 'F'): ");
        String gender = "";
        boolean goodGender = false;
        while (!goodGender) {
            try {
                gender = input.nextLine().toUpperCase();
                goodGender = true;
            }
            catch (Exception e) {
                System.out.println("Unexpected gender input - String expected.");
                input.nextLine(); //Scanner bug
            }
        }
        if (userType == "Member") {
            float height = 0.00f;
            boolean goodHeight = false;
            while (!goodHeight) {
                System.out.println("Enter height (m): ");
                try {
                    height = input.nextFloat();
                    //Ensuring height entered is between 1 and 3 metres
                    if ((height < 1.0) || (height > 3.0)) {
                        System.out.println("Height must be between 1 and 3 metres.");
                    } else {
                        goodHeight = true;
                    }
                }
                catch (Exception e) {
                    System.out.println("Input must be an integer between 1.0 and 3.0.");
                    input.nextLine(); //Scanner bug
                }
            }
            float startWeight = 0.00f;
            boolean goodWeight = false;
            while (!goodWeight) {
                System.out.println("Enter starting weight (kg): ");
                try {
                    startWeight = input.nextFloat();
                    if ((startWeight < 35.0) || (startWeight > 250.0)) {
                        //Ensuring weight is between 35 and 250 kg
                        System.out.println("Starting Weight must be between 35kg and 250kg.");
                    } else {
                        goodWeight = true;
                    }
                }
                catch (Exception e) {
                    System.out.println("Input must be an integer between 35.0 and 250.0.");
                    input.nextLine(); //Scanner bug
                }
            }
            boolean goodInput = false;
            while (!goodInput) {
                try {
                    //Checking if the user is a student
                    System.out.println("Are you a student? (Y/N)");
                    input.nextLine();
                    char response = input.nextLine().charAt(0);
                    if (response == 'Y') { //If the user responds with 'Y', the user is asked for their studentId and collegeName
                        goodInput = true;
                        System.out.println("Please enter your student ID: ");
                        int studentId = input.nextInt();
                        System.out.println("Please enter the name of your college: ");
                        input.nextLine();
                        String collegeName = input.nextLine();
                        /* The StudentMember constructor is called and the data inputted by the user is passed into the constructor.
                        * The user's collegeName is passed in as the chosen package */
                        gym.addMember(new StudentMember(userEmail, name, address, gender, height, startWeight, collegeName, studentId, collegeName));
                        //The member field is set to the new member upon completion of registration
                        member = gym.searchMembersByEmail(userEmail);
                    } else if (response == 'N') { //If the user is not a student, the package options are then displayed
                        goodInput = true;
                        //The chosenPackage field holds the package that the user chooses upon viewing the displayPackages method
                        String chosenPackage = displayPackages(packages);
                        //The Member constructor is called and the data inputted by the user is passed in
                        gym.addMember(new Member(userEmail, name, address, gender, height, startWeight, chosenPackage));
                        //The member field is set to the new member upon completion of registration
                        member = gym.searchMembersByEmail(userEmail);
                    } else {
                        System.out.println("Characters 'Y' or 'N' expected, you entered something else");
                        System.out.println("");
                    }
                }
                catch (Exception e) {
                    System.out.println("Registration error occurred: " + e);
                }
            }
        } else if (userType == "Trainer") {
            //If the user registering is a Trainer, they are asked for their speciality
            System.out.println("Please enter your speciality: ");
            String speciality = input.nextLine();
            //Trainer constructor is then called
            gym.addTrainer(new Trainer(userEmail, name, address, gender, speciality));
            //The newly registered trainer is assigned to the trainer variable
            trainer = gym.searchTrainersByEmail(userEmail);
        }
        System.out.println("Registration successful");
        System.out.println("");
    }

    /**
     * Method used to display trainer menu
     *
     * @return the number chosen by the user as an int
     */
    private int trainerMenu() {
        int option = 0;
        boolean goodValue = false;

        while (!goodValue) {
            try {
                //Trainer Menu is displayed
                System.out.println("Trainer Menu:");
                System.out.println("1. Add a new member");
                System.out.println("2. List all members");
                System.out.println("3. Search for members by email");
                System.out.println("4. Assessments");
                System.out.println("0. Exit");
                System.out.println("===> ");
                option = input.nextInt();
                if (option >= 0 && option <= 4) {
                    goodValue = true;
                } else {
                    System.out.println("Please enter a number between 0 and 4");
                }
            }
            catch (Exception e) {
                input.nextLine();
                System.out.println("Number expected - you entered something else");
            }
        }
        return option;
    }

    /**
     * Method used to display member menu
     *
     * @return the number chosen by the user as an int
     */
    private int memberMenu() {
        int option = 0;
        boolean goodValue = false;

        while (!goodValue) {
            try { //Member menu is displayed
                System.out.println("Member Menu:");
                System.out.println("1. View your profile");
                System.out.println("2. Update your profile");
                System.out.println("3. Progress");
                System.out.println("0. Exit");
                System.out.println("===> ");
                option = input.nextInt();
                if (option >= 0 && option <= 3) {
                    goodValue = true;
                } else {
                    System.out.println("Please enter a number between 0 and 3");
                }
            }
            catch (Exception e) {
                input.nextLine();
                System.out.println("Number expected - you entered something else");
            }
        }
        return option;
    }

    /**
     * Method used to display trainer sub menu
     *
     * @return the number chosen by the user as an int
     */
    private int trainerSubMenu() {
        int option = 0;
        boolean goodValue = false;

        while (!goodValue) {
            try { //Trainer sub menu is displayed
                System.out.println("Assessments Sub-Menu");
                System.out.println("1. Add an assessment for a member");
                System.out.println("2. Update a comment on an assessment for a member");
                System.out.println("0. Return to Trainer Menu");
                System.out.println("===> ");
                option = input.nextInt();
                if (option >= 0 && option <= 2) {
                    goodValue = true;
                } else {
                    System.out.println("Please enter a number between 0 and 3");
                }
            }
            catch (Exception e) {
                System.out.println("Please enter a number between 0 and 3");
            }
        }
        return option;
    }

    /**
     * Method used to display member sub menu
     *
     * @return the number chosen by the user as an int
     */
    private int memberSubMenu() {
        int option = 0;
        boolean goodValue = false;

        try {
            while(!goodValue) { //Member sub menu is displayed
                System.out.println("Progress Sub-Menu");
                System.out.println("1. View progress by weight");
                System.out.println("2. View progress by waist measurement");
                System.out.println("0. Return to Member Menu");
                System.out.println("===> ");
                option = input.nextInt();
                if (option >= 0 && option <= 2) {
                    goodValue = true;
                } else {
                    System.out.println("Please enter a number between 0 and 2");
                }
            }
        }
        catch (Exception e) {
            System.out.println("Please enter a number between 0 and 2");
        }
        return option;
    }

    /**
     * Method used when a new member is being added to the gym
     */
    private void addMember() {

        /* Method is very similar to register except it is displayed differently
        as it is intended to be used when a trainer is registering a new member */

        input.nextLine(); //scanner bug
        String memberEmail = emailEntry();

        System.out.println("Enter member name: ");
        String name = "";
        boolean goodName = false;
        while (!goodName) {
            try {
                name = input.nextLine();
                goodName = true;
            }
            catch (Exception e) {
                System.out.println("Unexpected input: " + e);
            }
        }

        System.out.println("Enter member address: ");
        String address = "";
        boolean goodAddress = false;
        while (!goodAddress) {
            try {
                address = input.nextLine();
                goodAddress = true;
            }
            catch (Exception e) {
                System.out.println("Unexpected input: " + e);
            }
        }

        System.out.println("Enter member gender ('M' or 'F'): ");
        String gender = "";
        boolean goodGender = false;
        while (!goodGender) {
            try {
                gender = input.nextLine().toUpperCase();
                goodGender = true;
            }
            catch (Exception e) {
                System.out.println("Unexpected input: " + e);
            }
        }

        Float height = 0.00f;
        boolean goodHeight = false;
        while (!goodHeight) {
            System.out.println("Enter height (m): ");
            try {
                height = input.nextFloat();
                if ((height < 1.0) || (height > 3.0)) {
                    System.out.println("Height must be between 1 and 3 metres.");
                } else {
                    goodHeight = true;
                }
            }
            catch (Exception e) {
                System.out.println("Input must be an integer between 1.0 and 3.0.");
                input.nextLine(); //Scanner bug
            }
        }

        Float startWeight = 0.00f;
        boolean goodWeight = false;
        while (!goodWeight) {
            System.out.println("Enter member starting weight (kg): ");
            try {
                startWeight = input.nextFloat();
                if ((startWeight < 35.0) || (startWeight > 250.0)) {
                    System.out.println("Starting Weight must be between 35kg and 250kg.");
                } else {
                    goodWeight = true;
                }
            }
            catch (Exception e) {
                System.out.println("Input must be an integer between 35.0 and 250.0.");
                input.nextLine(); //Scanner bug
            }
        }

        boolean goodInput = false;
        while (!goodInput) {
            try {
                System.out.println("Is this member a student? (Y/N)");
                input.nextLine();
                char response = input.nextLine().toUpperCase().charAt(0);
                if (response == 'Y') {
                    goodInput = true;
                    System.out.println("Please enter the new member's student ID: ");
                    int studentId = input.nextInt();
                    System.out.println("Please enter the name of the new member's college: ");
                    input.nextLine();
                    String collegeName = input.nextLine();
                    gym.addMember(new StudentMember(memberEmail, name, address, gender, height, startWeight, collegeName, studentId, collegeName));
                } else if (response == 'N') {
                    goodInput = true;
                    String chosenPackage = displayPackages(packages);
                    gym.addMember(new Member(memberEmail, name, address, gender, height, startWeight, chosenPackage));
                } else {
                    System.out.println("Characters 'Y' or 'N' expected, you entered something else");
                    System.out.println("");
                }
            }
            catch (Exception e) {
                System.out.println("Registration error occurred: " + e);
            }

        }
        System.out.println("Member registered successfully");
    }

    /**
     * Method used to add Packages to packages HashMap
     */
    private void addPackages() {
        packages.put("Package 1", "Allowed access anytime to gym.\nFree access to all classes.\nAccess to all changing areas including deluxe changing rooms.");
        packages.put("Package 2", "Allowed access anytime to gym.\n€3 fee for all classes.\nAccess to all changing areas including deluxe changing rooms.");
        packages.put("Package 3", "Allowed access to gym at off-peak times.\n€5 fee for all classes.\nNo access to deluxe changing rooms.");
        packages.put("WIT", "Allowed access to gym during term time.\n€4 fee for all classes.\nNo access to deluxe changing rooms.");
    }

    /**
     * Method which displays the package choices and returns the choice from the user
     *
     * @param packages takes in the HashMap of packages to be displayed
     * @return returns the package choice from the user
     */
    private String displayPackages(LinkedHashMap<String, String> packages) {

        String output = "";
        int count = 1;

        //Iterator used to iterate through the HashMap
        Iterator it = packages.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry gymPackage = (Map.Entry)it.next();
            String gymPackageBenefits = (String)gymPackage.getValue();
            String gymPackageName = (String)gymPackage.getKey();
            //Format a list as a String using the gymPackageName and gymPackageBenefits fields
            output += count + ". " + gymPackageName + "\n" + gymPackageBenefits + "\n\n";
            System.out.println("");
            count++;
        }
        System.out.println(output);

        boolean goodValue = false;
        int choice = 0;
        String packageChoice = "";

        while(!goodValue) {
            System.out.println("Please enter your desired package (1, 2, 3, 4): ");
            try {
                choice = input.nextInt();
                switch (choice) { //Switch statement used to handle the user's package choice
                    case 1:
                        goodValue = true;
                        packageChoice = "Package 1";
                        break;
                    case 2:
                        goodValue = true;
                        packageChoice = "Package 2";
                        break;
                    case 3:
                        goodValue = true;
                        packageChoice = "Package 3";
                        break;
                    case 4:
                        goodValue = true;
                        packageChoice = "WIT";
                        break;
                    default:
                        System.out.println("Unexpected input - number between 1 and 4 expected.");
                        break;
                }
            }
            catch (Exception e) {
                System.out.println("Number between 1 and 4 expected.");
                input.nextLine(); //Scanner bug
            }
        }
        return packageChoice;
    }

    /**
     * Method used to load data from XML file
     */
    private void loadXML() {
        try {
            gym.load();
            System.out.println("Data loaded successfully");
        }
        catch (Exception e) {
            System.err.println("Error loading from file: " + e);
        }
    }

    /**
     * Method used to save data to XML file
     */
    private void saveXML() {
        try {
            gym.store();
            System.out.println("Data stored successfully");
        }
        catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }
    }

    /**
     * Method used to validate email entry
     *
     * @return
     */
    private String emailEntry() {
        String email = "";
        boolean goodEmail = false;
        while (!goodEmail) {
            try {
                boolean emailValidation = false;
                while (!emailValidation) {
                    System.out.println("Enter email: ");
                    email = input.nextLine();
                    if (email.contains("@") && (email.contains("."))) {
                        emailValidation = true;
                    } else {
                        System.out.println("This is not a valid email address - please try another");
                        System.out.println("");
                    }
                }
                Member result = gym.searchMembersByEmail(email);
                if (result != null) {
                    System.out.println("That email is already in use - please try another");
                    System.out.println("");
                } else {
                    goodEmail = true;
                }
            }
            catch (Exception e) {
                System.out.println("Unexpected input: " + e);
            }
        }
        return email;
    }

}
