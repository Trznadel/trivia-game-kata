package com.adaptionsoft.games.trivia.answers;

import com.adaptionsoft.games.trivia.game.Player;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CorrectAnswerTest {

    private Player player;
    private Answer answer;

    @Before
    public void setup() {
        player = new Player("John", 0);
        answer = new CorrectAnswer();
    }

    @Test
    public void playerNotInPenaltyBoxAnswersCorrectly() {
        player.updatePosition(5);

        answer.answerQuestion(player);

        assertThat(player.getScore(), is(1));
        assertThat(player.isInPenaltyBox(), is(false));
        assertThat(player.getPosition(), is(5));
    }

    @Test
    public void playerInPenaltyBoxAnswersCorrectly() {
        player.putInPenaltyBox();
        player.updatePosition(7);

        answer.answerQuestion(player);

        assertThat(player.getScore(), is(1));
        assertThat(player.isInPenaltyBox(), is(true));
        assertThat(player.getPosition(), is(7));
    }

}