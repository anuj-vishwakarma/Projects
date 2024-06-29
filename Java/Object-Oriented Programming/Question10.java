/*10. Write a Java program to create a class called "Student" with a name, grade, and courses attributes, and methods to add and remove courses.*/

import java.util.ArrayList;

class Student {
    private String name;
    private char grade;

    ArrayList<String> Courses = new ArrayList<>();
    public void addCourse(String course) {
        Courses.add(course);
    }
    public void removeCourse(String course) {
        Courses.remove(course);
    }
    Student(String name, char grade, String...course){
        this.name = name;
        this.grade = grade;
        for(String c:course){
            addCourse(c);
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getCourses() {
        return Courses;
    }

    public char getGrade() {
        return grade;
    }

}

public class Question10 {
    public static void main(String[] args) {
        Student s = new Student("Nobita",'A',"Maths","Chemistry","Python");
        System.out.println(s.getCourses());
        s.removeCourse("Maths");
        System.out.println(s.getCourses());
    }
}
