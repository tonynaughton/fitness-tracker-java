/**
 * The GymUtility class is used to store static methods which can be used by other applications.
 *
 * @author Tony Naughton
 * @version 1.0 (06.Jun.2020)
 */
public class GymUtility {

    /**
     * A method which is used to calculate a Member's BMI figure.
     * The returned figure is rounded to two decimal places.
     *
     * @param member Member that BMI is being calculated for
     * @param assessment Most recent assessment for the Member
     * @return A double value representing the Member's BMI
     */
    public static double calculateBMI(Member member, Assessment assessment) {
        float weight;
        if (assessment == null) {
            weight = member.getStartWeight();
        } else {
            weight = assessment.getWeight();
        }
        //Formula used to calculate BMI - (weight / height*height)
        double bmi = weight / (member.getHeight() * member.getHeight());
        //Rounding BMI to 2 decimal places
        double bmiRounded = Math.round(bmi * 100.0) / 100.0;
        return bmiRounded;
    }

    /**
     * A method which categorises a member's BMI result.
     *
     * @param bmiValue takes in the Member's BMI value
     * @return returns the BMI Category as a String
     */
    public static String determineBMICategory(double bmiValue) {
        String str = "";
        if (bmiValue < 16.0) {
            str = "SEVERELY UNDERWEIGHT";
        } else if ((bmiValue >= 16.0) && (bmiValue < 18.5)) {
            str = "UNDERWEIGHT";
        } else if ((bmiValue >= 18.5) && (bmiValue < 25.0)) {
            str = "NORMAL";
        } else if ((bmiValue >= 25.0) && (bmiValue < 30.0)) {
            str = "OVERWEIGHT";
        } else if ((bmiValue >= 30.0) && (bmiValue < 35.0)) {
            str = "MODERATELY OBESE";
        } else if (bmiValue >= 35.0) {
            str = "SEVERELY OBESE";
        }
        return str;
    }

    /**
     * A method which is used to check if a Member's current body weight is ideal or not.
     *
     * @param member Member that weight is being checked for
     * @param assessment Most recent assessment that weight is being based on
     * @return returns a boolean value representing whether Member's weight is ideal or not
     */
    public static boolean isIdealBodyWeight(Member member, Assessment assessment) {
        double idealWeight;
        //If the member is male, ideal weight is set to 50.0, otherwise it is set to 45.5
        if (member.getGender().equals("M")) {
            idealWeight = 50.0;
        } else {
            idealWeight = 45.5;
        }
        //If member is taller than 60 inches
        if (metersToInches(member.getHeight()) > 60) {
            //Multiply the height over 60 inches by 2.3 and add it to the ideal weight
            idealWeight += ((metersToInches(member.getHeight()) - 60) * 2.3);
        }
        float weight;
        //If no assessment was passed in, go by the starting weight
        if (assessment == null) {
            weight = member.getStartWeight();
        } else {
            weight = assessment.getWeight();
        }
        double difference = weight - idealWeight;
        //If the member's weight is within 0.2 kg of the idealWeight, return true
        if (difference >= -0.2 && difference <= 0.2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * A method used to convert meters to inches
     *
     * @param meters takes in meter value
     * @return returns the calculated inches value
     */
    public static double metersToInches(double meters) {
        double result = meters*39.370;
        double resultRounded = Math.round(result * 100.0)/100.0;
        return resultRounded;
    }

    /**
     * method used to convert kilogram value to pounds
     *
     * @param kg takes in kilogram value
     * @return returns pounds value
     */
    public static double kgToPounds(double kg) {
        double x = kg*2.20462;
        double xRounded = Math.round(x * 100.0)/100.0;
        return xRounded;
    }

}