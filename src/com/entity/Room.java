package com.entity;

public class Room {

    private String id;
    private String name;
    private RoomType type;
    private boolean isOnMaintenance;

    private int minCapacity;
    private int maxCapacity;

    public Room(String id, String name, RoomType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isOnMaintenance = false;
        capacityAssign();
    }

    private void capacityAssign(){
        switch(type){
            case SMALL -> {
                minCapacity = 2;
                maxCapacity = 4;
            }
            case MEDIUM -> {
                minCapacity = 5;
                maxCapacity = 10;
            }
            case LARGE -> {
                minCapacity = 11;
                maxCapacity = 20;
            }
        }
    }

    public int getMinCapacity() {
        return minCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomType getType() {
        return type;
    }

    public boolean isOnMaintenance() {
        return isOnMaintenance;
    }

    public void setOnMaintenance(boolean onMaintenance) {
        isOnMaintenance = onMaintenance;
    }
    @Override
    public String toString() {
        return String.format("Room ID : %s\nRoom Name : %s\nType : %s\nIsOnMaintenance : %s",
                id,name,type.toString(),isOnMaintenance ? "Yes" : "No");
    }
}
