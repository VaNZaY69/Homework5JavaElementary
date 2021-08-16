package com.vanzay;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        LinkedList<Integer> integer = new LinkedList<>();
        integer.add(3);
        integer.add(5);
        integer.add(7);
        integer.add(9);
        integer.add(12);
        integer.add(2, 33);
        integer.add(0, 66);
        integer.add(integer.size(), 77);

        for (Integer value : integer) {
            System.out.print(value + " ");
        }

        System.out.println("size " + integer.size());

        integer.remove(3);

        System.out.println(integer.contains(88));

        System.out.println(integer.getFirst());
        System.out.println(integer.getLast());
        integer.removeElement(7);

        System.out.println(integer.get(1));

        Iterator<Integer> it = integer.iteratorPrevious();
        while (it.hasNext()){
            System.out.print(it.next() + " ");
        }
        System.out.println();
        for (Integer value : integer) {
            System.out.print(value + " ");
        }
        System.out.println("size " + integer.size());

        integer.clear();

        System.out.println("size " + integer.size());

        System.out.println(integer.isEmpty());

    }
}
