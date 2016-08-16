package com.adaptionsoft.games.trivia.answers;

import com.adaptionsoft.games.trivia.game.Player;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WrongAnswerTest {
    private Player player;
    private Answer answer;

    @Before
    public void setup() {
        player = new Player("John", 0);
        answer = new WrongAnswer();
    }

    @Test
    public void playerNotInPenaltyBoxAnswersWrongly() {
        player.updatePosition(5);

        answer.answerQuestion(player);

        assertThat(player.getScore(), is(0));
        assertThat(player.isInPenaltyBox(), is(true));
        assertThat(player.getPosition(), is(5));
    }

    @Test
    public void playerInPenaltyBoxAnswersWrongly() {
        player.putInPenaltyBox();
        player.updatePosition(7);

        answer.answerQuestion(player);

        assertThat(player.getScore(), is(0));
        assertThat(player.isInPenaltyBox(), is(true));
        assertThat(player.getPosition(), is(7));
    }
}