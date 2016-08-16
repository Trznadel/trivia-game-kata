package com.adaptionsoft.games.trivia.questions;

import java.util.LinkedList;

public enum Questions {

    POP("Pop"),
    ROCK("Rock"),
    SCIENCE("Science"),
    SPORT("Sport");

    private final String category;
    private LinkedList<String> questions;

    Questions(String category) {
        this.category = category;
    }

    public void prepareQuestions(int amount) {
        questions = new LinkedList<>();
        for (int i = 0; i < amount; i++) {
            questions.addLast((category + " Question " + i));
        }
    }

    public final int getAmountOfQuestions() {
        return questions.size();
    }

    public final String removeQuestionFromPool() {
        return questions.removeFirst();
    }

    @Override
    public String toString() {
        return category;
    }
}
