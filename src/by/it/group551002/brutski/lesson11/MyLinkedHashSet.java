package by.it.group551002.brutski.lesson11;

import java.util.*;

@SuppressWarnings("unchecked")
public class MyLinkedHashSet<E> implements Set<E> {
    private final int startCapacity = 25;
    private Node<E>[] table =  (Node<E>[]) new Node[startCapacity];
    private Node<E> headAdd, tailAdd;
    private int size = 0;

    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> nextAdd;
        Node<E> prevAdd;

        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = headAdd;
        boolean first = true;

        while (current != null) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(current.data);
            first = false;
            current = current.nextAdd;
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        int index = getIndex(e, table.length);

        for (Node<E> node = table[index]; node != null; node = node.next) {
            if (Objects.equals(node.data, e)) {
                return false;
            }
        }

        Node<E> newNode = new Node<>(e, table[index]);
        table[index] = newNode;

        if (tailAdd == null) {
            headAdd = tailAdd = newNode;
        } else {
            tailAdd.nextAdd = newNode;
            newNode.prevAdd = tailAdd;
            tailAdd = newNode;
        }

        size++;
        reallocate(size);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o, table.length);
        Node<E> prev = null;

        for (Node<E> node = table[index]; node != null; node = node.next) {
            if (Objects.equals(node.data, o)) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }

                if (node.prevAdd != null) {
                    node.prevAdd.nextAdd = node.nextAdd;
                } else {
                    headAdd = node.nextAdd;
                }

                if (node.nextAdd != null) {
                    node.nextAdd.prevAdd = node.prevAdd;
                } else {
                    tailAdd = node.prevAdd;
                }

                size--;
                return true;
            }
            prev = node;
        }
        return false;
    }

    private int getIndex(Object o, int length) {
        int h = (o == null) ? 0 : o.hashCode();
        return (h & 0x7FFFFFFF) % length;
    }

    private void reallocate(int min_size) {
        if (min_size > table.length * 4 / 5) {
            double k = 1.5;
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
        headAdd = tailAdd = null;
        size = 0;
    }

    @Override
    public boolean contains(Object o) {
        int index = getIndex(o, table.length);
        for (Node<E> node = table[index]; node != null; node = node.next) {
            if (Objects.equals(node.data, o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null)
            throw new NullPointerException();

        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null)
            throw new NullPointerException();

        boolean changed = false;
        for (E e : c) {
            if (add(e)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) throw new NullPointerException();
        boolean changed = false;

        List<E> toRemove = new ArrayList<>();
        for (Node<E> node = headAdd; node != null; node = node.nextAdd) {
            if (c.contains(node.data)) {
                toRemove.add(node.data);
            }
        }

        for (E e : toRemove) {
            if (remove(e)) {
                changed = true;
            }
        }

        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) throw new NullPointerException();
        boolean changed = false;

        List<E> toRemove = new ArrayList<>();
        for (Node<E> node = headAdd; node != null; node = node.nextAdd) {
            if (!c.contains(node.data)) {
                toRemove.add(node.data);
            }
        }

        for (E e : toRemove) {
            if (remove(e)) {
                changed = true;
            }
        }

        return changed;
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
