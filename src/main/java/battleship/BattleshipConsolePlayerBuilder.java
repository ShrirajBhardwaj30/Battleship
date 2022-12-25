package battleship;

import board.Arena;
import board.ArenaItem;
import board.InvalidArenaCoordinateException;
import game.GameOpponents;
import player.ArenaPlayer;
import player.ArenaPlayerBuilder;
import player.PlayerConfigurationException;

import java.util.List;

import static utils.StringConstant.PLAYER_BUILD_ARENA_MISSING;
import static utils.StringConstant.PLAYER_BUILD_OPPONENT_MISSING;

public class BattleshipConsolePlayerBuilder<I> extends BattleshipConsolePlayer<I>
        implements ArenaPlayerBuilder<I, BattleshipConsolePlayer.BattleshipState> {

    BattleshipConsolePlayerBuilder(String player) {
        this.setPlayerName(player);
        this.setArena(new BattleshipArena<>());
    }

    public static <I> BattleshipConsolePlayerBuilder<I> For(String player) {
        return new BattleshipConsolePlayerBuilder<I>(player);
    }

    @Override
    public ArenaPlayer<I> build() throws PlayerConfigurationException {
        if (this.getArena() == null) {
            throw new PlayerConfigurationException(PLAYER_BUILD_ARENA_MISSING);
        }
        if (this.getOpponents() == null) {
            throw new PlayerConfigurationException(PLAYER_BUILD_OPPONENT_MISSING);
        }
        return this;
    }

    @Override
    public BattleshipConsolePlayerBuilder<I> addArena(Arena<I, BattleshipState> arena) {
        this.setArena(arena);
        return this;
    }

    @Override
    public BattleshipConsolePlayerBuilder<I> addOpponents(GameOpponents<I> opponents) {
        this.setOpponents(opponents);
        return this;
    }

    @Override
    public BattleshipConsolePlayerBuilder<I> addArenaItems(List<ArenaItem<I, BattleshipConsolePlayer.BattleshipState>> initialState)
            throws PlayerConfigurationException {
        if (this.getArena() == null) {
            return this;
        }
        for (ArenaItem<I, BattleshipConsolePlayer.BattleshipState> item : initialState) {
            try {
                this.getArena().updateItem(item);
            } catch (InvalidArenaCoordinateException e) {
                throw new PlayerConfigurationException(e);
            }
        }
        return this;
    }



}
