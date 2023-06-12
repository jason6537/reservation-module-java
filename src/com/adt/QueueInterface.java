package com.adt;

import java.util.Iterator;

/**
 *
 * @author KahPoh
 * @version 1.0
 * @param <T>
 *
 * Copyright © KahPoh 2022. All Right Reserved.
 */
public interface QueueInterface<T> {
    /**
     * Task: Adds newEntry to the end of the queue.
     * @param newEntry an object to be added
     */
    public void enqueue(T newEntry);

    /**
     * Task: Add multiple new entries that from newEntries to the end of the queue.
     * @param newEntries the multiple object to be added
     */
    public void enqueueAll(T[] newEntries);

    /**
     * Task: Remove and return the entry that at the front of the queue.
     * @return The front entry that was removed from the queue or null if the queue is empty.
     */
    public T dequeue();
    public int selection(String str);
    public String deleteCommand(String str);
    /**
     * Task: Retrieve and return the entry that at the front of the queue
     * @return The entry at the front of the queue or null if the queue is empty.
     */
    public T getFront();

    /**
     * Task: Retrieve and return the entry that at the rear of the queue
     * @return The entry at the rear of the queue or null if the queue is empty.
     */
    public T getRear();

    /**
     * Task: Replace the old entry to the new entry at the specific position
     * @param position of the entry to be replace.
     * @param newEntry that to replace the old entry.
     * @return true if found the entry in position, false if unable found the entry in position
     */
    public boolean replace(int position, T newEntry);

    /**
     * Task: Retrieve and return the entry that at the front of the queue
     * @param entry that wants to remove
     * @return The entry at the front of the queue or null if the queue is empty.
     */
    public boolean remove(T entry);

    /**
     * Task: Retrieve and return the entry that at the front of the queue
     * @param position of the entry that wants to remove
     * @return The entry at the front of the queue or null if the queue is empty.
     */
    public T removeAt(int position);

    /**
     * Task: Removes all entries from the queue.
     */
    public void clear();

    /**
     * Task: Determines whether the queue is empty
     * @return true if the queue is empty, false if the queue is not empty
     */
    public boolean isEmpty();

    /**
     * Task: Get the number of entries that stored in the queue.
     * @return The number of entries that stored in the queue.
     */
    public int count();

    /**
     * Task: Get the current position of the entry in the queue.
     * @param entry that used to get current position of the object in the queue
     * @return Current position of the entry in the queue.
     */
    public int getCurrentPosition(T entry);

    /**
     * Task: Determine whether entry exist in the queue.
     * @param entry that used to determine whether the entry exist in the queue
     * @return true if the entry exists in the queue, or false if the entry not exist in the queue.
     */
    public boolean contains(T entry);

    /**
     * Task: Convert the queue to the object array.
     * @return Array that converted from the queue.
     */
    public T[] toArray();

    /**
     * Task: Return the string that entries convert from the queue.
     * @return String that entries convert from the queue.
     */
    @Override
    public String toString();

    /**
     * Task: Return the iterator of the object for the queue
     * @return Iterator of the object for the queue
     */
    public Iterator<T> getIterator();
}
