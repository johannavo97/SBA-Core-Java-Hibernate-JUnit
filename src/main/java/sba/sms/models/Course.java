package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    @Column(length = 50, nullable = false)
    String name;
    @NonNull
    @Column(length = 50, nullable = false)
    String instructor;

    @ToString.Exclude
    @ManyToMany(mappedBy = "courses",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER )
    List<Student> students = new ArrayList<>();

    public void addStudent(Student student){
        students.add(student);
        student.getCourses().add(this);
    }
}