
package com.adaptionsoft.games.trivia;
import com.adaptionsoft.games.trivia.game.Game;
import com.adaptionsoft.games.trivia.answers.CorrectAnswer;
import com.adaptionsoft.games.trivia.answers.WrongAnswer;
import com.adaptionsoft.games.trivia.game.Player;

import java.util.Random;


public class GameRunner {
	private static final Random rand = new Random(10);

	public static void main(String[] args) {
		Game aGame = new Game(50);
		aGame.addPlayer("Chet");
		aGame.addPlayer("Pat");
		aGame.addPlayer("Sue");

		while (!aGame.checkIfPlayerWonTheGame()) {
			simulateRoll(aGame);
		}
	}

	private static void simulateRoll(Game aGame) {
		if(aGame.roll(rand.nextInt(5) + 1)) {
            Player player = aGame.getCurrentPlayer();
            simulateAnswer(player);
        }
	}

	private static void simulateAnswer(Player player) {
		if (rand.nextInt(9) == 7) {
            player.answerQuestion(new WrongAnswer());
        } else {
            player.answerQuestion(new CorrectAnswer());
        }
	}

}
