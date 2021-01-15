/**
 * The StudentMember class is a subclass of Member and is intended to be used by Student Members of the gym.
 * Student Members have two extra fields from a standard Member: 'studentId' and 'collegeName'.
 *
 * @author Tony Naughton
 * @version 1.0 (06.Jun.2020)
 */

public class StudentMember extends Member  {

    private int studentId;
    private String collegeName;

    /**
     * Constructor for a Student Member
     *
     * @param email Member's email address
     * @param name Member's name
     * @param address Member's address
     * @param gender Member's gender
     * @param height Member's height
     * @param startingWeight Member's starting weight
     * @param chosenPackage Member's chosen package
     * @param studentId Member's student ID
     * @param collegeName Member's college name
     */
    public StudentMember(String email, String name, String address,
                         String gender, float height, float startingWeight, String chosenPackage,
                         int studentId, String collegeName) {
        super(email, name, address, gender, height, startingWeight, chosenPackage);
        this.studentId = studentId;
        this.collegeName = collegeName;
    }

    /**
     * Method which returns a Student Member's details as a formatted String.
     *
     * @return Formatted String containing Student Member's details.
     */
    public String toString() {
        String str = super.toString();
        str += "Student ID: " + studentId + "\n";
        str += "College Name: " + collegeName + "\n";
        return str;
    }

    /**
     * Method which sets Student Member's chosen package
     *
     * @param packageChoice Package choice
     */
    public void chosenPackage(String packageChoice) {
        setChosenPackage(packageChoice);
    }

    /**
     * Accessor for studentId field
     *
     * @return studentId value
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Accessor for collegeName field
     *
     * @return collegeName value
     */
    public String getCollegeName() {
        return collegeName;
    }

}
