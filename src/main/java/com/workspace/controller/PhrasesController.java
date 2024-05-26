package com.workspace.controller;

import com.workspace.dto.collection.PhraseStatusRequest;
import com.workspace.exception.AppException;
import com.workspace.model.Group;
import com.workspace.model.Phrase;
import com.workspace.security.CurrentUser;
import com.workspace.security.UserPrincipal;
import com.workspace.service.PhraseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class PhrasesController { //TODO create colection controller

    private PhraseService phraseService;

    private Calendar calendar = Calendar.getInstance();

    @Autowired
    public PhrasesController(PhraseService userService) {
        this.phraseService = userService;
    }

    @GetMapping("phrases")
    @PreAuthorize("hasRole('USER')")
    public List<Phrase> getAllPhrases(@CurrentUser UserPrincipal currentUser) {
        return phraseService.getPhrasesByUserId(currentUser.getId());
    }

    @GetMapping("phrases/phrases-game")
    @PreAuthorize("hasRole('USER')")
    public List<Phrase> getPhrasesForGame(@CurrentUser UserPrincipal currentUser) {
        List<Phrase> allPhrases = phraseService.getPhrasesByUserId(currentUser.getId()) //TODO move logic to service
                .stream()
                .filter(phrase -> phrase.getRepeatCount() < 4)
                .collect(Collectors.toList());
        Collections.shuffle(allPhrases);
        int size = Math.min(allPhrases.size(), 5); //TODO move to const

        return filterByRepeatDate(allPhrases)
                .stream()
                .limit(size)
                .collect(Collectors.toList());
    }

    private List<Phrase> filterByRepeatDate(List<Phrase> allPhrases) {
        Date now = new Date();
        return allPhrases.stream()
                .filter(phrase -> {
                    if (phrase.getLastRepeatDate() == null || phrase.getRepeatCount() == 0) return true;

                    calendar.setTime(phrase.getLastRepeatDate());
                    if (phrase.getRepeatCount() == 1) {
                        calendar.add(Calendar.SECOND, 10);
                    } else if (phrase.getRepeatCount() == 2) {
                        calendar.add(Calendar.SECOND, 20);
                    } else if (phrase.getRepeatCount() == 3) {
                        calendar.add(Calendar.SECOND, 30);
                    }
                    return calendar.getTime().before(now);
                })
                .collect(Collectors.toList());
    }

    @PostMapping("collections/{collectionId}/phrase")
    @PreAuthorize("hasRole('USER')")
    public void addPhrase(@CurrentUser UserPrincipal currentUser,
                          @RequestBody Phrase phrase,
                          @PathVariable(value = "collectionId") Long collectionId) {

        Group selectedCollection = phraseService.getCollectionsByUserId(currentUser.getId())
                .stream()
                .filter(collection -> collection.getId().equals(collectionId)) //TODO check statement
                .findFirst()
                .orElseThrow(() -> new AppException("Not found Collection with id = " + collectionId));

        phrase.setUserId(currentUser.getId());
        phrase.setGroups(Collections.singletonList(selectedCollection));
//        phrase.setGroups(Set.of(selectedCollection));
        phraseService.addPhrase(phrase);
    }

    @PostMapping("phrases/status")
    @PreAuthorize("hasRole('USER')")
    public void updatePhraseStatus(@CurrentUser UserPrincipal currentUser,
                                   @RequestBody PhraseStatusRequest statusRequest) {
        phraseService.updatePhraseStatus(statusRequest);
    }

    @DeleteMapping("phrases")
    @PreAuthorize("hasRole('USER')")
    public void removePhrase(@RequestParam Long phraseId) {
        phraseService.deletePhrase(phraseId); //TODO check other user permission
    }

    @GetMapping("collections")
    @PreAuthorize("hasRole('USER')")
    public List<Group> getAllUserCollections(@CurrentUser UserPrincipal currentUser) { // TODO refactor group -> collection
        List<Group> userGroups = phraseService.getCollectionsByUserId(currentUser.getId());
//        List<String> groups = phraseService.getGroupNamesForPhrase(userGroups[1].phras)
        return userGroups;
    }

    @PostMapping("collections")
    @PreAuthorize("hasRole('USER')")
    public Group addGroup(@CurrentUser UserPrincipal currentUser,
                                   @RequestBody Group groupRequest) {

        groupRequest.setUserId(currentUser.getId());
        return phraseService.addGroup(groupRequest);
    }

    @DeleteMapping("collections")
    @PreAuthorize("hasRole('USER')")
    public void deleteGroup(@RequestParam Long groupId) {
        phraseService.deleteGroup(groupId);
    }

//    @GetMapping("phrases/prediction")
//    @PreAuthorize("hasRole('USER')")
//    public Phrase getPhrasePrediction(@RequestParam String phraseText) {
//        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + phraseText;
//
////        WebClient.Builder builder = WebClient.builder();
////
////        List<DictionaryEntry> dictionaryEntries = builder.build()
////                .get()
////                .uri(url)
////                .retrieve()
////                .bodyToMono(new ParameterizedTypeReference<List<DictionaryEntry>>() {}) //TODO gson
////                .block();
//
////        Phrase predictedPhrase = new Phrase(1L, 1L, new Date(), "image", "text", 0, new Date(), "dddd", "phraseDefinition", List.of("example1"), List.of());
//
////        if (dictionaryEntries != null && !dictionaryEntries.isEmpty() && dictionaryEntries.get(0) != null) {
////            DictionaryEntry predictedEntry = dictionaryEntries.get(0);
////            predictedPhrase.setText(predictedEntry.getWord());
////            predictedPhrase.setExamples(List.of(predictedEntry.getPhonetic()));
////            predictedPhrase.setDefinition(predictedEntry.getMeanings().get(0).getDefinitions().get(0).getDefinition());
////        }
//
//        return predictedPhrase;
//    }

    //TODO phrases with group description /// groups with phrases list (response style)

}
