package com.adt;

import java.util.Iterator;

/**
 *
 * @author Kah Poh
 * @version 1.0
 * @param <T>
 *
 * Copyright © KahPoh 2022. All Right Reserved.
 */
public class DynamicArrayQueue<T> implements QueueInterface<T>{
    private static final int DEFAULT_CAPACITY = 5;
    private T[] queue;
    private int frontIndex;
    private int backIndex;


    public DynamicArrayQueue(){
        this(DEFAULT_CAPACITY);
    }

    public DynamicArrayQueue(int initCapacity){
        queue = (T[]) new Object[initCapacity];
        backIndex = -1;
        frontIndex = 0;
    }

    /**
     * Task: Adds newEntry to the end of the queue.
     * @param newEntry an object to be added
     */
    @Override
    public void enqueue(T newEntry){
        if (isArrayFull()){
            if (((backIndex+1)-frontIndex)==queue.length){
                extendQueue();
            }else{
                shiftQueue();
            }
        }
        backIndex++;
        queue[backIndex] = newEntry;
    }

    /**
     * Task: Add multiple new entries that from newEntries to the end of the queue.
     * @param newEntries the multiple object to be added
     */
    @Override
    public void enqueueAll(T[] newEntries){
        for(T entry : newEntries){
            enqueue(entry);
        }
    }

    /**
     * Task: Remove and return the entry that at the front of the queue.
     * @return The front entry that was removed from the queue or null if the queue is empty.
     */
    @Override
    public T dequeue(){
        if (isEmpty()){
            return null;
        }else{
            T result = queue[frontIndex];
            frontIndex++;
            return result;
        }

    }

    /**
     * Task: Retrieve and return the entry that at the front of the queue
     * @return The entry at the front of the queue or null if the queue is empty.
     */
    @Override
    public T getFront(){
        if (!isEmpty()){
            return queue[frontIndex];
        }else{
            return null;
        }
    }

    /**
     * Task: Retrieve and return the entry that at the rear of the queue
     * @return The entry at the rear of the queue or null if the queue is empty.
     */
    @Override
    public T getRear(){
        if (!isEmpty()){
            return queue[backIndex];
        }else{
            return null;
        }
    }

    /**
     * Task: Replace the old entry to the new entry at the specific position
     * @param position of the entry to be replace.
     * @param newEntry that to replace the old entry.
     * @return true if found the entry in position, false if unable found the entry in position
     */
    @Override
    public boolean replace(int position, T newEntry){
        if (!isEmpty()){
            return false;
        }else{
            if (position<=0||position>count()){
                return false;
            }else{
                queue[position-1] = newEntry;
                return true;
            }
        }
    }

    /**
     * Task: Retrieve and return the entry that at the front of the queue
     * @param entry that wants to remove
     * @return The entry at the front of the queue or null if the queue is empty.
     */
    @Override
    public boolean remove(T entry){
        boolean exist = false;
        for(int index = frontIndex;index <= backIndex;index++){
            if (queue[index].equals(entry)){
                removeAt(index+1);
                exist = true;
                break;
            }
        }
        if (!exist){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Task: Retrieve and return the entry that at the front of the queue
     * @param position of the entry that wants to remove
     * @return The entry at the front of the queue or null if the queue is empty.
     */
    @Override
    public T removeAt(int position){
        if (position>count()||position<= 0){
            return null;
        }else{
            int index = position - 1;
            T entry = queue[index];
            for(int i = index;i < backIndex;i++){
                queue[i] = queue[i+1];
            }
            queue[backIndex] = null;
            backIndex -= 1;
            return entry;
        }
    }

    /**
     * Task: Removes all entries from the queue.
     */
    @Override
    public void clear(){
        queue = (T[]) new Object[queue.length];
        backIndex = -1;
        frontIndex = 0;
    }

    /**
     * Task: Determines whether the queue is empty
     * @return true if the queue is empty, false if the queue is not empty
     */
    @Override
    public boolean isEmpty(){
        return backIndex < frontIndex;
    }

    /**
     * Task: Get the number of entries that stored in the queue.
     * @return The number of entries that stored in the queue.
     */
    @Override
    public int count(){
        return (backIndex+1) - frontIndex;
    }

    /**
     * Task: Get the current position of the entry in the queue.
     * @param entry that used to get current position of the object in the queue
     * @return Current position of the entry in the queue.
     */
    @Override
    public int getCurrentPosition(T entry){
        for(int index = frontIndex; index<=backIndex;index++){
            if (queue[index].equals(entry)){
                return (index-frontIndex)+1;
            }
        }
        return -1;
    }

    /**
     * Task: Determine whether entry exist in the queue.
     * @param entry that used to determine whether the entry exist in the queue
     * @return true if the entry exists in the queue, or false if the entry not exist in the queue.
     */
    @Override
    public boolean contains(T entry){
        for(int index = frontIndex; index<=backIndex;index++){
            if (queue[index].equals(entry)){
                return true;
            }
        }
        return false;
    }

    /**
     * Task: Convert the queue to the object array.
     * @return Array that converted from the queue.
     */
    @Override
    public T[] toArray(){
        T[] returnQueue = (T[]) new Object[count()];
        for (int index = frontIndex;index<=backIndex;index++){
            returnQueue[index-frontIndex] = queue[index];
        }
        return returnQueue;
    }

    /**
     * Task: Return the string that entries convert from the queue.
     * @return String that entries convert from the queue.
     */
    @Override
    public String toString(){
        String str = "Queue{";
        for (int index = frontIndex;index<=backIndex;index++){
            str += queue[index];
            if (index!=backIndex){
                str+=", ";
            }
        }
        str+="}";
        return str;
    }

    /**
     * Task: Check is the array full
     * @return true if the array full, false if the array not full
     */
    private boolean isArrayFull(){
        return backIndex == (queue.length-1);
    }

    /**
     * Task: Extend the size of the array and transfer the entries from old array to new array
     */
    private void extendQueue(){
        int newCapacity = queue.length * 2;
        T[] newQueue = (T[]) new Object[newCapacity];
        int index;
        for (index = frontIndex;index<=backIndex;index++){
            newQueue[index-frontIndex] = queue[index];
        }
        queue = newQueue;
    }

    /**
     * Task: Fill up the empty space of the queue
     */
    private void shiftQueue(){
        int index;
        T[] tempQueue = (T[]) new Object[queue.length];
        for (index = frontIndex;index<=backIndex;index++){
            tempQueue[index-frontIndex] = queue[index];
        }
        queue = tempQueue;
        frontIndex = 0;
        backIndex = index;
    }
    public int Command(String lol){




        return 0;
    }

    public String deleteCommand(String str){
        if(str.matches("/create(.*)")){
            return str.replace("/create ","");
        }else if(str.matches("/check(.*)")){
            return str.replace("/check ","");
        }else if(str.matches("/remove(.*)")){
            return str.replace("/remove ","");
        }else if(str.matches("/list(.*)")){
            return str.replace("/list ","");
        }else if (str.matches("/next(.*)")){
            return str.replace("/next ","");
        }else if (str.matches("/back(.*)")){
            return str.replace("/back ","");
        } else if (str.matches("/join(.*)")){
            return str.replace("/join ","");
        } else if (str.matches("/customer(.*)")){
            return str.replace("/customer ","");
        }

        return str = "false";
    }

    public int selection(String str){
        if(str.matches("/create(.*)")){
            return 1;
        }else if(str.matches("/check(.*)")){
            return 2;
        }else if(str.matches("/remove(.*)")){
            return 3;
        }else if(str.matches("/list(.*)")){
            return 4;
        }else if (str.matches("/next(.*)")){
            return 5;
        }else if (str.matches("/back(.*)")){
            return 6;
        }else if (str.matches("/leave(.*)")){
            return 7;}
        else if (str.matches("/view(.*)")){
            return 8;}
        else if (str.matches("/endroom(.*)")){
            return 9;}
        else if (str.matches("/endroom(.*)")){
            return 9;}
        else if (str.matches("/join(.*)")){
            return 10;}
        else if (str.matches("/report(.*)")){
            return 11;}
        else if (str.matches("/room(.*)")){
            return 12;}
        else if (str.matches("/customer(.*)")){
            return 13;}


        return 0;
    }

    /**
     * Task: Return the iterator of the object for the queue
     * @return Iterator of the object for the queue
     */
    @Override
    public Iterator<T> getIterator(){
        return new DynamicArrayQueueIterator();
    }

    private class DynamicArrayQueueIterator implements Iterator<T>{
        private int nextIndex;

        private DynamicArrayQueueIterator(){
            nextIndex = frontIndex;
        }

        /**
         * Task: Check whether has next entry
         * @return true if have next entry else false
         */
        @Override
        public boolean hasNext(){
            return nextIndex <= backIndex;
        }

        /**
         * Task: Retrieve the next entry
         * @return return entry object if there is next entry else return null value if there is no next entry
         */
        @Override
        public T next(){
            if (hasNext()){
                T entry = queue[nextIndex++];
                return entry;
            }else{
                return null;
            }
        }

    }

}

