package com.jyujuy.dayonetest.controller;

import com.jyujuy.dayonetest.controller.request.SaveExamScoreRequest;
import com.jyujuy.dayonetest.controller.response.ExamFailStudentResponse;
import com.jyujuy.dayonetest.controller.response.ExamPassStudentResponse;
import com.jyujuy.dayonetest.service.StudentScoreService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ScoreApi {
 private final StudentScoreService studentScoreService;
    @PutMapping("/exam/{exam}/score")
    public void save(
            @PathVariable("exam") String exam,
            @RequestBody SaveExamScoreRequest request
    ) {
         studentScoreService.saveScore(request.getStudentName(),
                 exam,
                 request.getKorScore(),
                 request.getEnglishScore(),
                 request.getMathScore()
         );
    }

    @GetMapping("/exam/{exam}/pass")
    public List<ExamPassStudentResponse> pass(
            @PathVariable("exam") String exam
    ) {
//        return List.of(
//                new ExamPassStudentResponse("jyujyu", 60.0)
//        );
        return studentScoreService.getPassStudentsList(exam);
    }

    @GetMapping("/exam/{exam}/fail")
    public List<ExamFailStudentResponse> fail(@PathVariable("exam") String exam) {
//        return List.of(
//                new ExamFailStudentResponse("jyujyu", 20.0)
//        );
        return studentScoreService.getFailStudentsList(exam);
    }


}
