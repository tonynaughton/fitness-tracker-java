import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * The GymAPI class stores the lists of members and trainers at the gym.
 * The class also contains various methods which act on the data stored
 * in the lists of members and trainers.
 *
 * @author Tony Naughton
 * @version 1.0
 */

public class GymAPI {
    private ArrayList<Member> members;
    private ArrayList<Trainer> trainers;

    /**
     * Constructor for GymAPI class which assigns members and trainers to new ArrayLists.
     */
    public GymAPI() {
        this.members = new ArrayList<Member>();
        this.trainers = new ArrayList<Trainer>();
    }

    /**
     * Accessor for members field.
     *
     * @return members ArrayList value.
     */
    public ArrayList<Member> getMembers() {
        return members;
    }

    /**
     * Accessor for trainers field.
     *
     * @return trainers ArrayList value.
     */
    public ArrayList<Trainer> getTrainers() {
        return trainers;
    }

    /**
     * Method used to add a member to the members ArrayList.
     *
     * @param member Takes in the member value which is to be added to the members ArrayList.
     */
    public void addMember(Member member) {
        members.add(member);
    }

    /**
     * Method used to add a trainer to the trainers ArrayList.
     *
     * @param trainer Takes in the trainer value which is to be added to the trainers ArrayList.
     */
    public void addTrainer(Trainer trainer) {
        trainers.add(trainer);
    }

    /**
     * Method used to check the size of the members ArrayList.
     *
     * @return an int value of the size of the members ArrayList.
     */
    public int numberOfMembers() {
        return members.size();
    }

    /**
     * Method used to check the size of the trainers ArrayList.
     *
     * @return an int value of the size of the trainers ArrayList.
     */
    public int numberOfTrainers() {
        return trainers.size();
    }

    /**
     * Method used to check if a passed in int value is a valid index in the members ArrayList.
     *
     * @param index index value which is to be checked for
     * @return returns boolean value stating if the index is valid or not
     */
    public boolean isValidMemberIndex(int index) {
        if ((index >= 0) && (index <= (getMembers().size() - 1))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method used to check if a passed in int value is a valid index in the trainers ArrayList.
     *
     * @param index index value which is to be checked for
     * @return returns boolean value stating if the index is valid or not
     */
    public boolean isValidTrainerIndex(int index) {
        if ((index >= 0) && (index <= (getTrainers().size() - 1))) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Method used to check if a Member exists in the gym with the email address passed in
     *
     * @param emailEntered email address which is to be searched
     * @return returns a Member object which will be the member with the email address if one is found
     */
    public Member searchMembersByEmail(String emailEntered) {
        for (Member member : members) {
            if (member.getEmail().equals(emailEntered)) {
                return member;
            }
        }
        return null;
    }

    /**
     * Method used to check if a Trainer exists in the gym with the email address passed in
     *
     * @param emailEntered email address which is to be searched
     * @return returns a Trainer object which will be the trainer with the email address if one is found
     */
    public Trainer searchTrainersByEmail(String emailEntered) {
        for (Trainer trainer : trainers) {
            if (trainer.getEmail().equals(emailEntered)) {
                return trainer;
            }
        }
        return null;
    }

    /**
     * Method used to check if a Member exists in the gym with the name passed in
     *
     * @param nameEntered name which is to be searched
     * @return returns a Member object which will be the member with the name if one is found
     */
    public ArrayList<String> searchMembersByName (String nameEntered) {
        ArrayList<String> result = new ArrayList<String>();
        for (Member member : members) {
            if (member.getName().toLowerCase().contains(nameEntered.toLowerCase())) {
                result.add(member.getName());
            }
        }
        return result;
    }

    /**
     * Method used to check if a Trainer exists in the gym with the name passed in
     *
     * @param nameEntered name which is to be searched
     * @return returns a Trainer object which will be the trainer with the name if one is found
     */
    public ArrayList<String> searchTrainersByName (String nameEntered) {
        ArrayList<String> result = new ArrayList<String>();
        for (Trainer trainer : trainers) {
            if (trainer.getName().contains(nameEntered)) {
                result.add(trainer.getName());
            }
        }
        return result;
    }

    /**
     * Mehtod which returns an ArrayList of Members registered at the gym.
     *
     * @return returns ArrayList of Members
     */
    public ArrayList<Member> listMembers () {
        return members;
    }

    /**
     * A method which finds all the Members registered at the gym who
     * currently have an ideal body weight.
     *
     * @return returns an ArrayList of Members with ideal body weight
     */
    public ArrayList<Member> listMembersWithIdealWeight () {
        ArrayList<Member> idealWeightMembers = new ArrayList<Member>();
        for (Member member : members) {
            Assessment assessment;
            if (member.getAssessments().isEmpty()) { //Ensure member has existing assessments
                assessment = null;
            } else {
                assessment = member.latestAssessment(); //Get members latest assessment
            }
            if (GymUtility.isIdealBodyWeight(member, assessment)) {
                idealWeightMembers.add(member);
            }
        }
        return idealWeightMembers;
    }

    /**
     * Method used to find all Members registered at the gym who have all of part
     * of the BMI category passed in.
     *
     * @param bmiCategory String value of BMI category to be checked for.
     * @return returns an ArrayList of Members who's BMI category matches
     * or partly matches the BMI category entered.
     */
    public ArrayList<Member> listMembersBySpecificBMICategory(String bmiCategory) {
        ArrayList<Member> idealWeightMembers = new ArrayList<Member>();
        for (Member member : members) {
            Assessment assessment;
            HashMap<String, Assessment> memberAssessments = member.getAssessments();
            if (memberAssessments.isEmpty()) {
                assessment = null;
            } else {
                assessment = member.latestAssessment();
            }
            double memberBmi = GymUtility.calculateBMI(member, assessment);
            String memberBmiCategory = GymUtility.determineBMICategory(memberBmi);
            if (memberBmiCategory.contains(bmiCategory.toUpperCase())) {
                idealWeightMembers.add(member);
            }
        }
        return idealWeightMembers;
    }

    /**
     * A method used to print a Member's weight and height in both imperial and metric values.
     *
     * @return returns a formatted String of the Member's weight
     * and height in imperial and metric values.
     */
    public String listMemberDetailsImperialAndMetric() {
        if (!members.isEmpty()) {
            String result = "";
            for (Member member : members) {
                String memberDetails = "";
                memberDetails += member.getName() + " ";
                int memberWeight = (int) Math.round((member.getLatestWeight()*100.0)/100.0);
                memberDetails += memberWeight + " kg ";
                int memberWeightPounds = (int) GymUtility.kgToPounds((member.getLatestWeight()*100.0)/100.0);
                memberDetails += memberWeightPounds + " lbs ";
                double memberHeight = Math.round(member.getHeight()*10) / 10.0;
                memberDetails += memberHeight + " metres ";
                double memberHeightInches = GymUtility.metersToInches((member.getHeight()*100.0)/100.0);
                memberDetails += (int) Math.ceil(memberHeightInches) + " inches \n";
                result += memberDetails;
            }
            return result;
        } else {
            return "No registered members";
        }
    }

    /**
     * Method used to save the inputted data to an XML file named 'data.xml'.
     *
     * @throws Exception Returns any errors encountered when attempting to save data to the XML file.
     */
    public void store() throws Exception {
        XStream xstream = new XStream(new DomDriver());

        Class<?>[] classes = new Class[] {Member.class, Trainer.class, HashMap.class, Assessment.class, StudentMember.class};

        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("data.xml"));

        out.writeObject(members);
        out.writeObject(trainers);

        out.close();

    }

    /**
     * Method used to load data from an XML file named 'data.xml'.
     *
     * @throws Exception Returns any errors encountered when attempting to load data from the XML file.
     */
    public void load() throws Exception {
        XStream xstream = new XStream(new DomDriver());

        Class<?>[] classes = new Class[] {Member.class, Trainer.class, HashMap.class, Assessment.class, StudentMember.class};

        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("data.xml"));

        members = (ArrayList<Member>) is.readObject();
        trainers = (ArrayList<Trainer>) is.readObject();

        is.close();
    }

}
