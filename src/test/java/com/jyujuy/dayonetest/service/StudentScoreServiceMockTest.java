package com.jyujuy.dayonetest.service;

import com.jyujuy.dayonetest.controller.response.ExamFailStudentResponse;
import com.jyujuy.dayonetest.controller.response.ExamPassStudentResponse;
import com.jyujuy.dayonetest.model.StudentFail;
import com.jyujuy.dayonetest.model.StudentPass;
import com.jyujuy.dayonetest.repository.StudentFailRepository;
import com.jyujuy.dayonetest.repository.StudentPassRepository;
import com.jyujuy.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Stream;

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

    @Test
    @DisplayName("합격자 명단 가져오기 검증")
    public void getPassStudentsListTest() {
        // given : 평균점수가 60점 미만인 경우
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        // 먼저 우리는 givenTestExam = "testexam" 인것만 가져오는지
        // 코드 상에서 filter 가 잘 걸리는지 확인해 볼겁니다.

        StudentPass expectStudent1 = StudentPass.builder().id(1L).studentName("jyujyu").exam("testexam").avgScore(70.0).build();
        StudentPass expectStudent2 = StudentPass.builder().id(2L).studentName("test").exam("testexam").avgScore(80.0).build();
        StudentPass notExpectStudent3 = StudentPass.builder().id(3L).studentName("iamnot").exam("secondExam").avgScore(90.0).build();
//        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
//                StudentPass.builder().id(1L).studentName("jyujyu").exam("testexam").avgScore(70.0).build(),
//                StudentPass.builder().id(2L).studentName("test").exam("testexam").avgScore(80.0).build(),
//                StudentPass.builder().id(3L).studentName("iamnot").exam("testexam").avgScore(90.0).build()
//        ));

        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(expectStudent1, expectStudent2, notExpectStudent3));

        StudentScoreService service = new StudentScoreService(studentScoreRepository, studentPassRepository, studentFailRepository);
        String givenTestExam = "testexam";

        // when
        var expectResponses = Stream.of(expectStudent1, expectStudent2).map((pass) -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore())).toList();
        List<ExamPassStudentResponse> responses = service.getPassStudentsList(givenTestExam);

//        responses.forEach((response) -> System.out.println(response.getStudentName() + " " + response.getAvgScore()));

//        expectResponses.forEach((response) -> {
//            System.out.println(response.getStudentName() + " " + response.getAvgScore());
//        });
//        System.out.println("==========");
//        responses.forEach((response) -> {
//            System.out.println(response.getStudentName() + " " + response.getAvgScore());
//        });
//        org.opentest4j.AssertionFailedError: iterable contents differ at index [0],
//        Expected :com.jyujuy.dayonetest.controller.response.ExamPassStudentResponse@6ecd665
//        Actual   :com.jyujuy.dayonetest.controller.response.ExamPassStudentResponse@45394b31
        // 단순하게 비교해서 참조값이 다른 상태인데 이를 해결하기 위해서는 @EqualsAndHashCode response에 추가해주면 된다
        Assertions.assertIterableEquals(expectResponses, responses);
    }

    @Test
    @DisplayName("불합격자 명단 가져오기")
    public void getFailStudentsListTest() {
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);
        String givenTestExam = "testexam";

        StudentFail notExpectStudent3 = StudentFail.builder().id(1L).studentName("jyujyu").exam("secondExam").avgScore(70.0).build();
        StudentFail expectStudent1 = StudentFail.builder().id(2L).studentName("test").exam(givenTestExam).avgScore(80.0).build();
        StudentFail expectStudent2 = StudentFail.builder().id(3L).studentName("iamnot").exam(givenTestExam).avgScore(90.0).build();

        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(expectStudent1, expectStudent2, notExpectStudent3));
        StudentScoreService service = new StudentScoreService(studentScoreRepository, studentPassRepository, studentFailRepository);

        // when
        var expectFailList = List.of(expectStudent1, expectStudent2).stream().map(
                (fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore())
        ).toList();
        List<ExamFailStudentResponse> responses = service.getFailStudentsList(givenTestExam);

        Assertions.assertIterableEquals(expectFailList, responses);
    }

}
