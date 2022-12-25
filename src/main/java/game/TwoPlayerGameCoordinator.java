package game;

import player.*;
import utils.StringConstant;

import java.util.Arrays;
import java.util.List;

public abstract class TwoPlayerGameCoordinator<I, V> implements GameCoordinator<I> {
    private List<ArenaPlayer<I>> players;
    private List<PlayerInput<I>> playersInputStreams;
    private Integer winner;
    private Integer turn;

    public abstract List<ArenaPlayerBuilder<I, V>> createPlayers() throws GameBuildException;

    public abstract List<PlayerInput<I>> createPlayerInputs();

    public void setupGame() throws GameBuildException {
        List<ArenaPlayerBuilder<I, V>> players = this.createPlayers();
        if (players.size() != 2) {
            throw new GameBuildException(StringConstant.INCORRECT_PLAYER_CONFIGURATION);
        }

        List<PlayerInput<I>> playerInputStreams = this.createPlayerInputs();
        if (playerInputStreams.size() != 2) {
            throw new GameBuildException(StringConstant.INCORRECT_PLAYER_INPUT_CONFIGURATION);
        }

        players.get(0).addOpponents((x) -> {return this.players.get(1);});
        players.get(1).addOpponents((x) -> {return this.players.get(0);});

        try {
            this.players = Arrays.asList(players.get(0).build(), players.get(1).build());
        } catch (PlayerConfigurationException e) {
            throw new GameBuildException(e);
        }

        this.playersInputStreams = playerInputStreams;
        this.turn = 0;
        this.winner = null;
    }

    public void startGame() throws GameRunException {
        try {
            while(winner == null) {
                players.get(this.turn).play(this.playersInputStreams.get(this.turn));
                /*
                    if the opponent has lost.
                 */
                if (players.get(this.turn ^ 1).lost()) {
                    winner = this.turn;
                    break;
                }
                /*
                    if no inputs from all the players.
                 */
                if (this.playersInputStreams.get(this.turn).isEmpty() && this.playersInputStreams.get(this.turn ^ 1).isEmpty()) {
                    winner = null;
                    break;
                }
                this.turn = this.turn ^ 1;
            }
        } catch (InvalidPlayerInputException e) {
            throw new GameRunException(e);
        }
    }

    public void endGame() {
        if (this.winner != null) {
            System.out.println(String.format(StringConstant.WON_BATTLE, this.players.get(winner).getName()));
            return;
        }
        System.out.println(StringConstant.DRAW_TEMPLATE);
    }
}
