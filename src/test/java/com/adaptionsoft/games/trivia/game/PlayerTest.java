package com.adaptionsoft.games.trivia.game;

import com.adaptionsoft.games.trivia.answers.CorrectAnswer;
import com.adaptionsoft.games.trivia.answers.WrongAnswer;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PlayerTest {

    private Player player;

    @Before
    public void setup() {
        player = new Player("John", 0);
    }

    @Test
    public void checkPlayerName() {
        assertThat(player.getName(), is("John"));
    }

    @Test
    public void checkPlayerId() {
        assertThat(player.getId(), is(0));
    }

    @Test
    public void updatePlayerPosition_5() {
        player.updatePosition(5);

        assertThat(player.getPosition(), is(5));
    }

    @Test
    public void updatePlayerPosition_11() {
        player.updatePosition(11);

        assertThat(player.getPosition(), is(11));
    }

    @Test
    public void updatePlayerPosition_12() {
        player.updatePosition(12);

        assertThat(player.getPosition(), is(0));
    }

    @Test
    public void updatePlayerPosition_30() {
        player.updatePosition(30);

        assertThat(player.getPosition(), is(18));
    }

    @Test
    public void answersCorrectly() {
        player.answerQuestion(new CorrectAnswer());

        assertThat(player.getScore(), is(1));
        assertThat(player.isInPenaltyBox(), is(false));
    }

    @Test
    public void answersCorrectlyTwice() {
        player.answerQuestion(new CorrectAnswer());
        player.answerQuestion(new CorrectAnswer());

        assertThat(player.getScore(), is(2));
        assertThat(player.isInPenaltyBox(), is(false));
    }

    @Test
    public void answersIncorrectly() {
        player.answerQuestion(new WrongAnswer());

        assertThat(player.getScore(), is(0));
        assertThat(player.isInPenaltyBox(), is(true));
    }

    @Test
    public void answersCorrectlyAndIncorrectly() {
        player.answerQuestion(new CorrectAnswer());
        player.answerQuestion(new WrongAnswer());

        assertThat(player.getScore(), is(1));
        assertThat(player.isInPenaltyBox(), is(true));
    }

    @Test
    public void answersInCorrectlyAndCorrectly() {
        player.answerQuestion(new WrongAnswer());
        player.answerQuestion(new CorrectAnswer());

        assertThat(player.getScore(), is(1));
        assertThat(player.isInPenaltyBox(), is(true));
    }
}