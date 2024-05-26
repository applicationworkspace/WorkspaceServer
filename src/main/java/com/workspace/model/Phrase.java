package com.workspace.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "phrases")
public class Phrase { //TODO refactor dbDTO
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @CreationTimestamp
//    @Column(name = "create_date") // TODO refactor
    private Date createdAt;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "text")
    private String text;

    @Column(name = "repeat_count")
    private int repeatCount = 0;

    @Column(name = "last_repeat_date")
    private Date lastRepeatDate;

    @Column(name = "translation")
    private String translation;

    @Column(name = "definition")
    private String definition;

    @ElementCollection
    @CollectionTable(name = "phrase_examples", joinColumns = @JoinColumn(name = "phrase_id"))
    @Column(name = "phrase_examples")
    private List<String> examples;

    @JsonIgnoreProperties(value = "phrases")
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "group_phrase",
            joinColumns = @JoinColumn(name = "phrase_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private List<Group> groups;
}