import java.time.LocalDate;

public class Visitor {

    private int visitorID;     // Unique identifier for the visitor
    private String name;       // Visitor's name
    private String nid;        // National ID
    private String contact;    // Contact number
    private LocalDate visitDate; // Date of visit

    // Default constructor
    public Visitor() {
    }

    // Constructor with all fields (optional)
    public Visitor(int visitorID, String name, String nid, String contact, LocalDate visitDate) {
        this.visitorID = visitorID;
        this.name = name;
        this.nid = nid;
        this.contact = contact;
        this.visitDate = visitDate;
    }

    // Constructor for AddVisitorListener
    public Visitor(String name, String nid, String contact, String visitDate) {
        this.name = name;
        this.nid = nid;
        this.contact = contact;
        this.visitDate = LocalDate.parse(visitDate); // Parse visitDate as a LocalDate
    }

    // Getter and Setter methods
    public int getVisitorID() {
        return visitorID;
    }

    public void setVisitorID(int visitorID) {
        this.visitorID = visitorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    // Optional: Override toString for better debugging
    @Override
    public String toString() {
        return "Visitor{" +
                "visitorID=" + visitorID +
                ", name='" + name + '\'' +
                ", nid='" + nid + '\'' +
                ", contact='" + contact + '\'' +
                ", visitDate=" + visitDate +
                '}';
    }
}
