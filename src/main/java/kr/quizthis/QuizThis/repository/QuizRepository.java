package kr.quizthis.QuizThis.repository;

import kr.quizthis.QuizThis.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    @Override
    ArrayList<Quiz> findAll();

    @Query(value = "SELECT * from quiz where quizsetid = :quizsetId", nativeQuery = true)
    List<Quiz> findAllByQuizSetId(Integer quizsetId);


}
