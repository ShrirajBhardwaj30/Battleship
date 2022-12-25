package battleshipgame;

import battleship.BattleshipConsolePlayerBuilder;
import battleship.BattleshipConsolePlayer.BattleshipState;
import board.Arena;
import board.ArenaConfigurationException;
import game.GameBuildException;
import game.GameRunException;
import game.TwoPlayerGameCoordinator;
import player.ArenaPlayerBuilder;
import player.PlayerConfigurationException;
import player.PlayerInput;
import utils.StringConstant;


import java.util.Arrays;
import java.util.List;


public class BattleshipGame extends TwoPlayerGameCoordinator<BattleshipCoordinate, BattleshipState> {
    public static void main(String[] args) throws GameBuildException, GameRunException {
        BattleshipGame battleshipGame = new BattleshipGame();
        battleshipGame.setupGame();
        battleshipGame.startGame();
        battleshipGame.endGame();
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
}
