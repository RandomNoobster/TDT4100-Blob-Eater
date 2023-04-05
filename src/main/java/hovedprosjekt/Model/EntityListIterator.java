package hovedprosjekt.Model;

import java.util.Iterator;
import java.util.List;

public class EntityListIterator implements Iterator<Entity> {
    private List<Entity> entities;
    private int index;

    /**
     * Creates a new {@link EntityListIterator} object.
     */
    public EntityListIterator(List<Entity> entities) {
        this.entities = entities;
        this.index = entities.size();
    }

    /**
     * @return the next entity in the list
     */
    public Entity next() {
        index--;
        return entities.get(index);
    }

    /**
     * @return true if there is a next entity in the list, false otherwise
     */
    public boolean hasNext() {
        try {
            entities.get(index - 1);
            return true;
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }
}
