package MainDirectory.Classes;

/**
 * This Class is the object for results
 * This Class Contains instance variables for results
 */
public class Results {
    String name;
    int votes;

    public Results(String name, int votes) {
        this.name = name;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}