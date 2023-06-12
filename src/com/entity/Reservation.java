package com.entity;

public class Reservation {

    private String id;
    private Room reservedRoom;
    private Student reservedStudent;
    private int numberOfStudents;

    private int numberOfHours;
    private static int reservationCount = 0;

    public Reservation(Room reservedRoom, Student reservedStudent, int numberOfStudents, int numberOfHours) {
        this.id = autoIncrementID();
        this.reservedRoom = reservedRoom;
        this.reservedStudent = reservedStudent;
        this.numberOfStudents = numberOfStudents;
        this.numberOfHours = numberOfHours;
        reservationCount++;
    }

    private String autoIncrementID(){
       final String prefix = "R";

       return prefix + reservationCount;
    }

    public String getId() {
        return id;
    }

    public Room getReservedRoom() {
        return reservedRoom;
    }

    public Student getReservedStudent() {
        return reservedStudent;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
