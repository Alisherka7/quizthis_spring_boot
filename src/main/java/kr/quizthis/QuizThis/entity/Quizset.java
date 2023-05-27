package kr.quizthis.QuizThis.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@Data
@ToString
@NoArgsConstructor
@Getter
public class Quizset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quizsetid;

    @Column
    private String title;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

}
