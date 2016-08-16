package com.adaptionsoft.games.trivia.questions;

import org.junit.Before;
import org.junit.Test;

import static com.adaptionsoft.games.trivia.questions.Questions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QuestionsManagerTest {

    private QuestionsManager manager;

    @Before
    public void setup() {
        manager = new QuestionsManager(10);
    }

    @Test
    public void checkQuestionsAmount() {
        assertThat(manager.getAmountOfQuestionsInCategory(POP), is(10));
        assertThat(manager.getAmountOfQuestionsInCategory(ROCK), is(10));
        assertThat(manager.getAmountOfQuestionsInCategory(SCIENCE), is(10));
        assertThat(manager.getAmountOfQuestionsInCategory(SPORT), is(10));
    }

    @Test
    public void checkPopCategoryNumbers() {
        manager.askSelectedQuestion(0);
        assertThat(manager.getAmountOfQuestionsInCategory(POP), is(9));

        manager.askSelectedQuestion(4);
        assertThat(manager.getAmountOfQuestionsInCategory(POP), is(8));

        manager.askSelectedQuestion(8);
        assertThat(manager.getAmountOfQuestionsInCategory(POP), is(7));
    }

    @Test
    public void checkSportCategoryNumbers() {
        manager.askSelectedQuestion(2);
        assertThat(manager.getAmountOfQuestionsInCategory(SPORT), is(9));

        manager.askSelectedQuestion(6);
        assertThat(manager.getAmountOfQuestionsInCategory(SPORT), is(8));

        manager.askSelectedQuestion(10);
        assertThat(manager.getAmountOfQuestionsInCategory(SPORT), is(7));
    }

    @Test
    public void checkScienceCategoryNumbers() {
        manager.askSelectedQuestion(1);
        assertThat(manager.getAmountOfQuestionsInCategory(SCIENCE), is(9));

        manager.askSelectedQuestion(5);
        assertThat(manager.getAmountOfQuestionsInCategory(SCIENCE), is(8));

        manager.askSelectedQuestion(9);
        assertThat(manager.getAmountOfQuestionsInCategory(SCIENCE), is(7));
    }

    @Test
    public void checkRockCategoryNumbers() {
        manager.askSelectedQuestion(3);
        assertThat(manager.getAmountOfQuestionsInCategory(ROCK), is(9));

        manager.askSelectedQuestion(7);
        assertThat(manager.getAmountOfQuestionsInCategory(ROCK), is(8));

        manager.askSelectedQuestion(11);
        assertThat(manager.getAmountOfQuestionsInCategory(ROCK), is(7));

        manager.askSelectedQuestion(12);
        assertThat(manager.getAmountOfQuestionsInCategory(ROCK), is(6));

        manager.askSelectedQuestion(20);
        assertThat(manager.getAmountOfQuestionsInCategory(ROCK), is(5));

        manager.askSelectedQuestion(999);
        assertThat(manager.getAmountOfQuestionsInCategory(ROCK), is(4));
    }

    @Test
    public void checkSelectedCategory() {
        assertThat(manager.getLastQuestionCategory(), is(ROCK));
        manager.askSelectedQuestion(1);
        assertThat(manager.getLastQuestionCategory(), is(SCIENCE));
        manager.askSelectedQuestion(10);
        assertThat(manager.getLastQuestionCategory(), is(SPORT));
        manager.askSelectedQuestion(4);
        assertThat(manager.getLastQuestionCategory(), is(POP));
        manager.askSelectedQuestion(47);
        assertThat(manager.getLastQuestionCategory(), is(ROCK));
    }

}