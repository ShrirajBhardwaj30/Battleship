package battleship;

import board.Arena;

import board.InvalidArenaCoordinateException;
import game.GameOpponents;
import player.ArenaPlayer;
import player.InvalidPlayerInputException;
import player.PlayerInput;
import utils.StringConstant;


public class BattleshipConsolePlayer<I> implements ArenaPlayer<I> {
    public enum BattleshipState {
        SHIP_P,
        SHIP_Q,
        SHIP_DESTROYED,
        SHIP_NOT_PRESENT,
    }

    private String playerName;
    private Arena<I, BattleshipState> arena;
    private GameOpponents<I> opponents;

    @Override
    public void play(PlayerInput<I> input) throws InvalidPlayerInputException {
        I fire;
        while ((fire = input.getNext()) != null && this.opponents.getOpponent(fire).notify(fire)) {
            System.out.println(String.format(StringConstant.FIRES_TEMPLATE, this.playerName, fire.toString(), StringConstant.HIT));
        }

        if (fire != null && !this.opponents.getOpponent(fire).lost()) {
            System.out.println(String.format(StringConstant.FIRES_TEMPLATE, this.playerName, fire.toString(), StringConstant.MISS));
        }

        if (fire == null) {
            System.out.println(String.format(StringConstant.NO_INPUT_LEFT, this.playerName));
        }
    }

    @Override
    public boolean notify(I input) throws InvalidPlayerInputException {
        try {
            switch (arena.getItem(input).getValue()) {
                case SHIP_Q:
                    arena.updateItem(new BattleshipArenaItem<>(input, BattleshipState.SHIP_P));
                    return true;
                case SHIP_P:
                    arena.updateItem(new BattleshipArenaItem<>(input, BattleshipState.SHIP_DESTROYED));
                    return true;
                default:
                    return false;
            }
        } catch (InvalidArenaCoordinateException exception) {
            throw new InvalidPlayerInputException(exception);
        }
    }

    @Override
    public boolean lost() {
        return this.arena.checkAll((BattleshipState x) -> {
           return x == BattleshipState.SHIP_DESTROYED || x == BattleshipState.SHIP_NOT_PRESENT;
        });
    }

    @Override
    public String getName() {
        return this.playerName;
    }

    protected void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    protected void setArena(Arena<I, BattleshipState> arena) {
        this.arena = arena;
    }

    protected Arena<I, BattleshipState> getArena() {
        return this.arena;
    }

    protected void setOpponents(GameOpponents<I> opponents) {
        this.opponents = opponents;
    }

    public GameOpponents<I> getOpponents() {
        return this.opponents;
    }
}
