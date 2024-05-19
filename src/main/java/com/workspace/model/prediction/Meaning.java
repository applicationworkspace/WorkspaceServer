package com.workspace.model.prediction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meaning {
    private String partOfSpeech;
    private List<Definition> definitions;
}
