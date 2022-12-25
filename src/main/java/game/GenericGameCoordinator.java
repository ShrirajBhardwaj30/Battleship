package game;

import player.*;
import utils.StringConstant;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericGameCoordinator<I, V> implements GameCoordinator<I> {
    private List<ArenaPlayer<I>> players;
    private List<PlayerInput<I>> playersInputStreams;
    private Integer winner;
    private Integer turn;
    private Integer nPlayers;

    public GenericGameCoordinator(int nPlayers) {
        this.players = new ArrayList<>();
        this.nPlayers = nPlayers;
    }

    protected abstract List<ArenaPlayerBuilder<I, V>> createPlayers() throws GameBuildException;

    protected abstract List<PlayerInput<I>> createPlayerInputs();

    protected abstract void configureOpponents(List<ArenaPlayer<I>> players, List<ArenaPlayerBuilder<I, V>> playerBuilders);

    protected abstract Integer inWinner(List<ArenaPlayer<I>> players, List<PlayerInput<I>> playersInputStreams);

    protected abstract Boolean isDraw(List<ArenaPlayer<I>> players, List<PlayerInput<I>> playersInputStreams);

    public void setupGame() throws GameBuildException {
        List<ArenaPlayerBuilder<I, V>> players = this.createPlayers();
        if (players.size() != nPlayers) {
            throw new GameBuildException(StringConstant.INCORRECT_PLAYER_CONFIGURATION);
        }

        List<PlayerInput<I>> playerInputStreams = this.createPlayerInputs();
        if (playerInputStreams.size() != nPlayers) {
            throw new GameBuildException(StringConstant.INCORRECT_PLAYER_INPUT_CONFIGURATION);
        }

        this.configureOpponents(this.players, players);

        try {
            for (ArenaPlayerBuilder<I, V> player : players) {
                this.players.add(player.build());
            }
            this.playersInputStreams = playerInputStreams;
            this.turn = 0;
            this.winner = null;
        } catch (PlayerConfigurationException e) {
            throw new GameBuildException(e);
        }
    }

    public void startGame() throws GameRunException {
        try {
            while(winner == null) {
                // if this player is out of game move ahead.
                if (players.get(this.turn).lost()) {
                    this.turn = this.turn + 1 % nPlayers;
                    continue;
                }

                players.get(this.turn).play(this.playersInputStreams.get(this.turn));
                /*
                    check for winner.
                 */
                winner = this.inWinner(players, this.playersInputStreams);

                /*
                    check for draw.
                 */
                if (winner == null && this.isDraw(this.players, this.playersInputStreams)) {
                    break;
                }

                this.turn = (this.turn + 1) % this.nPlayers;
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

    protected Integer getnPlayers() {
        return nPlayers;
    }

    protected void setnPlayers(Integer nPlayers) {
        this.nPlayers = nPlayers;
    }
}
