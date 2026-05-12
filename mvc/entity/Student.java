package mvc.entity;

public class Student {
    private String id;
    private String name;
    private String dob;
    private int sex;
    private String classroomId;

    public Student(String id, String name, String dob, int sex, String classroomId) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.sex = sex;
        this.classroomId = classroomId;
    }

    // Getters và Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDob() { return dob; }
    public int getSex() { return sex; }
    public String getClassroomId() { return classroomId; }

    public void setName(String name) { this.name = name; }
    public void setDob(String dob) { this.dob = dob; }

    @Override
    public String toString() {
        String gender = (sex == 1) ? "Nam" : "Nữ";
        return String.format("| %-10s | %-20s | %-12s | %-10s | Lớp: %-8s |",
                id, name, dob, gender, classroomId);
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}