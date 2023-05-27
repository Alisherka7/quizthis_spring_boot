package kr.quizthis.QuizThis.service;

import kr.quizthis.QuizThis.dto.QuizApiDto.*;
import kr.quizthis.QuizThis.entity.Quiz;
import kr.quizthis.QuizThis.entity.Quizset;
import kr.quizthis.QuizThis.entity.User;
import kr.quizthis.QuizThis.repository.QuizRepository;
import kr.quizthis.QuizThis.repository.QuizSetRepository;
import kr.quizthis.QuizThis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final UserRepository userRepository;

    @Autowired
    private QuizSetRepository quizSetRepository;

    @Autowired
    private QuizRepository quizRepository;




    // 퀴즈 반환
    public List<QuizResponse> getQuiz(Integer quizsetId){
        // 해당 사용자의 데이터
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        List<QuizResponse> target = new ArrayList<>();

        List<Quiz> quizList = quizRepository.findAllByQuizSetId(quizsetId);
        // quiz to quiz response
        for(Quiz ql : quizList){
            target.add(
                    QuizResponse.builder()
                            .quizid(ql.getQuizid())
                            .question(ql.getQuestion())
                            .answer(ql.getAnswer())
                            .build()
            );
        }

        return target;
    }

    // 퀴즈 세트 추가
    public QuizsetResponse newQuizset(QuizsetDto request) {
        // 해당 사용자의 데이터
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        Quizset createSet = Quizset.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .build();

        quizSetRepository.save(createSet);
        return QuizsetResponse.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();

    }

    public List<QuizGameModeResponse> quizGame(Integer quizsetId){
        // 해당 사용자의 데이터
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        Quizset quizset = quizSetRepository.findByQuizsetId(quizsetId).orElseThrow();

        // find all quiz by Quizset id
        List<Quiz> quiz = quizRepository.findAllByQuizSetId(quizset.getQuizsetid());
        List<QuizGameModeResponse> quizGameResponse = generateQuiz(quiz);
        return quizGameResponse;
    }

    private List<QuizGameModeResponse> generateQuiz(List<Quiz> quiz){
        List<QuizGameModeResponse> temp = new ArrayList<>();
        List<Quiz> selectedItems = getRandomItems(quiz, 4);

        for (Quiz item : selectedItems) {
            String question = item.getQuestion();
            String correctAnswer = item.getAnswer();

            // Shuffle the answer options
            List<String> answerOptions = new ArrayList<>();
            answerOptions.add(correctAnswer);
            selectedItems.stream()
                    .filter(data -> !data.getQuestion().equals(question))
                    .map(Quiz::getAnswer)
                    .forEach(answerOptions::add);
            Collections.shuffle(answerOptions);
            for(String i : answerOptions){
                System.out.println(i + "->");
            }

            temp.add(new QuizGameModeResponse(item.getQuizid(), question, correctAnswer, answerOptions.get(0), answerOptions.get(1), answerOptions.get(2), answerOptions.get(3)));
        }

        return temp;
    }

    private static List<Quiz> getRandomItems(List<Quiz> quiz, int count){
        List<Quiz> randomItems = new ArrayList<>();
        Random random = new Random();

        while (randomItems.size() < count) {
            Quiz item = quiz.get(random.nextInt(quiz.size()));
            if (!randomItems.contains(item)) {
                randomItems.add(item);
            }
        }
        return randomItems;
    }

    public List<QuizsetResponse> getQuizsets(){
        // 해당 사용자의 데이터
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        List<Quizset> quizSets = quizSetRepository.findByUserId(user.getId());
        List<QuizsetResponse> response = new ArrayList<>();

        for(Quizset i : quizSets){
            List<Quiz> quizL = quizRepository.findAllByQuizSetId(i.getQuizsetid());
            response.add(QuizsetResponse.builder()
                    .quizsetid(i.getQuizsetid())
                            .title(i.getTitle())
                            .description(i.getDescription())
                            .items(quizL.size())
                    .build());
        }
        return response;
    }

    // 해당 퀴즈 세트를 제거
    public QuizsetResponse updateQuizset(QuizsetUpdateDto request) {

        // find current Quizset
        Quizset target = quizSetRepository.findByQuizsetId(request.getQuizsetid()).orElseThrow();
        if(!request.getTitle().equals(target.getTitle())){
            target.setTitle(request.getTitle());
        }
        if(!request.getDescription().equals(target.getDescription())){
            target.setDescription(request.getDescription());
        }
        System.out.println("Update Quizset ->" + target.getDescription());

        quizSetRepository.save(target);
        return QuizsetResponse.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
    }

    // 해당 퀴즈 세트를 제거
    public QuizsetDeleteResponse deleteQuizset(Integer quizsetid) {
        Quizset qs = quizSetRepository.findByQuizsetId(quizsetid).orElseThrow(() -> new NoSuchElementException("Quizset not found"));
        List<Quiz> listOfQuiz = quizRepository.findAllByQuizSetId(quizsetid);
        quizRepository.deleteAll(listOfQuiz);
        quizSetRepository.delete(qs);


        return QuizsetDeleteResponse.builder()
                .status("Quizset removed successfully!")
                .build();
    }

    // 퀴즈 세트 반환

    // 퀴즈 생성
    public QuizResponse createQuiz(QuizDto request) {
        Optional<Quizset> quizset = quizSetRepository.findByQuizsetId(request.getQuizsetid());
        if(quizset.isEmpty()){
            return QuizResponse.builder()
                    .error("해당 퀴즈세트가 찾을 수 없습니다.!")
                    .build();
        }

        Quiz newQuiz = Quiz.builder()
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .quizset(quizset.get())
                .build();

        quizRepository.save(newQuiz);

        return QuizResponse.builder()
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .build();
    }

    // 퀴즈 수정
    // 해당 퀴즈 세트를 제거
    public QuizResponse updateQuiz(QuizUpdateDto request) {

        // find current Quizset
        Quiz target = quizRepository.findById(request.getQuizid()).orElseThrow();
        target.update(request);
        System.out.println("Update Quizset ->" + target.getAnswer());

        quizRepository.save(target);
        return QuizResponse.builder()
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .build();
    }


    // 퀴즈 제거
    public QuizDeleteResponse deleteQuiz(Integer quizid) {
        Optional<Quiz> quiz = quizRepository.findById(quizid);
        if(quiz.isPresent()){
            quizRepository.delete(quiz.get());
            return QuizDeleteResponse.builder()
                    .status("Quiz removed successfully!")
                    .build();
        }

        return QuizDeleteResponse.builder()
                .error("Quiz already removed!")
                .build();
    }

    // 퀴즈 반환

}
