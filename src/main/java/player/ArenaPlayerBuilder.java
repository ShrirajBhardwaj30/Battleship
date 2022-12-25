package player;


/*
    Builds Arena Player of ArenaItem<I, V>
    ArenaPlayer

*/

import board.Arena;
import board.ArenaItem;
import game.GameOpponents;

import java.util.List;

public interface ArenaPlayerBuilder<I, V> {
    /*
        returns the built object for the ArenaPlayer.
     */
    ArenaPlayer<I> build() throws PlayerConfigurationException;
    /*
        setup the player arena for this to be setup.
     */
    ArenaPlayerBuilder<I, V> addArena(Arena<I, V> arena);
    /*
        setup the Player state based on List ArenaItems Ordered.
     */
    ArenaPlayerBuilder<I, V> addArenaItems(List<ArenaItem<I, V>> initialState) throws PlayerConfigurationException;
    /*
        add one or more opponents;
     */
    ArenaPlayerBuilder<I, V> addOpponents(GameOpponents<I> opponents);
}
