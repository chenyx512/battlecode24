package macrobot.fast;

public class FastIntSet {
    public StringBuilder keys;
    public int size;

    public FastIntSet() {
        keys = new StringBuilder();
        size = 0;
    }

    public int size() {
        return size;
    }

    public void add(int i) {
        String key = String.valueOf((char) i);
        if (keys.indexOf(key) < 0) {
            keys.append(key);
            size++;
        }
    }

    public void remove(int i) {
        String key = String.valueOf((char) i);
        int index;
        if ((index = keys.indexOf(key)) >= 0) {
            keys.deleteCharAt(index);
            size--;
        }
    }

    public boolean contains(int i) {
        return keys.indexOf(String.valueOf((char) i)) >= 0;
    }

    public void clear() {
        size = 0;
        keys = new StringBuilder();
    }
}
