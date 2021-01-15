/**
 * The PremiumMember class is a subclass of Member and is intended to be used by Premium Members of the gym.
 *
 * @author Tony Naughton
 * @version 1.0 (06.Jun.2020)
 */
public class PremiumMember extends Member  {

    /**
     *
     * @param email Member's email address
     * @param name Member's name
     * @param address Member's address
     * @param gender Member's gender
     * @param height Member's height
     * @param startingWeight Member's starting weight
     * @param chosenPackage Member's chosen package
     */
    public PremiumMember(String email, String name, String address,
                         String gender, float height, float startingWeight, String chosenPackage) {
        super(email, name, address, gender, height, startingWeight, chosenPackage);
    }

    /**
     * Method which returns a Premium Member's details as a formatted String.
     *
     * @return Formatted String containing Premium Member's details.
     */
    public String toString() {
        String str = super.toString();
        return str;
    }

    /**
     * Method which sets Premium Member's chosen package
     *
     * @param packageChoice Package choice
     */
    public void chosenPackage(String packageChoice) {
        setChosenPackage(packageChoice);
    }

}
