package battleshipgame;

import battleship.BattleshipConsolePlayerBuilder;
import battleship.BattleshipConsolePlayer.BattleshipState;
import board.Arena;
import board.ArenaConfigurationException;
import game.GameBuildException;
import game.GameRunException;
import game.GenericGameCoordinator;
import player.ArenaPlayer;
import player.ArenaPlayerBuilder;
import player.PlayerConfigurationException;
import player.PlayerInput;
import utils.StringConstant;


import java.util.Arrays;
import java.util.List;


public class BattleshipGame extends GenericGameCoordinator<BattleshipCoordinate, BattleshipState> {
    public BattleshipGame(int nPlayer) {
        super(nPlayer);
    }

    private BattleshipConsolePlayerBuilder<BattleshipCoordinate> getPlayerBuilder(String playerName) {
        return BattleshipConsolePlayerBuilder.For(playerName);
    }

    @Override
    public List<ArenaPlayerBuilder<BattleshipCoordinate, BattleshipState>> createPlayers() throws GameBuildException {
        try {
            Arena<BattleshipCoordinate, BattleshipState> arena = BattleshipConsoleArenaBuilder.buildArena();
            BattleshipConsolePlayerBuilder<BattleshipCoordinate> player1 = getPlayerBuilder(StringConstant.P1)
                .addArena(arena.clone());
            BattleshipConsolePlayerBuilder<BattleshipCoordinate> player2 = getPlayerBuilder(StringConstant.P2)
                .addArena(arena.clone());
            BattleshipConsoleArenaBuilder.buildBattleships(player1, player2);
            return Arrays.asList(player1, player2);
        }
        catch (ArenaConfigurationException | PlayerConfigurationException e) {
            throw new GameBuildException(e);
        }
    }

    @Override
    public List<PlayerInput<BattleshipCoordinate>> createPlayerInputs() {
        BattleshipGamePlayerInput player1input = new BattleshipGamePlayerInput();
        BattleshipGamePlayerInput player2input = new BattleshipGamePlayerInput();
        BattleshipConsoleArenaBuilder.buildPlayerInputs(player1input, player2input);
        return Arrays.asList(player1input, player2input);
    }

    @Override
    protected void configureOpponents(List<ArenaPlayer<BattleshipCoordinate>> arenaPlayers,
                          List<ArenaPlayerBuilder<BattleshipCoordinate, BattleshipState>> arenaPlayerBuilders) {
        // configure opposite Opponents;
        for (int i = 0; i <= 1; i++) {
            int finalI = i;
            arenaPlayerBuilders.get(finalI).addOpponents(x -> arenaPlayers.get(finalI ^ 1));
        }
    }

    @Override
    protected Integer inWinner(List<ArenaPlayer<BattleshipCoordinate>> arenaPlayers,
                               List<PlayerInput<BattleshipCoordinate>> playerInputs) {
        for (int i = 0; i <= 1; i++) {
            if (!arenaPlayers.get(i).lost() && arenaPlayers.get(i ^ 1).lost()) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected Boolean isDraw(List<ArenaPlayer<BattleshipCoordinate>> arenaPlayers,
                             List<PlayerInput<BattleshipCoordinate>> playerInputs) {
        int firstPlayer = 0;
        return (arenaPlayers.get(firstPlayer).lost() && arenaPlayers.get(firstPlayer ^ 1).lost()) ||
                (playerInputs.get(firstPlayer).isEmpty() && playerInputs.get(firstPlayer ^ 1).isEmpty());
    }

    public static void main(String[] args) throws GameBuildException, GameRunException {
        BattleshipGame battleshipGame = new BattleshipGame(2);
        battleshipGame.setupGame();
        battleshipGame.startGame();
        battleshipGame.endGame();
    }
}
