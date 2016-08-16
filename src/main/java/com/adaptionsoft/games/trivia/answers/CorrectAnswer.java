package com.adaptionsoft.games.trivia.answers;

import com.adaptionsoft.games.trivia.game.Player;

public class CorrectAnswer implements Answer {

    @Override
    public void answerQuestion(Player player) {
        System.out.println("Answer was correct!!!!");
        player.addPoint();
        System.out.println(player + " now has " + player.getScore() + " Gold Coins.");
    }
}
