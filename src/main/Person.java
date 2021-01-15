/**
 * The Person class is the super class of all users at the gym. Common fields among all users of the gym are stored here:
 * 'name', 'email', 'address' and 'gender'.
 *
 * @author Tony Naughton
 * @version 1.0 (06.Jun.2020)
 */

public class Person {
    private String name;
    private String email;
    private String address;
    private String gender;

    /**
     * Constructor for the Person class
     *
     * @param email Field for a person's email address
     * @param name Field for a person's name
     * @param address Field for a person's address
     * @param gender Field for a person's gender
     */
    public Person(String email, String name, String address, String gender) {
        setName(name);
        this.email = email;
        this.address = address;
        setGender(gender);
    }

    /**
     * A method which returns the Person's details as a formatted String
     *
     * @return A String which contains the Person's details
     */
    public String toString() {
        String str = "";
        str += "Name: " + name + "\n";
        str += "Email: " + email + "\n";
        str += "Address: " + address + "\n";
        str += "Gender: " + gender + "\n";
        return str;
    }

    /**
     * Accessor for Name field
     *
     * @return Person's name
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator for name field. A limit of 30 characters is applied to a Person's name.
     *
     * @param name The name that is to be passed into the Person object
     */
    public void setName(String name) {
        if (name.length() > 30) {
            this.name = name.substring(0, 30);
        } else {
            this.name = name;
        }
    }

    /**
     * Accessor for Email field
     *
     * @return returns the Person's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Accessor for Address field
     *
     * @return returns the Person's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Mutator for Address field
     *
     * @param address takes in the value which the address is to be updated to
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Accessor for Gender field
     *
     * @return returns the Person's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Mutator for the Gender field. If the value passed in is not 'M' or 'F', the gender is set to Unspecified.
     *
     * @param gender takes in the value which the gender is to be updated to
     */
    public void setGender(String gender) {
        //If the gender input is not equal to 'M' or 'F', it is set to Unspecified
        if ((gender.equals("M") || (gender.equals("F")))) {
            this.gender = gender;
        } else {
            this.gender = "Unspecified";
        }
    }
}
