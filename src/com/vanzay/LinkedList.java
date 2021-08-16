package com.vanzay;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedList<T> implements Iterable<T> {
    private ListItem<T> head;
    private ListItem<T> tail;
    private int size = 0;

    private static class ListItem<T> {
        T value;
        ListItem<T> next;
        ListItem<T> prev;

        ListItem(ListItem<T> next, T value, ListItem<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public ListItem(T value) {
            this.value = value;
        }

        public ListItem() {
        }
    }

    public void add(T elem) {
        ListItem<T> listItem = new ListItem<>(elem);
        if (head == null) {
            head = tail = listItem;
        } else {
            tail.next = listItem;
            listItem.prev = tail;
            tail = listItem;
        }
        size++;
    }

    public void add(int index, T element) {
        checkIndex(index);
        ListItem<T> newListItem = new ListItem<>(element);
        if (index == 0) {
            newListItem.next = head;
            head.prev = newListItem;
            head = newListItem;
            size++;
        } else if (index == size) {
            add(element);
        } else {
            ListItem<T> oldListItem = head;
            for (int i = 0; i < index; i++) {
                oldListItem = oldListItem.next;
            }
            ListItem<T> oldPrevious = oldListItem.prev;
            oldPrevious.next = newListItem;
            oldListItem.prev = newListItem;
            newListItem.prev = oldPrevious;
            newListItem.next = oldListItem;
            size++;
        }
    }

    public boolean addAll(Collection<? extends T> c) {
        return addAll(size, c);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndex(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0)
            return false;

        ListItem<T> pred, succ;
        if (index == size) {
            succ = null;
            pred = tail;
        } else {
            succ = findByIndex(index);
            pred = succ.prev;
        }

        for (Object o : a) {
            @SuppressWarnings("unchecked") T e = (T) o;
            ListItem<T> newNode = new ListItem<>(pred, e, null);
            if (pred == null)
                head = newNode;
            else
                pred.next = newNode;
            pred = newNode;
        }

        if (succ == null) {
            tail = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }

        size += numNew;
        return true;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            ListItem<T> cur = head;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public T next() {
                T value = cur.value;
                cur = cur.next;
                return value;
            }
        };
    }

    public Iterator<T> iteratorPrevious() {
        return new Iterator<>() {
            ListItem<T> cur = tail;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public T next() {
                T value = cur.value;
                cur = cur.prev;
                return value;
            }
        };
    }

    public int indexOf(T o) {
        int index = 0;
        if (o == null) {
            for (ListItem<T> x = head; x != null; x = x.next) {
                if (x.value == null)
                    return index;
                index++;
            }
        } else {
            for (ListItem<T> x = head; x != null; x = x.next) {
                if (o.equals(x.value))
                    return index;
                index++;
            }
        }
        return -1;
    }

    public T get(int index) {
        checkIndex(index);
        return findByIndex(index).value;
    }

    public T getFirst() {
        final ListItem<T> f = head;
        if (f == null)
            throw new NoSuchElementException();
        return f.value;
    }

    public T getLast() {
        final ListItem<T> f = tail;
        if (f == null)
            throw new NoSuchElementException();
        return f.value;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (get(i).equals(element)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void remove(int index) {
        checkIndex(index);
        if (index == 0) {
            head = head.next;
        } else {
            ListItem<T> listItem = findListItemBeforeByIndex(index);
            ListItem<T> tmp = findByIndex(index);
            tmp.next.prev = tmp.prev;
            Objects.requireNonNull(listItem).next = tmp.next;
        }
        size--;
    }

    public void removeElement(T element) {
        if (size == 0) {
            return;
        } else if (size == 1) {
            head = null;
            tail = null;
            size = 0;
            return;
        }

        ListItem<T> listItemBefore = findListItemBefore(element);

        if (listItemBefore != null) {
            if (tail.value.equals(element)) {
                listItemBefore.next = null;
                tail = listItemBefore;
            } else {
                ListItem<T> tmp = listItemBefore.next.next;
                listItemBefore.next = tmp;
                tmp.prev = listItemBefore;
            }
        }
        size--;
    }

    private ListItem<T> findByIndex(int index) {
        int tmpIndex = 0;
        if (head == null) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            return head;
        }

        ListItem<T> listItem = head;
        while (listItem.next != null) {
            listItem = listItem.next;
            tmpIndex++;
            if (tmpIndex == index) {
                return listItem;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    private ListItem<T> findListItemBefore(T value) {
        if (head.value.equals(value)) {
            return new ListItem<>();
        }

        ListItem<T> listItem = head;
        while (listItem.next != null) {
            if (listItem.next.value.equals(value)) {
                return listItem;
            }
            listItem = listItem.next;
        }
        return null;
    }

    private ListItem<T> findListItemBeforeByIndex(int index) {
        int count = 0;
        ListItem<T> listItem = head;
        while (listItem.next != null) {
            if (count == index - 1) {
                return listItem;
            }
            count++;
            listItem = listItem.next;
        }
        return null;
    }

    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (ListItem<T> x = head; x != null; x = x.next)
            result[i++] = x.value;
        return result;
    }

    public void set(int index, T element) {
        checkIndex(index);
        ListItem<T> x = findByIndex(index);
        x.value = element;
    }

    private void checkIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }
}
