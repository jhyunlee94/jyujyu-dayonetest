package com.jyujuy.dayonetest.service;

import com.jyujuy.dayonetest.repository.StudentFailRepository;
import com.jyujuy.dayonetest.repository.StudentPassRepository;
import com.jyujuy.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StudentScoreServiceMockTest {
    @Test
    public void firstSaveScroeMockTest() {
        StudentScoreService studentScoreService = new StudentScoreService(Mockito.mock(StudentScoreRepository.class), Mockito.mock(StudentPassRepository.class), Mockito.mock(StudentFailRepository.class));

        String givenStudentName = "jyujyu";
        String givenExam = "testexam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        studentScoreService.saveScore(givenStudentName, givenExam, givenKorScore, givenEnglishScore, givenMathScore);
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 이상인 경우")
    public void saveScoreMockTest() {
        // given : 평균점수가 60점 이상인 경우
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService service = new StudentScoreService(studentScoreRepository, studentPassRepository, studentFailRepository);

        String givenStudentName = "jyujyu";
        String givenExam = "testexam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        // when
        service.saveScore(givenStudentName, givenExam, givenKorScore, givenEnglishScore, givenMathScore);

        // then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 미만인 경우")
    public void saveScoreMockTest2() {
        // given : 평균점수가 60점 미만인 경우
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService service = new StudentScoreService(studentScoreRepository, studentPassRepository, studentFailRepository);

        String givenStudentName = "jyujyu";
        String givenExam = "testexam";
        Integer givenKorScore = 40;
        Integer givenEnglishScore = 40;
        Integer givenMathScore = 60;

        // when
        service.saveScore(givenStudentName, givenExam, givenKorScore, givenEnglishScore, givenMathScore);

        // then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(1)).save(Mockito.any());
    }
}
