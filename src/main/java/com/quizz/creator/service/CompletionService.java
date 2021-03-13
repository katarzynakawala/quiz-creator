package com.quizz.creator.service;

import com.quizz.creator.pojo.Completion;
import com.quizz.creator.pojo.User;
import com.quizz.creator.repository.CompletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompletionService {

    @Autowired
    private CompletionRepository completionRepository;

    public Page<Completion> findAllMine(User user, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("completedAt").descending());
        return completionRepository.findAllMinePaginated(user.getId(), pageable);
    }

    public void create(Completion completion) {
        completionRepository.save(completion);
    }

}