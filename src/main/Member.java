import java.util.*;

/**
 * The Member class is a subclass of Person. It stores fields and methods which are only
 * used when logging into the app as a Member.
 *
 * @author Tony Naughton
 * @version 1.0 (06.Jun.2020)
 */

public class Member extends Person {
    private float height;
    private float startingWeight;
    private String chosenPackage;
    public HashMap <String, Assessment> assessments;

    /**
     * Constructor for Member class
     *
     * @param email Member's email address
     * @param name Member's name
     * @param address Member's address
     * @param gender Member's gender
     * @param height Member's height
     * @param startingWeight Member's starting weight
     * @param chosenPackage Member's chosen package
     */
    public Member(String email, String name, String address,
                  String gender, float height, float startingWeight, String chosenPackage) {
        super(email, name, address, gender);
        setHeight(height);
        setStartWeight(startingWeight);
        setChosenPackage(chosenPackage);
        //Setting assessments field to a new HashMap
        assessments = (new HashMap<String, Assessment>());
    }

    /**
     * Method which returns the latest Assessment by date from a Member's set of Assessments.
     *
     * @return Returns Assessment object which is latest Assessment carried out on the Member
     */
    public Assessment latestAssessment() {
        //Using SortedSet to get the assessment dates in chronological order
        SortedSet<String> sortedAssessments = sortedAssessmentDates();
        Assessment result = null;
        if (!sortedAssessments.isEmpty()) {
            //Getting the last item added to the sortedAssessments set will be the most recent
            String latestDate = sortedAssessments.last();
            result = assessments.get(latestDate);
        }
        return result;
    }

    /**
     * Method which takes the assessments from the assessments HashMap
     * and sorts them by most recent first as a SortedSet.
     *
     * @return SortedSet<String> which contains the Assessment dates sorted by most recent first.
     */
    public SortedSet<String> sortedAssessmentDates() {
        //TreeSet used to put the Assessment Dates in Chronological order
        SortedSet<String> assessmentDates = new TreeSet<>();
        Iterator it = assessments.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry assessment = (Map.Entry)it.next();
            //Iterating through the set and adding the dates to the assessmentDates set
            String date = (String)assessment.getKey();
            assessmentDates.add(date);
        }
        return assessmentDates;
    }

    /**
     * Empty method which is overwritten in PremiumMember and StudentMember classes
     *
     * @param chosenPackage Takes in Member's chosen package
     */
    public void chosenPackage(String chosenPackage) {

    };

    /**
     * Method which returns a Member's details as a formatted String.
     *
     * @return Formatted String containing Member's details.
     */
    public String toString() {
        String str = super.toString();
        str += "Height: " + height + " metres\n";
        str += "Start Weight: " + startingWeight + " kg\n";
        str += "Chosen Package: " + chosenPackage + "\n";
        return str;
    }

    /**
     * Method used to get the most recent weight recorded for a Member.
     *
     * @return float value of the latest weight.
     */
    public float getLatestWeight() {
        if (assessments.isEmpty()) {
            return startingWeight;
        } else {
            return latestAssessment().getWeight();
        }
    }

    /**
     * Method used to display Assessments sorted in order of weight - lowest to highest.
     *
     * @return A String is returned displaying the Assessment weights in order of lowest to highest.
     */
    public String getAssessmentsByWeight() {
        String result = "";
        SortedSet<String> assessmentsByWeight = new TreeSet<>();
        Iterator it = assessments.entrySet().iterator();
        if (!assessments.isEmpty()) {
            while (it.hasNext()) {
                Map.Entry assessment = (Map.Entry)it.next();
                Assessment orderedAssessment = (Assessment) assessment.getValue();
                String date = (String) assessment.getKey();
                float weight = orderedAssessment.getWeight();
                assessmentsByWeight.add(weight + "kg - " + date);
            }
            result += "Your assessments sorted by weight: \n";
            for (String assessment : assessmentsByWeight) {
                result += assessment + "\n";
            }
        } else {
            result = "There are no assessments recorded";
            System.out.println("");
        }
        return result;
    }

    /**
     * Method used to display Assessments sorted in order of waist - lowest to highest.
     *
     * @return A String is returned displaying the Assessment waists in order of lowest to highest.
     */
    public String getAssessmentsByWaist() {
        String result = "";
        SortedSet<String> assessmentsByWaist = new TreeSet<>();
        Iterator it = assessments.entrySet().iterator();
        if (!assessments.isEmpty()) {
            while (it.hasNext()) {
                Map.Entry assessment = (Map.Entry)it.next();
                Assessment orderedAssessment = (Assessment) assessment.getValue();
                String date = (String) assessment.getKey();
                float waist = orderedAssessment.getWaist();
                assessmentsByWaist.add(waist + "cm - " + date);
            }
            result += "Your assessments sorted by waist measurement: \n";
            for (String assessments : assessmentsByWaist) {
                result += assessments + "\n";
            }
        } else {
            result = "There are no assessments recorded";
            System.out.println("");
        }
        return result;
    }

    /**
     * Accessor for startingWeight field.
     *
     * @return startingWeight value as a float.
     */
    public float getStartWeight() {
        return startingWeight;
    }

    /**
     * Mutator for startingWeight field. Value cannot be below 35.0 or higher than 250.0.
     *
     * @param startWeight takes in the startingWeight value.
     */
    public void setStartWeight(float startWeight) {
        try {
            if ((startWeight < 35.0) || (startWeight > 250.0)) {
                //Ensuring the weight is between 35 and 250 kg
                System.out.println("Starting Weight must be between 35kg and 250kg.");
            } else {
                this.startingWeight = startWeight;
            }
        }
        catch (Exception e) {
            System.out.println("Unexpected input: " + e);
        }
    }

    /**
     * Accessor for height field.
     *
     * @return height value as a float.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Mutator for height field. Height must be between 1 and 3 metres.
     *
     * @param height takes in the height value.
     */
    public void setHeight(float height) {
        try {
            if ((height < 1.0) || (height > 3.0)) {
                //Ensuring the height is between 1 and 3 metres
                System.out.println("Height must be between 1 and 3 metres.");
            } else {
                this.height = height;
            }
        }
        catch (Exception e) {
            System.out.println("Unexpected input: " + e);
        }
    }

    /**
     * Accessor for chosenPackage field.
     *
     * @return chosenPackage value as a String.
     */
    public String getChosenPackage() {
        return chosenPackage;
    }

    /**
     * Mutator for chosenPackage field.
     *
     * @param chosenPackage takes in the chosenPackage value.
     */
    public void setChosenPackage(String chosenPackage) {
        this.chosenPackage = chosenPackage;
    }

    /**
     * Accessor for assessments field.
     *
     * @return assessments value as a HashMap.
     */
    public HashMap getAssessments() {
        return assessments;
    }

}

