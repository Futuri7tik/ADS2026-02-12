package by.it.group551002.brutski.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class MyTreeSet<E> implements Set<E> {
    private Object[] array = new Object[15];
    private int size = 0;

    private void reallocate(int min_size) {
        if (min_size > array.length) {
            int newCapacity = (int) (array.length * 1.5);
            Object[] newElements = new Object[newCapacity];
            System.arraycopy(array, 0, newElements, 0, size);
            array = newElements;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int binarySearch(Object o) {
        int l = 0, r = size - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            @SuppressWarnings("unchecked")
            Comparable<Object> midVal = (Comparable<Object>) array[mid];

            if (o == null) {
                if (array[mid] == null)
                    return mid;
                else
                    return -mid - 1;
            }

            int cmp = midVal.compareTo(o);
            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return -l - 1;
    }

    private void deleteAt(int i) {
        System.arraycopy(array, i + 1, array, i, size - i - 1);
        array[--size] = null;
    }

    @Override
    public boolean add(E e) {
        if (e == null)
            throw new NullPointerException("Added element is null");

        int ind = binarySearch(e);
        if (ind >= 0)
            return false;

        ind = -(ind + 1);
        reallocate(size + 1);
        if (ind < size)
            System.arraycopy(array, ind, array, ind + 1, size - ind);
        array[ind] = e;
        size++;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append('[');
        for (int i = 0; i < size; ++i) {
            if (i > 0) {
                str.append(", ");
            }
            str.append(array[i] == this ? "(this Collection)" : String.valueOf(array[i]));
        }
        str.append(']');
        return str.toString();
    }

    @Override
    public boolean remove(Object o) {
        int ind = binarySearch(o);
        if (ind < 0)
            return false;

        deleteAt(ind);
        return true;
    }

    @Override
    public boolean contains(Object o) {
        return binarySearch(o) >= 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o: c) {
            if (!contains(o))
                return false;
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;

        for (E o: c) {
            if (add(o))
                changed = true;
        }
        return changed;
    }

    private boolean keepOrDelete(Collection<?> c, boolean toKeep) {
        int write = 0;
        int originalSize = size;

        for (int read = 0; read < size; ++read) {
            if (c.contains(array[read]) == toKeep) {
                array[write++] = array[read];
            }
        }
        boolean changed = (write != originalSize);
        for (int j = write; j < size; ++j) {
            array[j] = null;
        }
        size = write;
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return keepOrDelete(c, true);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return keepOrDelete(c, false);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }
}
