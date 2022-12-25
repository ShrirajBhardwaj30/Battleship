package game;

import player.ArenaPlayer;

/*
    GameOpponent: provides methods to detect opponents in Game.
    Generic interface to decide based on user-input which opponent the player move is played against.
 */

public interface GameOpponents<I> {
    ArenaPlayer<I> getOpponent(I input);
}
