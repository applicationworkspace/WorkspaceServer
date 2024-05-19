package com.workspace.repository;

import com.workspace.model.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface PhraseRepository extends JpaRepository<Phrase, Long> {
    @Query(
            value = "SELECT * FROM phrases WHERE user_id = :user_id",
            nativeQuery = true)
    List<Phrase> findPhrasesByUserId(@Param("user_id") Long userId);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE phrases set repeat_count = :repeats WHERE id = :id",
            nativeQuery = true)
    void updatePhraseRepeatCountById(@Param("id") Long phraseId, @Param("repeats") int repeats);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE phrases set last_repeat_date = :lastRepeatDate WHERE id = :id",
            nativeQuery = true)
    void updatePhraseRepeatDateById(@Param("id") Long phraseId, @Param("lastRepeatDate") Date lastRepeatDate);


//    List<Phrase> findByDateBetween(LocalDateTime from, LocalDateTime to);
}