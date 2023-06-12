package com.client;

import com.adt.ListInterface;
import com.adt.QueueInterface;
import com.entity.Reservation;
import com.entity.Room;
import com.entity.Student;

import java.util.Iterator;

public class ReservationView {

    public ReservationView() {
    }

    public void displayReservationCommandsMenu(){
        System.out.println();
        System.out.println("==========[Reservation]==========");
        System.out.println("1. Create Reservation");
        System.out.println("2. Display Available Rooms");
        System.out.println("3. Edit Reservation");
        System.out.println("4. Check Out from Room");
        System.out.println("5. Generate Report Or View History");
        System.out.println("6. Search Reservation");
        System.out.println("7. Exit");
        System.out.println("==========[Reservation]==========");
        System.out.print("Please Enter Your Choice = ");
    }


    public void displayRoomTypeSelection(){
        System.out.println();
        System.out.println("==========[Room]==========");
        System.out.println("1. Small Discussion Room (2-4pax)");
        System.out.println("2. Medium Discussion Room (5-10pax)");
        System.out.println("3. Large Discussion room (11-20pax)");
        System.out.println("==========[Room]==========");
        System.out.print("Please Enter Your Choice = ");
    }

    public void displayAvailableRooms(QueueInterface<Room> room){
        System.out.println();

        Iterator<Room> it = room.getIterator();
        System.out.println();
        System.out.println("==========[Result]==========");

        System.out.printf("%-6s %-30s %-15s %-15s %-9s\n",
                "RoomID",
                "Room Name",
                "Room Type",
                "IsOnMaintenance",
                "Capacity"
        );

        System.out.println("=============================================================================");


        while(it.hasNext()){
            Room result = it.next();
            System.out.printf("%-6s %-30s %-15s %-15s (%d-%d)\n",
                    result.getId(),
                    result.getName(),
                    result.getType(),
                    result.isOnMaintenance() ? "Yes" : "No",
                    result.getMinCapacity(),
                    result.getMaxCapacity()
            );
        }
        System.out.println("==========[Result]==========");
    }

    public void displayReservationDetails(Reservation reservation){
        System.out.println();
        System.out.println("==========[Reservation Details]==========");
        System.out.printf("Reservation ID     : %s\n", reservation.getId());
        System.out.printf("Student Name       : %s\n", reservation.getReservedStudent().getName());
        System.out.printf("Number Of Student  : %s\n", reservation.getNumberOfStudents());
        System.out.println("=========================================");
        System.out.println(reservation.getReservedRoom());
        System.out.println("==========[Reservation Details]==========");
    }

    public void displayReportTypeMenu(){
        System.out.println();
        System.out.println("==========[Report]==========");
        System.out.println("1. Generate Summary Report");
        System.out.println("2. Generate Student Reservation History Report");
        System.out.println("3. Return");
        System.out.println("==========[Report]==========\n");
        System.out.print("Enter your choice = ");
    }

    public void generateSummaryReport(QueueInterface<Reservation> reservations){
        Iterator<Reservation> it = reservations.getIterator();
        int count = 0;
        System.out.println();
        System.out.println("==========[Summary Report]==========");
        System.out.printf("%-5s %-6s %-30s %-6s %s\n",
                "ID",
                "RoomID",
                "Room Name",
                "Room Type",
                "Pax"
        );
        while(it.hasNext()){
            Reservation rs = it.next();
            Room rm = rs.getReservedRoom();
            System.out.printf("%-5s %-6s %-30s %-6s %2d\n",
                    rs.getId(),
                    rm.getId(),
                    rm.getName(),
                    rm.getType(),
                    rs.getNumberOfStudents()
                    );
            count++;
        }
        System.out.println("==========[Summary Report]==========");
        System.out.printf("%-54s = %d\n", "Total Number Of Reservations Of The Day", count);


    }

    public void generateStudentPreviousHistoryReport(Student student){

        ListInterface<Reservation> prev = student.getPreviousReservation();
        System.out.println();
        System.out.println("=============[Student History]==============");
        System.out.printf("Student ID : %s\n", student.getId());
        System.out.printf("Student Name : %s\n", student.getName());
        System.out.println("=====================[Previous Reservations]===================");
        System.out.printf("%-5s %-6s %-30s %-15s %s\n",
                "ID",
                "RoomID",
                "Room Name",
                "Room Type",
                "Pax"
        );
        System.out.println("===============================================================");
        for (int i = 1; i <= prev.getLength(); i++) {
            Reservation rs = prev.getEntry(i);
            Room rm = rs.getReservedRoom();
            System.out.printf("%-5s %-6s %-30s %-15s %2d\n",
                    rs.getId(),
                    rm.getId(),
                    rm.getName(),
                    rm.getType(),
                    rs.getNumberOfStudents()
            );
        }
        System.out.println("=======================[Student History]=======================");

    }



}
