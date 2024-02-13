package com.jyujuy.dayonetest.service;

import com.jyujuy.dayonetest.MyCalculator;
import com.jyujuy.dayonetest.controller.response.ExamFailStudentResponse;
import com.jyujuy.dayonetest.controller.response.ExamPassStudentResponse;
import com.jyujuy.dayonetest.model.*;
import com.jyujuy.dayonetest.repository.StudentFailRepository;
import com.jyujuy.dayonetest.repository.StudentPassRepository;
import com.jyujuy.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Stream;

public class StudentScoreServiceMockTest {
    private StudentScoreService studentScoreService;
    private StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
    private StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
    private StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

    @BeforeEach // 모든 테스트가 실행되기전에 실행되는 어노테이션
    public void beforeEach() {
        studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        studentPassRepository = Mockito.mock(StudentPassRepository.class);
        studentFailRepository = Mockito.mock(StudentFailRepository.class);
        studentScoreService = new StudentScoreService(studentScoreRepository, studentPassRepository, studentFailRepository);
    }

    @Test
    public void firstSaveScroeMockTest() {
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
        StudentScore expectStudentScore = StudentScoreTestDataBuilder.passed()
                // builder 패턴의 특징은 이렇게 오버라이딩이 가능합니다.
                .studentName("Student New Name")
                .build();

//        StudentPass expectStudentPass = StudentPass.builder().studentName(expectStudentScore.getStudentName()).exam(expectStudentScore.getExam()).avgScore((new MyCalculator(0.0)).add(expectStudentScore.getKorScore().doubleValue()).add(expectStudentScore.getEnglishScore().doubleValue()).add(expectStudentScore.getMathScore().doubleValue()).divide(3.0).getResult()).build();
        StudentPass expectStudentPass = StudentPassFixture.create(expectStudentScore);
        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);
//        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);


        // when
        studentScoreService.saveScore(expectStudentScore.getStudentName(), expectStudentPass.getExam(), expectStudentScore.getKorScore(), expectStudentScore.getEnglishScore(), expectStudentScore.getMathScore());

        // then
//        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
//        Mockito.verify(studentPassRepository, Mockito.times(1)).save(Mockito.any());
//        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());

        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentScore.getStudentName(), capturedStudentScore.getStudentName());
        Assertions.assertEquals(expectStudentScore.getExam(), capturedStudentScore.getExam());
        Assertions.assertEquals(expectStudentScore.getKorScore(), capturedStudentScore.getKorScore());
        Assertions.assertEquals(expectStudentScore.getEnglishScore(), capturedStudentScore.getEnglishScore());
        Assertions.assertEquals(expectStudentScore.getMathScore(), capturedStudentScore.getMathScore());


        Mockito.verify(studentPassRepository, Mockito.times(1)).save(studentPassArgumentCaptor.capture());
        StudentPass capturedStudentPass = studentPassArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentPass.getStudentName(), capturedStudentPass.getStudentName());
        Assertions.assertEquals(expectStudentPass.getExam(), capturedStudentPass.getExam());
        Assertions.assertEquals(expectStudentPass.getAvgScore(), capturedStudentPass.getAvgScore());

        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 미만인 경우")
    public void saveScoreMockTest2() {
        // given : 평균점수가 60점 미만인 경우

        // StudentScore.class 에 대한 ArgumentCaptor 를 만들어주는겁니다.
        StudentScore expectStudentScore = StudentScoreFixture.failed();

//        StudentFail expectStudentFail = StudentFail.builder().studentName(expectStudentScore.getStudentName()).exam(expectStudentScore.getExam()).avgScore((new MyCalculator(0.0)).add(expectStudentScore.getKorScore().doubleValue()).add(expectStudentScore.getEnglishScore().doubleValue()).add(expectStudentScore.getMathScore().doubleValue()).divide(3.0).getResult()).build();

        StudentFail expectStudentFail = StudentFailFixture.create(expectStudentScore);
        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);
//        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);
        // when
        studentScoreService.saveScore(expectStudentScore.getStudentName(), expectStudentScore.getExam(), expectStudentScore.getKorScore(), expectStudentScore.getEnglishScore(), expectStudentScore.getMathScore());

        // then
//        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
//        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
//        Mockito.verify(studentFailRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentScore.getStudentName(), capturedStudentScore.getStudentName());
        Assertions.assertEquals(expectStudentScore.getExam(), capturedStudentScore.getExam());
        Assertions.assertEquals(expectStudentScore.getKorScore(), capturedStudentScore.getKorScore());
        Assertions.assertEquals(expectStudentScore.getEnglishScore(), capturedStudentScore.getEnglishScore());
        Assertions.assertEquals(expectStudentScore.getMathScore(), capturedStudentScore.getMathScore());

        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());

        Mockito.verify(studentFailRepository, Mockito.times(1)).save(studentFailArgumentCaptor.capture());
        StudentFail capturedStudentFail = studentFailArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentFail.getStudentName(), capturedStudentFail.getStudentName());
        Assertions.assertEquals(expectStudentFail.getExam(), capturedStudentFail.getExam());
        Assertions.assertEquals(expectStudentFail.getAvgScore(), capturedStudentFail.getAvgScore());
    }

    @Test
    @DisplayName("합격자 명단 가져오기 검증")
    public void getPassStudentsListTest() {
        // given : 평균점수가 60점 미만인 경우
        // 먼저 우리는 givenTestExam = "testexam" 인것만 가져오는지
        // 코드 상에서 filter 가 잘 걸리는지 확인해 볼겁니다.

//        StudentPass expectStudent1 = StudentPass.builder().id(1L).studentName("jyujyu").exam("testexam").avgScore(70.0).build();
        StudentPass expectStudent1 = StudentPassFixture.create("jyujyu", "testexam");
//        StudentPass expectStudent2 = StudentPass.builder().id(2L).studentName("test").exam("testexam").avgScore(80.0).build();
        StudentPass expectStudent2 = StudentPassFixture.create("test", "testexam");
//        StudentPass notExpectStudent3 = StudentPass.builder().id(3L).studentName("iamnot").exam("secondExam").avgScore(90.0).build();
        StudentPass notExpectStudent3 = StudentPassFixture.create("anotherStudent", "secondExam");
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

        String givenTestExam = "testexam";

//        StudentFail notExpectStudent3 = StudentFail.builder().id(1L).studentName("jyujyu").exam("secondExam").avgScore(70.0).build();
//        StudentFail expectStudent1 = StudentFail.builder().id(2L).studentName("test").exam(givenTestExam).avgScore(80.0).build();
//        StudentFail expectStudent2 = StudentFail.builder().id(3L).studentName("iamnot").exam(givenTestExam).avgScore(90.0).build();
        StudentFail notExpectStudent3 = StudentFailFixture.create("jyujyu", "anotherExam");
        StudentFail expectStudent1 = StudentFailFixture.create("testName", givenTestExam);
        StudentFail expectStudent2 = StudentFailFixture.create("testName 2", givenTestExam);

        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(expectStudent1, expectStudent2, notExpectStudent3));
        StudentScoreService service = new StudentScoreService(studentScoreRepository, studentPassRepository, studentFailRepository);

        // when
        var expectFailList = List.of(expectStudent1, expectStudent2).stream().map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore())).toList();
        List<ExamFailStudentResponse> responses = service.getFailStudentsList(givenTestExam);

        Assertions.assertIterableEquals(expectFailList, responses);
    }

}
