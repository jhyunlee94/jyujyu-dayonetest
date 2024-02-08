package com.jyujuy.dayonetest.service;

import com.jyujuy.dayonetest.repository.StudentFailRepository;
import com.jyujuy.dayonetest.repository.StudentPassRepository;
import com.jyujuy.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StudentScoreServiceMockTest {
    @Test
    public void firstSaveScroeMockTest() {
        StudentScoreService studentScoreService = new StudentScoreService(
                Mockito.mock(StudentScoreRepository.class),
                Mockito.mock(StudentPassRepository.class),
                Mockito.mock(StudentFailRepository.class)
        );
        String givenStudentName = "jyujyu";
        String givenExam  = "testexam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        studentScoreService.saveScore(
            givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );
    }
}
