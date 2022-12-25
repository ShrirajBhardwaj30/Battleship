package player;

public interface PlayerInput<I> {
    I getNext();
    boolean isEmpty();
}