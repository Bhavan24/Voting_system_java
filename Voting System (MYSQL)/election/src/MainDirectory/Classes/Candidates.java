package MainDirectory.Classes;

/**
 * This Class is the object for candidates
 * This Class Contains instance variables for candidates
 */
public class Candidates {
    int id;
    String name;
    String party;

    public Candidates(int id, String name, String party) {
        this.id = id;
        this.name = name;
        this.party = party;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }
}