package by.it.group551002.brutski.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@SuppressWarnings("unchecked")
public class MyHashSet<E> implements Set<E> {
    int startCapacity = 25;
    private Node<E>[] table = (Node<E>[]) new Node[startCapacity];
    int size = 0;
    double k = 1.5;

    public static class Node<E> {
        E data;
        Node<E> next;

        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private void reallocate(int min_size) {
        if (min_size > table.length * 4 / 5) {
            int newCapacity = (int) (table.length * k);
            Node<E>[] newElements = (Node<E>[]) new Node[newCapacity];

            for (Node<E> bucket : table) {
                Node<E> current = bucket;
                while (current != null) {
                    Node<E> next = current.next;

                    int newIndex = getIndex(current.data, newCapacity);
                    current.next = newElements[newIndex];
                    newElements[newIndex] = current;

                    current = next;
                }
            }
            table = newElements;
        }
    }

    private int getIndex(Object o, int length) {
        int h = (o == null) ? 0 : o.hashCode();
        return (h & 0x7FFFFFFF) % length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        table = (Node<E>[]) new Node[startCapacity];
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder resStr = new StringBuilder("[");

        boolean firstItem = true;
        for (Node<E> bucket : table) {
            for (Node<E> node = bucket; node != null; node = node.next) {
                if (!firstItem) {
                    resStr.append(", ");
                }
                resStr.append(node.data);
                firstItem = false;
            }
        }
        resStr.append("]");
        return resStr.toString();
    }

    @Override
    public boolean add(E e) {
        int index = getIndex(e, table.length);
        for (Node<E> node = table[index]; node != null; node = node.next) {
            if (java.util.Objects.equals(e, node.data)) {
                return false;
            }
        }
        table[index] = new Node<>(e, table[index]);
        ++size;
        reallocate(size);

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o, table.length);
        Node<E> prev = null;
        for (Node<E> node = table[index]; node != null; node = node.next) {
            if (java.util.Objects.equals(o, node.data))  {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return true;
            }
            prev = node;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        int index = getIndex(o, table.length);
        for (Node<E> node = table[index]; node != null; node = node.next) {
            if (java.util.Objects.equals(o, node.data)) {
                return true;
            }
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////

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

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }
}
