package battleshipgame;

import utils.Pair;

public class BattleshipCoordinate extends Pair<Character, Character> {
    public BattleshipCoordinate(Character first, Character second) {
        super(first, second);
    }

    @Override
    public String toString() {
        return  this.getFirst() + "" + this.getSecond() + "";
    }
}
