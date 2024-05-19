package com.workspace.repository;

import com.workspace.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Group, Long> {
    @Query(
            value = "SELECT * FROM groups WHERE user_id = :user_id",
            nativeQuery = true)
    List<Group> findCollectionsByUserId(@Param("user_id") Long userId);

//    @Query(
//            value = "SELECT * FROM groups WHERE user_id = :user_id",
//            nativeQuery = true)
//    List<String> getCollectionNamesByPhraseId(@Param("phrase_id") Long phraseId);
}

