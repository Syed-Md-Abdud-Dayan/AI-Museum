public class Specimen {
    private int specimenID;
    private String name;
    private String description;
    private String developmentDate;
    private int creatorID;
    private int sectionID;

    // Constructor
    public Specimen(int specimenID, String name, String description, String developmentDate, int creatorID, int sectionID) {
        this.specimenID = specimenID;
        this.name = name;
        this.description = description;
        this.developmentDate = developmentDate;
        this.creatorID = creatorID;
        this.sectionID = sectionID;
    }
    public Specimen(String name, String description, String developmentDate, int creatorID, int sectionID) {
        this.name = name;
        this.description = description;
        this.developmentDate = developmentDate;
        this.creatorID = creatorID;
        this.sectionID = sectionID;
    }

    // Getters
    public int getSpecimenID() {
        return specimenID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDevelopmentDate() {
        return developmentDate;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public int getSectionID() {
        return sectionID;
    }
}
