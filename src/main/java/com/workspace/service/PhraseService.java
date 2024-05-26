package com.workspace.service;

import com.workspace.dto.collection.PhraseStatusRequest;
import com.workspace.model.Group;
import com.workspace.model.Phrase;
import com.workspace.repository.CollectionRepository;
import com.workspace.repository.PhraseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PhraseService {

    @Autowired
    PhraseRepository phraseRepository;

    @Autowired
    CollectionRepository collectionRepository;

    public List<Phrase> getPhrasesByUserId(Long userId) {
        return phraseRepository.findPhrasesByUserId(userId);
    }

    public void addPhrase(Phrase userPhrase) {
        phraseRepository.save(userPhrase);
    }

    public void updatePhraseStatus(PhraseStatusRequest statusRequest) {
        Optional<Phrase> phrase = phraseRepository.findById(statusRequest.getId());
        if (phrase.isPresent()) {
            int newRepeatCounter = phrase.get().getRepeatCount();
            if (statusRequest.getIsKnown()) {
                newRepeatCounter++;
            } else {
                newRepeatCounter = 0;
            }
            phraseRepository.updatePhraseRepeatDateById(statusRequest.getId(), new Date());
            phraseRepository.updatePhraseRepeatCountById(statusRequest.getId(), newRepeatCounter);
        }
    }

    public void deletePhrase(Long phraseId) {
        phraseRepository.deleteById(phraseId);
    }

    public List<Group> getCollectionsByUserId(Long userId) {
        return collectionRepository.findCollectionsByUserId(userId);
    }

    public Group addGroup(Group group) {
        return collectionRepository.save(group);
    }

    public void deleteGroup(Long phraseId) {
        collectionRepository.deleteById(phraseId);
    }

//    public List<String> getGroupNamesForPhrase(Long phraseId) {
//        return collectionRepository.getCollectionNamesByPhraseId(phraseId);
//    }

//    public Phrase getPhrasePrediction() {
//        return Phrase();
//    }
}
