public class Section {
    private int sectionID;
    private String name;
    private String description;

    // Constructor
    public Section(int sectionID, String name, String description) {
        this.sectionID = sectionID;
        this.name = name;
        this.description = description;
    }
    public Section(String name, String description) {
        this.name = name;
        this.description = description;
    }


    // Getters
    public int getSectionID() {
        return sectionID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
