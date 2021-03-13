package com.quizz.creator.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Answer {

    @JsonProperty("answer")
    private Set<Integer> options = new HashSet<>();

}