package board;

import java.util.function.Function;

/*
    Arena is a board of elements of type T.
 */
public interface Arena <T, V> {
    /*
        AddItem: adds an item to arena of type T.
     */
    void addItem(ArenaItem<T, V> item) throws ArenaConfigurationException;
    /*
        removeItem: remove an item from arena of type T.
    */
    void removeItem(ArenaItem<T, V> item) throws InvalidArenaCoordinateException;
    /*
        getItem: gets an item from the given position
        throws error if element not found.
     */
    ArenaItem<T, V> getItem(T item) throws InvalidArenaCoordinateException;

    /*
        updateItem: updateItem to a specific value at the position.
        doesn't add new item.
     */
    V updateItem(ArenaItem<T, V> item) throws InvalidArenaCoordinateException;

    /*
        checkAll: returns true if all items are of same value;
     */
    boolean checkAll(Function<V, Boolean> condition);

    /*
        deep-clone the items of Arena.
     */
    Arena<T, V> clone();
}
