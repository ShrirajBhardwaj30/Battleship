package game;

import player.InvalidPlayerInputException;

/*
    GameCoordinator is the coordinator of the game.
    makes sure that game is going smoothly managing turn,
    giving each player instance chance to play on their turn.
 */
public interface GameCoordinator<I> {
    /*
        setupGame: Coordinator creates the instances for players, objects needed to start the game.
     */
    void setupGame() throws GameBuildException, GameRunException;

    /*
        startGame: Coordinator starts the game with one of player.
     */
    void startGame() throws InvalidPlayerInputException, GameRunException;

    /*
        endGame: Coordinator ends the game with declaring winner at the end.
     */
    void endGame();
}
