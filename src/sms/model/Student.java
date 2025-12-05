package sms.model;

public class Student {
    private int id;
    private String name;
    private Integer age;     // allow null for unknown
    private String email;
    private String course;

    public Student() {}

    public Student(int id, String name, Integer age, String email, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.course = course;
    }

    // For creating before id assigned
    public Student(String name, Integer age, String email, String course) {
        this(0, name, age, email, course);
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    @Override
    public String toString() {
        return String.format("ID:%d | %s | Age:%s | Email:%s | Course:%s",
                id, name, age == null ? "N/A" : age.toString(),
                email == null ? "N/A" : email, course == null ? "N/A" : course);
    }
}
