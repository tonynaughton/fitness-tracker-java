/**
 * The Trainer class is a subclass of Person and is used by Trainers when accessing the application.
 *
 * @author Tony Naughton
 * @version 1.0 (06.Jun.2020)
 */

public class Trainer extends Person {

    private String speciality;

    /**
     * Trainer constructor method
     *
     * @param email Trainer's email address
     * @param name Trainer's name
     * @param address Trainer's address
     * @param gender Trainer's gender
     * @param speciality This field outlines what the Trainer specialises in at the Gym
     */
    public Trainer(String email, String name, String address, String gender, String speciality) {
        super(email, name, address, gender);
        setSpeciality(speciality);
    }

    /**
     * Method used to return Trainer details in a formatted String
     *
     * @return Returns a formatted String with Trainer fields
     */
    public String toString() {
        String str = super.toString();
        str += "Speciality: " + speciality + "\n";
        return str;
    }

    /**
     * Accessor for Speciality field
     *
     * @return returns Trainer's speciality
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * Mutator for Trainer's speciality
     *
     * @param speciality Takes in the new speciality the Trainer has
     */
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

}
