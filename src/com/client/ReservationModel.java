package com.client;

import com.adt.*;
import com.entity.*;

public class ReservationModel {

    private final ListInterface<Student> studentDirectory;

    private final QueueInterface<Reservation> reservations;
    private final QueueInterface<Room> smallRooms;
    private final QueueInterface<Room> mediumRooms;
    private final QueueInterface<Room> bigRooms;

    private final QueueInterface<Room> maintenanceRoom;

    public ReservationModel() {
        this.studentDirectory = new ArrayList<>();
        this.reservations = new DynamicArrayQueue<>();
        this.smallRooms = new DynamicArrayQueue<>();
        this.mediumRooms = new DynamicArrayQueue<>();
        this.bigRooms = new DynamicArrayQueue<>();
        this.maintenanceRoom = new DynamicArrayQueue<>();
        initData();
    }

    private void initData(){
        mediumRooms.enqueue(new Room("M0001","Normal Discussion Room 5",RoomType.MEDIUM));
        mediumRooms.enqueue(new Room("M0005","Normal Discussion Room 6",RoomType.MEDIUM));
        mediumRooms.enqueue(new Room("M0002","Normal Discussion Room 7",RoomType.MEDIUM));
        smallRooms.enqueue(new Room("M0003","Small Discussion Room 1",RoomType.SMALL));
        smallRooms.enqueue(new Room("M0006","Small Discussion Room 2",RoomType.SMALL));
        smallRooms.enqueue(new Room("M0008","Small Discussion Room 5",RoomType.SMALL));
        smallRooms.enqueue(new Room("M0009","Small-Medium Discussion Room 1",RoomType.SMALL));
        smallRooms.enqueue(new Room("M0010","Small-Medium Discussion Room 2",RoomType.SMALL));
        smallRooms.enqueue(new Room("M0012","Small-Medium Discussion Room 3",RoomType.SMALL));
        bigRooms.enqueue(new Room("M0004","Big-Medium Discussion Room 1",RoomType.LARGE));

        //Student Directory Init
        studentDirectory.add(new Student("987654", "kahpoh"));
        studentDirectory.add(new Student("220568", "John"));
        studentDirectory.add(new Student("778899", "Ahmad"));

        // Test a student reservation
        // Kahpoh will have a reservation on this account right now
        Room rm = smallRooms.dequeue();
        Reservation rs = new Reservation(rm,studentDirectory.getEntry(1),4,2);
        studentDirectory.getEntry(1).setCurrentReservation(rs);
        reservations.enqueue(rs);

    }

    /**
     * Add the room back into the queue
     * @param room - Room that is checkout by the student
     */
    public void updateRoomQueue(Room room){

        QueueInterface<Room> retrivalQueue = getRoomQueue(room.getType());
        retrivalQueue.enqueue(room);

    }

    /**
     * Get the suitable Room Queue based on the type
     * @param preferredType Type Of Room
     * @return Queue Of Rooms based on the type
     */
    public QueueInterface<Room> getRoomQueue(RoomType preferredType){

        switch(preferredType){
            case SMALL -> {
                return smallRooms;
            }
            case MEDIUM -> {
                return mediumRooms;
            }
            case LARGE -> {
                return bigRooms;
            }
        }

        return null;
    }

    /**
     * Search the Student by Name
     * @param studentName Name to Search
     * @return if found - Student Object
     *         if not found - null
     */
    public Student searchStudentByName(String studentName){
        for(int i = 1; i <= studentDirectory.getLength(); i++)
            if(studentName.equalsIgnoreCase(studentDirectory.getEntry(i).getName()))
                return studentDirectory.getEntry(i);

        return null;
    }

    /**
     * Search the Student by ID
      * @param studentID ID to Search
     * @return if found - Student Object
     *         if not found - null
     */
    public Student searchStudentByID(String studentID){

        for(int i = 1; i <= studentDirectory.getLength(); i++)
            if(studentID.equals(studentDirectory.getEntry(i).getId()))
                return studentDirectory.getEntry(i);

        return null;
    }

    /**
     * Register a new student to the student directory
     * @param newStudent Student Object to add into the directory
     */
    public void registerNewStudentToDirectory(Student newStudent){
        studentDirectory.add(newStudent);
    }

    /**
     * Core function to determine what room to give to student
     *      the method consist of
     *          -determine what room should it gives depends on the number of student
     *          -upsizing the room if the room of current size not available
     * @param numberOfStudent Number of students will be inside the room
     * @return Room
     */
    public Room retrieveRooms(int numberOfStudent){

        RoomType expectedType = decideSize(numberOfStudent);

        if(evaluateRoomAvailability(expectedType)){
           return getRoom(expectedType);
        }

        //Try to upsize if the room is not available

        if(expectedType == RoomType.LARGE) {
            return null;
        }
        else if(expectedType == RoomType.MEDIUM){

            if(evaluateRoomAvailability(RoomType.LARGE)) {
                return getRoom(RoomType.LARGE);
            }

           return null;
        }
        else{

            if(evaluateRoomAvailability(RoomType.MEDIUM)){
                return getRoom(RoomType.MEDIUM);
            }
            else if(evaluateRoomAvailability(RoomType.LARGE)){
                return getRoom(RoomType.LARGE);
            }

            return null;
        }

    }

    /**
     * Get Room from the queue and automatically dequeue the queue
     * @param type Type of Room
     * @return Room
     */
    private Room getRoom(RoomType type){
        if(type == RoomType.SMALL)
            return smallRooms.dequeue();
        else if(type == RoomType.MEDIUM)
            return mediumRooms.dequeue();
        else
            return bigRooms.dequeue();
    }

    /**
     * Decide the size of the room based on the range
     *          - (2 - 4) = Small
     *          - (5 - 10) = Medium
     *          - (11 - 20) = Large
     * @param numberOfStudent
     * @return RoomType - Type of the Room
     */
    private RoomType decideSize(int numberOfStudent){

        if(numberOfStudent < 5)
            return RoomType.SMALL;
        else if(numberOfStudent < 11)
            return RoomType.MEDIUM;
        else
            return RoomType.LARGE;

    }

    /**
     * Get the Room Range for each type of room
     * @param type Type of the Room
     * @return Integer[] array which means
     *              array[0] = Minimum Range
     *              array[1] = Maximum Range
     */
    public Integer[] getRoomRange(RoomType type){
        switch(type){
            case SMALL -> {
                return new Integer[]{2,4};
            }
            case MEDIUM -> {
                return new Integer[]{5,10};
            }
            case LARGE -> {
                return new Integer[]{11,20};
            }
        }
        return null;
    }

    /**
     * Evaluate the room is available to reserve
     * @param type Type of Room
     * @return True if is Available
     *          False if is not available
     */
    private boolean evaluateRoomAvailability(RoomType type){
        switch(type){
            case SMALL -> {
                return !smallRooms.isEmpty();
            }
            case MEDIUM -> {
                return !mediumRooms.isEmpty();
            }
            case LARGE -> {
                return !bigRooms.isEmpty();
            }
        }
        return false;
    }

    /**
     * Update the reservation Queue when there is a new reservation
     * @param newReservation The new reservation from the controller
     */
    public void addNewReservation(Reservation newReservation){
        reservations.enqueue(newReservation);
    }

    public ListInterface<Student> getStudentDirectory() {
        return studentDirectory;
    }

    public QueueInterface<Reservation> getReservations() {
        return reservations;
    }

    public QueueInterface<Room> getSmallRooms() {
        return smallRooms;
    }

    public QueueInterface<Room> getMediumRooms() {
        return mediumRooms;
    }

    public QueueInterface<Room> getBigRooms() {
        return bigRooms;
    }

    public QueueInterface<Room> getMaintenanceRoom() {
        return maintenanceRoom;
    }
}
