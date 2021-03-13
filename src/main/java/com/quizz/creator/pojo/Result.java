package com.quizz.creator.pojo;

import lombok.Data;

@Data
public class Result {
    public static final Result SUCCESS = new Result(true, "Congratulations, you're right!");
    public static final Result FAIL = new Result(false, "Wrong answer! Please, try again.");

    private final boolean success;

    private final String feedback;
}
