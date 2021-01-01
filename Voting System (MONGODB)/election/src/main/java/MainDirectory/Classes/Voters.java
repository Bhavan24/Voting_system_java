package MainDirectory.Classes;

/**
 * This Class is the object for voters
 * This Class Contains instance variables for voters
 */
public class Voters {
    int voter_id;
    String first_name;
    String last_name;
    String address;
    String gender;
    String dob;

    public Voters(int voter_id, String first_name,
                  String last_name, String address,
                  String gender, String dob) {
        this.voter_id = voter_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.gender = gender;
        this.dob = dob;
    }

    public int getVoter_id() {
        return voter_id;
    }

    public void setVoter_id(int voter_id) {
        this.voter_id = voter_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}