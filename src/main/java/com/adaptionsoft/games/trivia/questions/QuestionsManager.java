package com.adaptionsoft.games.trivia.questions;

import java.util.HashMap;
import java.util.Map;

import static com.adaptionsoft.games.trivia.questions.Questions.*;

public class QuestionsManager {

    private Map<Integer, Questions> selector = new HashMap<>();
    private Questions selectedCategory;

    
    public QuestionsManager(int amount) {
        qualifyCategories();
        selectedCategory = ROCK;
        prepareQuestions(amount);
    }

    private void prepareQuestions(int amount) {
        for (Questions q : Questions.values()) {
            q.prepareQuestions(amount);
        }
    }

    public int getAmountOfQuestionsInCategory(Questions q) {
        return q.getAmountOfQuestions();
    }

    public Questions getLastQuestionCategory() {
        return selectedCategory;
    }

    public void askSelectedQuestion(int categoryNumber) {
        selectedCategory = selectQuestion(categoryNumber);
        selectedCategory.removeQuestionFromPool();
    }

    private Questions selectQuestion(int categoryNumber) {
        return selector.getOrDefault(categoryNumber, ROCK);
    }

    private void qualifyCategories() {
        selector.put(0, POP);
        selector.put(4, POP);
        selector.put(8, POP);
        selector.put(1, SCIENCE);
        selector.put(5, SCIENCE);
        selector.put(9, SCIENCE);
        selector.put(2, SPORT);
        selector.put(6, SPORT);
        selector.put(10, SPORT);
    }

}
