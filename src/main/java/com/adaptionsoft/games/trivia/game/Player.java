package com.adaptionsoft.games.trivia.game;

import com.adaptionsoft.games.trivia.answers.Answer;

public class Player {

    private final String name;
    private final int id;
    private int score;
    private int position;
    private boolean isInPenaltyBox;

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void updatePosition(int roll) {
        position += roll;
        if(position > 11) {
            position = position - 12;
        }
    }

    public void answerQuestion(Answer answer) {
        answer.answerQuestion(this);
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }

    public void putInPenaltyBox() {
        isInPenaltyBox = true;
    }

    public void addPoint() {
        score++;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }
}
