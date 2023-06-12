package com.entity;

import com.adt.ArrayList;
import com.adt.ListInterface;
import com.client.ReservationController;

public class Student {

    private String id;
    private String name;
    private ListInterface<Reservation> previousReservation;

    private Reservation currentReservation;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.previousReservation = new ArrayList<>();
        this.currentReservation = null;
    }

    public Reservation getCurrentReservation() {
        return currentReservation;
    }

    public void setCurrentReservation(Reservation currentReservation) {
        this.currentReservation = currentReservation;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ListInterface<Reservation> getPreviousReservation() {
        return previousReservation;
    }
}
