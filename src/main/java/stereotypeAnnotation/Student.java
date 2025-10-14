package stereotypeAnnotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class Student {
    @Value("Neha")
    private String name;
    @Value("Jaipur")
    private String city;
    @Value("#{courseList}")
    private List<String> courseSubjects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public List<String> getCourseSubjects() {
        return courseSubjects;
    }

    public void setCourseSubjects(List<String> courseSubjects) {
        this.courseSubjects = courseSubjects;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", courseSubjects=" + courseSubjects +
                '}';
    }
}
