package com.workspace.model.prediction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryEntry {
    private String word;
    private String phonetic;
    private List<Meaning> meanings;
}
