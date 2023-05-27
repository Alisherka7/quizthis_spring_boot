package kr.quizthis.QuizThis.entity;

import jakarta.persistence.*;
import kr.quizthis.QuizThis.dto.QuizApiDto.QuizUpdateDto;
import lombok.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quizid;

    @Column
    private String question;

    @Column
    private String answer;

    @ManyToOne
    @JoinColumn(name = "quizsetid")
    private Quizset quizset;

//    // 퀴즈 생성
//    public static Quiz createQuiz(QuizDTO quizDTO, QuizSet quizSet) {
//        if(quizDTO.getQuizid() != null){
//            throw new IllegalArgumentException("Create new Quiz Error -> 퀴즈 아이디 없이 데이터 입력하세요!");
//        }
//        if(quizDTO.getQuizsetid() != quizSet.getQuizsetid()){
//            throw new IllegalArgumentException("Create new Quiz Error -> 퀴즈 아이디와 퀴즈 세트 아이디 동일하지 않습니다~!");
//        }
//
//        return new Quiz(
//                quizDTO.getQuizid(),
//                quizDTO.getQuestion(),
//                quizSet
//        );
//    }
//
    // 퀴즈 수정
    public void update(QuizUpdateDto request){
        // 객체를 갱신
        if(request.getQuestion() != null){
            this.question = request.getQuestion();
        }
        if(request.getAnswer() != null){
            this.answer = request.getAnswer();
        }
    }

}