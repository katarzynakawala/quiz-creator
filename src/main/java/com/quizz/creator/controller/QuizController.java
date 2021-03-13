package com.quizz.creator.controller;

import com.quizz.creator.pojo.*;
import com.quizz.creator.service.CompletionService;
import com.quizz.creator.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private CompletionService completionService;

    @GetMapping
    public Page<Quiz> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return quizService.findAll(page, size);
    }

    @GetMapping("/completed")
    public Page<Completion> getMyCompletions(@AuthenticationPrincipal User user,
                                             @RequestParam(defaultValue = "0") Integer page,
                                             @RequestParam(defaultValue = "10") Integer size) {
        return completionService.findAllMine(user, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getById(@PathVariable Long id) {
        return ResponseEntity.of(quizService.findById(id));
    }

    @PostMapping
    public Quiz create(@AuthenticationPrincipal User user, @RequestBody @Valid Quiz quiz) {
        quiz.setUser(user);

        return quizService.create(quiz);
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<Result> solve(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody Answer answer) {
        return ResponseEntity.of(quizService.solve(id, user, answer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return quizService.findById(id)
                .map(quiz -> {
                    if (quiz.getUser().equals(user)) {
                        quizService.delete(quiz);

                        return ResponseEntity.noContent().build();
                    }

                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
