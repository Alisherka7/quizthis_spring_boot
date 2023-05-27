package kr.quizthis.QuizThis.dto.QuizApiDto;

public class QuizGameModeResponse {
    private Integer quizid;
    private String question;
    private String answer;
    private String firstVersion;
    private String secondVersion;
    private String thirdVersion;
    private String fourthVersion;

    public QuizGameModeResponse(Integer quizid, String question, String answer, String firstVersion, String secondVersion, String thirdVersion, String fourthVersion) {
        this.quizid = quizid;
        this.question = question;
        this.answer = answer;
        this.firstVersion = firstVersion;
        this.secondVersion = secondVersion;
        this.thirdVersion = thirdVersion;
        this.fourthVersion = fourthVersion;
    }

    public Integer getQuizid() {
        return quizid;
    }

    public void setQuizid(Integer quizid) {
        this.quizid = quizid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFirstVersion() {
        return firstVersion;
    }

    public void setFirstVersion(String firstVersion) {
        this.firstVersion = firstVersion;
    }

    public String getSecondVersion() {
        return secondVersion;
    }

    public void setSecondVersion(String secondVersion) {
        this.secondVersion = secondVersion;
    }

    public String getThirdVersion() {
        return thirdVersion;
    }

    public void setThirdVersion(String thirdVersion) {
        this.thirdVersion = thirdVersion;
    }

    public String getFourthVersion() {
        return fourthVersion;
    }

    public void setFourthVersion(String fourthVersion) {
        this.fourthVersion = fourthVersion;
    }
}

