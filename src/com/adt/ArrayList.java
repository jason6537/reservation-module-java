package com.adt;


import java.util.Arrays;

public class ArrayList<T> implements ListInterface<T> {

    private T[] list;
    private int numberOfEntries;
    private boolean integrityOK;
    private static final int MAX_CAPACITY = 10000;
    private static final int DEFAULT_CAPACITY = 25;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        integrityOK = false;

        if (capacity < DEFAULT_CAPACITY) capacity = DEFAULT_CAPACITY;
        else checkCapacity(capacity);

        T[] tempList = ((T[]) new Object[capacity]);
        list = tempList;
        numberOfEntries = 0;
        integrityOK = true;
    }

    private void checkCapacity(int capacity) {
        if (capacity > MAX_CAPACITY)
            throw new IllegalStateException("The List Object is corrupt");
    }

    private void checkIntegrity() {
        if (!integrityOK)
            throw new SecurityException("The List Object is corrupt.");
    }

    @Override
    public void add(T newEntry) {
        add(numberOfEntries + 1, newEntry);
    }

    @Override
    public void add(int givenPosition, T newEntry) {
        checkIntegrity();

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries + 1)) {
            if (givenPosition <= numberOfEntries)
                makeRoom(givenPosition);
            list[givenPosition] = newEntry;
            numberOfEntries++;
            ensureCapacity();
        } else throw new IndexOutOfBoundsException("" + "Illegal position given to remove operation");
    }

    //Utility Method
    private void makeRoom(int givenPosition) {

        for (int i = numberOfEntries; i >= givenPosition; i--) {
            list[i] = list[i - 1];
        }

    }

    private void removeGap(int givenPosition) {
        int removedIndex = givenPosition;
        for (int i = removedIndex; i < numberOfEntries; i++) {
            list[i + 1] = list[i];
        }
    }


    @Override
    public T remove(int givenPosition) {

        checkIntegrity();
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {

            T result = list[givenPosition];

            if (givenPosition < numberOfEntries)
                removeGap(givenPosition);
            list[numberOfEntries] = null;
            numberOfEntries--;
            return result;
        } else
            throw new IndexOutOfBoundsException("" + "Illegal position given to remove operation");

    }

    @Override
    public void clear() {
        for (int i = 1; i < numberOfEntries; i++) {
            list[i] = null;
        }
        numberOfEntries = 0;
    }

    @Override
    public T replace(int givenPosition, T newEntry) {

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            T old = list[givenPosition];
            list[givenPosition] = newEntry;
            return old;
        } else
            throw new IndexOutOfBoundsException("" + "Illegal position given to remove operation");

    }
    @Override
    public T getEntry(int givenPosition) {

        checkIntegrity();

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
            return list[givenPosition];
        else
            throw new IndexOutOfBoundsException("" + "Illegal position to get entry");


    }

    @SuppressWarnings("unchecked")
    @Override
    public T[] toArray() {
        checkIntegrity();

        T[] result = ((T[]) new Object[numberOfEntries]);
        for (int i = 0; i < numberOfEntries; i++) {
            result[i] = list[i];
        }

        return result;
    }

    @Override
    public boolean contains(T anEntry) {

        checkIntegrity();

        for (int i = 1; i < numberOfEntries; i++) {
            if (anEntry.equals(list[i])) return true;
        }
        return false;
    }

    @Override
    public int getLength() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    private void ensureCapacity() {
        int capacity = list.length - 1;
        if (numberOfEntries >= capacity) {
            int newCapacity = 2 * capacity;
            checkCapacity(newCapacity);
            list = Arrays.copyOf(list, newCapacity + 1);
        }
    }

}
