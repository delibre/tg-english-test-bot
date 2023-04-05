package by.ciao.EnglishSchoolBot.englishtest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public class Question {
    private final int numberOfQuestion;
    private final String question;
    private final String[] answers;
    private final int correctAnswerIndex;

    public String getCorrectAnswer() {
        return answers[correctAnswerIndex -1];
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + Arrays.toString(answers) +
                '}';
    }
}
