package com.quizz.creator.service;

import com.quizz.creator.pojo.*;
import com.quizz.creator.repository.CompletionRepository;
import com.quizz.creator.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class QuizService {

    @Autowired
    private CompletionRepository completionRepository;

    @Autowired
    private QuizRepository quizRepository;

    public Page<Quiz> findAll(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        return quizRepository.findAll(paging);
    }

    public Optional<Quiz> findById(Long id) {
        return quizRepository.findById(id);
    }

    public Quiz create(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void delete(Quiz quiz) {
        quizRepository.delete(quiz);
    }

    public Optional<Result> solve(Long id, User user, Answer answer) {
        return findById(id).map(quiz -> {
            Integer[] answers = Objects.requireNonNullElse(quiz.getAnswer(), new Integer[]{});
            if (Set.of(answers).equals(answer.getOptions())) {
                Completion completion = new Completion();
                completion.setUser(user);
                completion.setQuiz(quiz);
                completion.setCompletedAt(LocalDateTime.now());

                completionRepository.save(completion);

                return Result.SUCCESS;
            }

            return Result.FAIL;
        });
    }

}