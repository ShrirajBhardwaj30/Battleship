package player;


import board.InvalidArenaCoordinateException;

import java.util.function.Function;

/*
    Player is generic interface for mapped input I.
    It provides methods for controlling the player like given input I what should be the business logic for player.
    It provides communication medium to and from opponent.
*/
public interface ArenaPlayer<I> {
    /*
        Play: do the action of playing for example moving a piece or launching a missile etc.
        Given the opponent public exposed methods.
    */
    void play(PlayerInput<I> input) throws InvalidPlayerInputException;
    /*
        Notify: notify the input to the player to do post action and return boolean state if it affects a state.
    */
    boolean notify(I input) throws InvalidPlayerInputException;

    /*
        Lost: returns true if the player is lost already.
    */
    boolean lost();
    /*
        getName: returns the name of the player.
     */
    String getName();
}

