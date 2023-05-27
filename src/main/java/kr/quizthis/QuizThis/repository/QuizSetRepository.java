package kr.quizthis.QuizThis.repository;

import kr.quizthis.QuizThis.entity.Quizset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface QuizSetRepository extends JpaRepository<Quizset, Integer> {
    @Override
    ArrayList<Quizset> findAll();

    @Query(value = "Select * from quizset where quizsetid = :currentId", nativeQuery = true)
    Optional<Quizset> findByQuizsetId(Integer currentId);

    @Query(value = "Select * from quizset where userid = :currentId", nativeQuery = true)
    List<Quizset> findByUserId(Integer currentId);

}
