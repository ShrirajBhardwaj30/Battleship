package game;

/*
    Builds Game Opponent for each of the player in the game.
 */
public interface GameOpponentsBuilder<I> {
    GameOpponents<I> build();
}
