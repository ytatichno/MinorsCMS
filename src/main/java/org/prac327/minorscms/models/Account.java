package org.prac327.minorscms.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Created by ytati
 * on 24.05.2024.
 */

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

//    @Column
    @OneToOne
    @JoinColumn(name = "student_card")
    private Student studentCard;

//    @Column
    @OneToOne
    @JoinColumn(name = "teacher_card")
    private Teacher teacherCard;

//    public UserDetails getUserDetails(){
//
//    }
}
