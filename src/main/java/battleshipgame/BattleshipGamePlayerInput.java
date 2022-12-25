package battleshipgame;

import player.PlayerInput;
import utils.Pair;

import java.util.LinkedList;
import java.util.List;
/*
    Concrete Class for Taking Inputs for Console Battleship Game.
 */
public class BattleshipGamePlayerInput implements PlayerInput<BattleshipCoordinate> {
    private List<BattleshipCoordinate> inputs;

    public BattleshipGamePlayerInput() {
        inputs = new LinkedList<>();
    }

    public void addInput(BattleshipCoordinate coordinate) {
        inputs.add(coordinate);
    }

    @Override
    public BattleshipCoordinate getNext() {
        if (inputs.size() == 0) {
            return null;
        }
        return inputs.remove(0);
    }

    public boolean isEmpty() {
        return this.inputs.isEmpty();
    }
}
