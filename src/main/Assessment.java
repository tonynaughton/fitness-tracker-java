/**
 * The Assessment class is designed to store details from a specific assessment for a given member at the gym.
 * Each assessment contains a recording of a Member's weight, thigh, waist and an assessment comment.
 *
 * @author Tony Naughton
 * @version 1.0 (06.Jun.2020)
 */

public class Assessment {

    private float weight;
    private float thigh;
    private float waist;
    private String comment;

    /**
     * Assessment constructor method.
     *
     * @param weight Recording of Member's weight measurement.
     * @param thigh Recording of Member's thigh measurement.
     * @param waist Recording of Member's waist measurement.
     * @param comment Comment left by a Trainer on the Assessment.
     */
    public Assessment(float weight, float thigh, float waist, String comment) {
        this.weight = weight;
        this.thigh = thigh;
        this.waist = waist;
        this.comment = comment;
    }

    /**
     * Method used to display the Assessment in String format by outputting each field in a formatted String.
     *
     * @return A single String containing all Assessment fields.
     */
    public String toString() {
        String str = "";
        str += "Weight: " + weight + "\n";
        str += "Thigh: " + weight + "\n";
        str += "Waist: " + weight + "\n";
        str += "Comment: " + weight + "\n\n";
        return str;
    }

    /**
     * Weight Accessor
     *
     * @return returns Assessment weight value
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Mutator for weight field
     *
     * @param weight returns Assessment weight
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Thigh Accessor
     *
     * @return returns Assessment thigh value
     */
    public float getThigh() {
        return thigh;
    }

    /**
     * Mutator for thigh field
     *
     * @param thigh returns Assessment thigh value
     */
    public void setThigh(float thigh) {
        this.thigh = thigh;
    }

    /**
     * Waist Accessor
     *
     * @return returns Assessment waist value
     */
    public float getWaist() {
        return waist;
    }

    /**
     * Mutator for waist field
     *
     * @param waist returns Assessment waist value
     */
    public void setWaist(float waist) {
        this.waist = waist;
    }

    /**
     * Comment Accessor
     *
     * @return returns Assessment comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Mutator for comment field
     *
     * @param comment returns Assessment comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
