package com.adaptionsoft.games.trivia.answers;

import com.adaptionsoft.games.trivia.game.Player;

public class WrongAnswer implements Answer {

    @Override
    public void answerQuestion(Player player) {
        System.out.println("Question was incorrectly answered");
        System.out.println(player + " was sent to the penalty box");
        player.putInPenaltyBox();
    }
}
