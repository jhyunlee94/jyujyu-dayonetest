package com.jyujuy.dayonetest.service;

import com.jyujuy.dayonetest.MyCalculator;
import com.jyujuy.dayonetest.controller.response.ExamFailStudentResponse;
import com.jyujuy.dayonetest.controller.response.ExamPassStudentResponse;
import com.jyujuy.dayonetest.model.StudentFail;
import com.jyujuy.dayonetest.model.StudentPass;
import com.jyujuy.dayonetest.model.StudentScore;
import com.jyujuy.dayonetest.repository.StudentFailRepository;
import com.jyujuy.dayonetest.repository.StudentPassRepository;
import com.jyujuy.dayonetest.repository.StudentScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentScoreService {

    private final StudentScoreRepository studentScoreRepository;
    private final StudentPassRepository studentPassRepository;
    private final StudentFailRepository studentFailRepository;
    public void saveScore(String studentName, String exam, Integer korScore, Integer englishScore, Integer mathScore ){
        StudentScore studentScore  = StudentScore.builder()
                .studentName(studentName)
                .exam(exam)
                .korScore(korScore)
                .englishScore(englishScore)
                .mathScore(mathScore)
                .build();

        studentScoreRepository.save(studentScore);

        MyCalculator myCalculator = new MyCalculator(0.0);
        Double avgScore =  myCalculator
                .add(korScore.doubleValue())
                .add(englishScore.doubleValue())
                .add(mathScore.doubleValue())
                .divide(3.0)
                .getResult();

        if (avgScore >= 60) {
            StudentPass studentPass = StudentPass.builder()
                    .exam(exam)
                    .studentName(studentName)
                    .avgScore(avgScore)
                    .build();
            studentPassRepository.save(studentPass);
        } else {
            StudentFail studentFail = StudentFail.builder()
                    .exam(exam)
                    .studentName(studentName)
                    .avgScroe(avgScore)
                    .build();

            studentFailRepository.save(studentFail);
        }
    }

    public List<ExamPassStudentResponse> getPassStudentsList(String exam) {
        List<StudentPass> studentPasses = studentPassRepository.findAll();

        return studentPasses.stream()
                .filter((pass) -> pass.getExam().equals(exam))
                .map((pass) -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .toList();
    }

    public List<ExamFailStudentResponse> getFailStudentsList(String exam) {
        List<StudentFail> studentFails = studentFailRepository.findAll();

        return studentFails.stream()
                .filter((fail) -> fail.getExam().equals(exam))
                .map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScroe()))
                .toList();
    }

}
