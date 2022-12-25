package battleship;

import board.Arena;
import board.ArenaConfigurationException;
import board.ArenaItem;
import board.InvalidArenaCoordinateException;
import utils.StringConstant;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BattleshipArena<T, V> implements Arena<T, V> {
    private Map<T, ArenaItem<T, V>> boardMap;

    public BattleshipArena() {
        this.boardMap = new HashMap<>();
    }

    @Override
    public void addItem(ArenaItem<T, V> item) throws ArenaConfigurationException {
        if (this.boardMap.containsKey(item.getCoordinates())) {
            throw new ArenaConfigurationException(StringConstant.ARENA_DUPLICATE_COORDINATE);
        }
        this.boardMap.put(item.getCoordinates(), item);
    }

    @Override
    public void removeItem(ArenaItem<T, V> item) throws InvalidArenaCoordinateException {
        if (this.boardMap.remove(item.getCoordinates()) == null) {
            throw new InvalidArenaCoordinateException(item.getCoordinates().toString());
        }
    }

    @Override
    public ArenaItem<T, V> getItem(T item) throws InvalidArenaCoordinateException {
        ArenaItem<T, V> value = this.boardMap.get(item);
        if (value == null) {
            throw new InvalidArenaCoordinateException(item.toString());
        }
        return value;
    }

    @Override
    public V updateItem(ArenaItem<T, V> item) throws InvalidArenaCoordinateException {
        if (item == null) {
            throw new InvalidArenaCoordinateException(null);
        }
        if (this.boardMap.get(item.getCoordinates()) == null) {
            throw new InvalidArenaCoordinateException(item.getCoordinates().toString());
        }
        
        return this.boardMap.put(item.getCoordinates(), item).getValue();
    }

    @Override
    public boolean checkAll(Function<V, Boolean> predicate) {
        return this.boardMap.values().stream().map(ArenaItem::getValue).allMatch(predicate::apply);
    }

    @Override
    public Arena<T, V> clone() {
        Arena<T, V> arena = new BattleshipArena<T, V>();
        for (Map.Entry<T, ArenaItem<T, V>> entry : this.boardMap.entrySet()) {
            ArenaItem<T, V> value = entry.getValue();
            try {
                arena.addItem(value);
            } catch (ArenaConfigurationException e) {
                // ignorable.
            }
        }
        return arena;
    }
}
