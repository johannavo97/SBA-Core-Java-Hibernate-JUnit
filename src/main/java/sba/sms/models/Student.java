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
@Table(name = "student")
public class Student {
    @Id
    @NonNull
    @Column(length = 50, unique = true,name = "email")
    String email;
    @NonNull
    @Column(length = 50, nullable = false, name = "name")
    String name;
    @NonNull
    @Column(length = 50, nullable = false, name = "password")
    String password;

    @ToString.Exclude
    @ManyToMany( fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "student_email"), inverseJoinColumns = @JoinColumn(name = "courses_id"))
    List<Course> courses = new ArrayList<>();
}

