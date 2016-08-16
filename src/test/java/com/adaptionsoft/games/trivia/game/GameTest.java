package com.adaptionsoft.games.trivia.game;

import com.adaptionsoft.games.trivia.answers.Answer;
import com.adaptionsoft.games.trivia.answers.CorrectAnswer;
import com.adaptionsoft.games.trivia.answers.WrongAnswer;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.stream.IntStream;

import static com.adaptionsoft.games.trivia.questions.Questions.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    private Random rand;
    private boolean isWinner;

    @Before
    public void setup() {
        game = new Game(50);
        rand = new Random(1);
    }

    @Test
    public void createGame() {
        assertQuestionsAmounts(50, 50, 50, 50);
    }

    @Test
    public void addFiveNumberOfPlayers() {
        addPlayers("John", "Mark", "Bob", "Tom", "Andy");
        assertThat(game.howManyPlayers(), is(5));
    }

    @Test
    public void isGamePlayable() {
        game.addPlayer("John");
        assertFalse(game.isPlayable());

        game.addPlayer("Mark");
        assertTrue(game.isPlayable());
    }

    @Test
    public void checkGameStateBeforeFirstRoll() {
        addPlayers();
        assertThat(game.getCurrentPlayer(), nullValue());
    }

    @Test
    public void askPopQuestions() {
        addPlayers("John", "Mark", "Bob", "Tom", "Andy");

        game.roll(0);
        assertThat(game.getCurrentPlayer().getPosition(), is(0));
        assertQuestionsAmounts(49, 50, 50, 50);

        game.roll(4);
        assertThat(game.getCurrentPlayer().getPosition(), is(4));
        assertQuestionsAmounts(48, 50, 50, 50);

        game.roll(8);
        assertThat(game.getCurrentPlayer().getPosition(), is(8));
        assertQuestionsAmounts(47, 50, 50, 50);
    }

    @Test
    public void askScienceQuestions() {
        addPlayers("John", "Mark", "Bob", "Tom", "Andy");

        game.roll(1);
        assertThat(game.getCurrentPlayer().getPosition(), is(1));
        assertQuestionsAmounts(50, 50, 49, 50);

        game.roll(5);
        assertThat(game.getCurrentPlayer().getPosition(), is(5));
        assertQuestionsAmounts(50, 50, 48, 50);

        game.roll(9);
        assertThat(game.getCurrentPlayer().getPosition(), is(9));
        assertQuestionsAmounts(50, 50, 47, 50);
    }

    @Test
    public void askSportsQuestions() {
        addPlayers("John", "Mark", "Bob", "Tom", "Andy");

        game.roll(2);
        assertThat(game.getCurrentPlayer().getPosition(), is(2));
        assertQuestionsAmounts(50, 49, 50, 50);

        game.roll(6);
        assertThat(game.getCurrentPlayer().getPosition(), is(6));
        assertQuestionsAmounts(50, 48, 50, 50);

        game.roll(10);
        assertThat(game.getCurrentPlayer().getPosition(), is(10));
        assertQuestionsAmounts(50, 47, 50, 50);
    }

    @Test
    public void askRockQuestions() {
        addPlayers("John", "Mark", "Bob", "Tom", "Andy");

        game.roll(3);
        assertThat(game.getCurrentPlayer().getPosition(), is(3));
        assertQuestionsAmounts(50, 50, 50, 49);

        game.roll(7);
        assertThat(game.getCurrentPlayer().getPosition(), is(7));
        assertQuestionsAmounts(50, 50, 50, 48);

        game.roll(11);
        assertThat(game.getCurrentPlayer().getPosition(), is(11));
        assertQuestionsAmounts(50, 50, 50, 47);
    }

    @Test
    public void rollOver11() {
        addPlayers("John", "Mark", "Bob", "Tom", "Andy");

        game.roll(13);
        assertThat(game.getCurrentPlayer().getPosition(), is(1));
        assertQuestionsAmounts(50, 50, 49, 50);
    }

    @Test
    public void rollEvenScoreWhenPlayerInPenaltyBox() {
        addPlayers("John");
        game.getPlayerById(0).ifPresent(Player::putInPenaltyBox);

        game.roll(2);

        assertThat(game.getCurrentPlayer().getPosition(), is(0));
        assertQuestionsAmounts(50, 50, 50, 50);
    }

    @Test
    public void rollOddScoreWhenPlayerInPenaltyBox() {
        addPlayers("John");
        game.getPlayerById(0).ifPresent(Player::putInPenaltyBox);

        game.roll(3);

        assertThat(game.getCurrentPlayer().getPosition(), is(3));
        assertQuestionsAmounts(50, 50, 50, 49);
    }

    @Test
    public void firstPlayerAnswersWrongly() {
        addPlayers("John", "Mark", "Bob", "Tom", "Andy");

        answerWrongly();

        assertTrue(game.getCurrentPlayer().isInPenaltyBox());
    }

    @Test
    public void allPlayerAnswersWrongly() {
        addPlayers("John", "Mark", "Bob");

        answerWrongly();
        assertTrue(game.getCurrentPlayer().isInPenaltyBox());

        answerWrongly();
        assertTrue(game.getCurrentPlayer().isInPenaltyBox());

        answerWrongly();
        assertTrue(game.getCurrentPlayer().isInPenaltyBox());
    }

    @Test
    public void firstPlayerAnswersCorrectly() {
        addPlayers("John", "Mark", "Bob", "Tom", "Andy");

        answerCorrectly();

        assertGameStateAfterCorrectAnswer(1);
    }

    @Test
    public void allPlayerAnswersCorrectly() {
        addPlayers("John", "Mark", "Bob");

        answerCorrectly();
        assertGameStateAfterCorrectAnswer(1);

        answerCorrectly();
        assertGameStateAfterCorrectAnswer(1);

        answerCorrectly();
        assertGameStateAfterCorrectAnswer(1);
    }

    @Test
    public void firstPlayerWinGame() {
        addPlayers("John", "Mark", "Bob");
        game.roll(0);
        Player player = game.getCurrentPlayer();
        player.addPoint();
        player.addPoint();
        player.addPoint();
        player.addPoint();
        player.addPoint();

        player.answerQuestion(new CorrectAnswer());
        boolean result = game.checkIfPlayerWonTheGame();

        assertTrue(result);
    }

    @Test
    public void playerInPenaltyBoxRollsOddScoreAndAnswersCorrectly() {
        addPlayers("John", "Mark", "Bob");
        game.getPlayerById(0).ifPresent(Player::putInPenaltyBox);

        if(game.roll(3)) {
            currentPlayerAnswers(new CorrectAnswer());
        }

        assertQuestionsAmounts(50, 50, 50, 49);
        assertGameStateAfterCorrectAnswerByPlayerInPenaltyBox(1);
    }

    @Test
    public void playerInPenaltyBoxRollsEvenScoreAndAnswersCorrectly() {
        addPlayers("John", "Mark", "Bob");
        game.getPlayerById(0).ifPresent(Player::putInPenaltyBox);

        if(game.roll(2)) {
            currentPlayerAnswers(new CorrectAnswer());
        }

        assertQuestionsAmounts(50, 50, 50, 50);
        assertGameStateAfterCorrectAnswerByPlayerInPenaltyBox(0);
    }

    @Test
    public void gameRunnerTest() {
        addPlayers("John", "Mark", "Bob", "Tom", "Andy");

        while (!isWinner) {
            if (game.roll(rand.nextInt(5) + 1)) {
                simulateQuestionAnswer(9, new int[]{7});
                isWinner = game.checkIfPlayerWonTheGame();
            }
        }

        assertThat(game.getCurrentPlayer().getId(), is(1));
        assertQuestionsAmounts(45, 42, 43, 47);
        assertPlayersPlaces(10, 8, 2, 4, 2);
        assertPlayersScores(2, 6, 2, 3, 5);
    }

    @Test
    public void simulateOnePersonGame() {
        addPlayers("John");

        while (!isWinner) {
            if (game.roll(rand.nextInt(4))) {
                simulateQuestionAnswer(12, new int[]{1, 3, 7});
                isWinner = game.checkIfPlayerWonTheGame();
            }
        }

        assertThat(game.getCurrentPlayer().getId(), is(0));
        assertQuestionsAmounts(48, 48, 49, 49);
        assertPlayersPlaces(0);
        assertPlayersScores(6);
    }

    @Test
    public void simulateThreePersonsGame() {
        addPlayers("John", "Mark", "Bob");

        while (!isWinner) {
            if(game.roll(rand.nextInt(27))) {
                simulateQuestionAnswer(13, new int[]{1, 3, 4, 5, 7, 11});
                isWinner = game.checkIfPlayerWonTheGame();
            }
        }

        assertThat(game.getCurrentPlayer().getId(), is(2));
        assertQuestionsAmounts(48, 46, 48, 37);
        assertPlayersPlaces(9, 12, 22);
        assertPlayersScores(1, 4, 6);
    }

    @Test
    public void simulateFivePersonsGame() {
        addPlayers("John", "Mark", "Bob", "Tom", "Andy");

        while (!isWinner) {
            if(game.roll(rand.nextInt(11))) {
                simulateQuestionAnswer(9, new int[]{2, 4, 5, 6, 7});
                isWinner = game.checkIfPlayerWonTheGame();
            }
        }

        assertThat(game.getCurrentPlayer().getId(), is(4));
        assertQuestionsAmounts(45, 42, 42, 42);
        assertPlayersPlaces(0, 9, 10, 11, 6);
        assertPlayersScores(4, 2, 2, 4, 6);
    }

    private void simulateQuestionAnswer(int bound, int[] failCondition) {
        Player player = game.getCurrentPlayer();
        if (IntStream.of(failCondition).anyMatch(x -> x == rand.nextInt(bound))) {
            player.answerQuestion(new WrongAnswer());
        } else {
            player.answerQuestion(new CorrectAnswer());
        }
    }

    private void assertQuestionsAmounts(int pop, int sports, int science, int rock) {
        assertThat(game.getAmountOfQuestionsInCategory(POP), is(pop));
        assertThat(game.getAmountOfQuestionsInCategory(SPORT), is(sports));
        assertThat(game.getAmountOfQuestionsInCategory(SCIENCE), is(science));
        assertThat(game.getAmountOfQuestionsInCategory(ROCK), is(rock));
    }

    private void assertGameStateAfterCorrectAnswer(int points) {
        assertThat(game.getCurrentPlayer().getScore(), is(points));
        assertFalse(game.getCurrentPlayer().isInPenaltyBox());
    }

    private void assertGameStateAfterCorrectAnswerByPlayerInPenaltyBox(int points) {
        assertThat(game.getCurrentPlayer().getScore(), is(points));
        assertTrue(game.getCurrentPlayer().isInPenaltyBox());
    }

    private void addPlayers(String... players) {
        for (String p : players) {
            game.addPlayer(p);
        }
    }

    private void assertPlayersScores(int... scores) {
        for (int i = 0; i < scores.length; i++) {
            assertThat(game.getPlayerById(i).get().getScore(), is(scores[i]));
        }
    }

    private void assertPlayersPlaces(int... scores) {
        for (int i = 0; i < scores.length; i++) {
            assertThat(game.getPlayerById(i).get().getPosition(), is(scores[i]));
        }
    }

    private void answerCorrectly() {
        if(game.roll(0)){
            currentPlayerAnswers(new CorrectAnswer());
        }
    }

    private void currentPlayerAnswers(Answer answer) {
        Player player = game.getCurrentPlayer();
        player.answerQuestion(answer);
    }

    private void answerWrongly() {
        if(game.roll(0)){
            currentPlayerAnswers(new WrongAnswer());
        }
    }

}
