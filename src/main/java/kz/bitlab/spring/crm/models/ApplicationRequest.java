package kz.bitlab.spring.crm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "requests")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ApplicationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "comment")
    private String comment;

    @Column(name = "phone")
    private String phone;

    @Column(name = "handled")
    private boolean handled;
}
