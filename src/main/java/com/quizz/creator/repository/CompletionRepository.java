package com.quizz.creator.repository;

import com.quizz.creator.pojo.Completion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionRepository extends JpaRepository<Completion, Long> {

    @Query("SELECT c FROM Completion c WHERE c.user.id = ?1")
    Page<Completion> findAllMinePaginated(Long userId, Pageable pageable);

}