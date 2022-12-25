package battleshipgame;

import battleship.BattleshipArena;
import battleship.BattleshipArenaItem;
import battleship.BattleshipConsolePlayer;
import battleship.BattleshipConsolePlayerBuilder;
import board.Arena;
import board.ArenaConfigurationException;
import player.PlayerConfigurationException;
import utils.StringConstant;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/*
    BattleshipConsoleArenaBuilder: Responsible for reading inputs from standard CLI,
    and converting them to object of Battleship Game.
 */
public class BattleshipConsoleArenaBuilder {
    private static Scanner scanner = new Scanner(System.in);

    static Arena<BattleshipCoordinate, BattleshipConsolePlayer.BattleshipState> buildArena() throws ArenaConfigurationException {
        char yDimension = scanner.next().charAt(0);
        char xDimension = scanner.next().charAt(0);
        Arena<BattleshipCoordinate, BattleshipConsolePlayer.BattleshipState> arena = new BattleshipArena<>();
        for(int i = StringConstant.START_X; i <= yDimension; i++) {
            for (int j = StringConstant.START_Y; j <= xDimension; j++) {
                arena.addItem(new BattleshipArenaItem<>(new BattleshipCoordinate((char)j, (char)i),
                        BattleshipConsolePlayer.BattleshipState.SHIP_NOT_PRESENT));
            }
        }
        return arena;
    }

    static void buildBattleships(BattleshipConsolePlayerBuilder<BattleshipCoordinate> player1,
                                 BattleshipConsolePlayerBuilder<BattleshipCoordinate> player2) throws PlayerConfigurationException {
        int battleships = scanner.nextInt();
        for (int i = 0; i < battleships; i++) {
            BattleshipConsolePlayer.BattleshipState state = scanner.next().charAt(0) == StringConstant.SHIP_P ?
                    BattleshipConsolePlayer.BattleshipState.SHIP_P : BattleshipConsolePlayer.BattleshipState.SHIP_Q;
            int blength = scanner.nextInt();
            int bheight = scanner.nextInt();
            String player1Position = scanner.next();
            String player2Position = scanner.next();
            for(int len = 0; len < blength; len++) {
                for(int hei = 0; hei < bheight; hei++) {
                    player1.addArenaItems(Collections.singletonList(
                            new BattleshipArenaItem<>(
                                    new BattleshipCoordinate(
                                            (char) (player1Position.charAt(0) + hei),
                                            (char) (player1Position.charAt(1) + len)),
                                    state)));
                    player2.addArenaItems(Collections.singletonList(
                            new BattleshipArenaItem<>(
                                    new BattleshipCoordinate(
                                            (char) (player2Position.charAt(0) + hei),
                                            (char) (player2Position.charAt(1) + len)),
                                    state)));
                }
            }
        }
    }

    static void buildPlayerInputs(BattleshipGamePlayerInput player1, BattleshipGamePlayerInput player2) {
        scanner.nextLine();
        Arrays.stream(scanner.nextLine().split(" "))
                .forEach(x -> player1.addInput(new BattleshipCoordinate(x.charAt(0),
                x.charAt(1))));
        Arrays.stream(scanner.nextLine().split(" "))
                .forEach(x -> player2.addInput(new BattleshipCoordinate(x.charAt(0),
                        x.charAt(1))));
    }
}
