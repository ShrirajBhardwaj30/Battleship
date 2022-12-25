package battleship;

import board.ArenaItem;

public class BattleshipArenaItem<T, V> implements ArenaItem<T , V> {
    private T coordinate;
    private V value;

    public BattleshipArenaItem(T coordinate, V value) {
        this.coordinate = coordinate;
        this.value = value;
    }

    @Override
    public T getCoordinates() {
        return this.coordinate;
    }

    @Override
    public V getValue() {
        return this.value;
    }
}
