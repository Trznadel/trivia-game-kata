package com.adaptionsoft.games.trivia.questions;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static com.adaptionsoft.games.trivia.questions.Questions.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class QuestionsTest {

    private static final int AMOUNT_50 = 50;

    @Before
    public void setup() {
        ROCK.prepareQuestions(AMOUNT_50);
        POP.prepareQuestions(AMOUNT_50);
        SPORT.prepareQuestions(AMOUNT_50);
        SCIENCE.prepareQuestions(AMOUNT_50);
    }

    @Test
    public void checkCategories() {
        assertThat(Questions.values().length, is(4));
        assertThat(ROCK.toString(), is("Rock"));
        assertThat(POP.toString(), is("Pop"));
        assertThat(SPORT.toString(), is("Sport"));
        assertThat(SCIENCE.toString(), is("Science"));
    }

    @Test
    public void checkDefaultQuestionsAmount() {
        assertThat(ROCK.getAmountOfQuestions(), is(AMOUNT_50));
        assertThat(SCIENCE.getAmountOfQuestions(), is(AMOUNT_50));
        assertThat(SPORT.getAmountOfQuestions(), is(AMOUNT_50));
        assertThat(POP.getAmountOfQuestions(), is(AMOUNT_50));
    }

    @Test
    public void checkAmountOfQuestionsAfterRemovingOne() {
        ROCK.prepareQuestions(10);

        assertThat(ROCK.getAmountOfQuestions(), is(10));
        ROCK.removeQuestionFromPool();
        assertThat(ROCK.getAmountOfQuestions(), is(9));
    }

    @Test(expected = NoSuchElementException.class)
    public void throwExceptionWhenAllQuestionsRemoved() {
        while(ROCK.getAmountOfQuestions() > -1) {
            ROCK.removeQuestionFromPool();
        }

    }
}