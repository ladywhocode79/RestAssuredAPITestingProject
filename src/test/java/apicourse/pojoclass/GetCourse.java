package apicourse.pojoclass;

public class GetCourse {
    private String url;
    private String instructor;
    private String services;
    private String expertise;
    private String linkedlinUrl;
    private Courses courses;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getLinkedlinUrl() {
        return linkedlinUrl;
    }

    public void setLinkedlinUrl(String linkedlinUrl) {
        this.linkedlinUrl = linkedlinUrl;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }


}
