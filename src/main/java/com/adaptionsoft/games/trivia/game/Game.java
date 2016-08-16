package com.adaptionsoft.games.trivia.game;

import com.adaptionsoft.games.trivia.questions.Questions;
import com.adaptionsoft.games.trivia.questions.QuestionsManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Game {

    private Map<Integer, Player> players = new HashMap<>();
    private QuestionsManager questionsManager;
    private Player currentPlayer;


    public Game(int amount) {
        questionsManager = new QuestionsManager(amount);
    }

    public void addPlayer(String playerName) {
        currentPlayer = new Player(playerName, players.size());
        players.put(players.size(), currentPlayer);
        System.out.println(playerName + " was added");
        System.out.println("Player number " + players.size());
    }

    public boolean roll(int roll) {
        selectNextPlayer();
        System.out.println(currentPlayer + " is the current player");
        System.out.println("They have rolled a " + roll);
        return wasQuestionAsked(roll);
    }

    public boolean checkIfPlayerWonTheGame() {
        return currentPlayer.getScore() == 6;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public int getAmountOfQuestionsInCategory(Questions q) {
        return questionsManager.getAmountOfQuestionsInCategory(q);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Optional<Player> getPlayerById(int id) {
        return Optional.ofNullable(players.get(id));
    }

    private boolean wasQuestionAsked(int roll) {
        if (currentPlayer.isInPenaltyBox() && isNotGettingOutOfPenaltyBox(roll)) {
            return false;
        } else {
            askProperCategoryQuestion(roll);
            return true;
        }
    }

    private boolean isNotGettingOutOfPenaltyBox(int roll) {
        if (roll % 2 == 0) {
            System.out.println(currentPlayer + " is not getting out of the penalty box");
            return true;
        } else {
            System.out.println(currentPlayer + " is getting out of the penalty box");
            return false;
        }
    }

    private void askProperCategoryQuestion(int roll) {
        currentPlayer.updatePosition(roll);
        questionsManager.askSelectedQuestion(currentPlayer.getPosition());
        System.out.println(currentPlayer + "'s new location is " + currentPlayer.getPosition());
        System.out.println("The category is " + questionsManager.getLastQuestionCategory());
    }

    private void selectNextPlayer() {
        if (currentPlayer == null || isCurrentPlayerLastOne()) {
            currentPlayer = players.get(0);
        } else {
            currentPlayer = players.get(currentPlayer.getId() + 1);
        }
    }

    private boolean isCurrentPlayerLastOne() {
        return currentPlayer.getId() + 1 == players.size();
    }
}
