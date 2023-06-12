package com.client;

import com.entity.Reservation;
import com.entity.Room;
import com.entity.RoomType;
import com.entity.Student;

import java.util.Scanner;

public class ReservationController {

    private static ReservationController controller;
    private final ReservationModel model;
    private final ReservationView view;

    private final Scanner scanner;

    public ReservationController() {
        this.model = new ReservationModel();
        this.view = new ReservationView();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Core method to run
     */
    public void run(){
        boolean back = false;
        int choice;

        while(!back){

            view.displayReservationCommandsMenu();
            choice = scanner.nextInt();

            //Validate int
            while(choice < 1 || choice > 7)
            {
                System.out.println("Invalid Choice!!!");
                view.displayReservationCommandsMenu();
                choice = scanner.nextInt();
            }

            if(choice == 7)
                back = true;
            else
                executeChoice(choice);
        }

        System.out.println("Thank you for using the system");

    }

    /**
     * Run the methods based on the choice
     * @param choice
     */
    private void executeChoice(int choice){

        switch (choice) {
            case 1 -> makeReservation();
            case 2 -> displayAvailableRooms();
            case 3 -> editReservation();
            case 4 -> checkOutFromRoom();
            case 5 -> generateReport();
            case 6 -> searchStudent();
        }

    }

    /**
     * Edit the reservations
     *      - edit the number of pax
     */
    private void editReservation() {
        System.out.print("Enter The Student ID = ");
        String studentID = scanner.next();
        Student result;
        //Check is id
        result = model.searchStudentByID(studentID);
        if(result == null){
            System.out.println("No Such Student Found!");
            return;
        }

        if(result.getCurrentReservation() == null){
            System.out.println("The Student Does Not Have A Reservation Going On");
            return;
        }

        view.displayReservationDetails(result.getCurrentReservation());
        Integer[] editRange = model.getRoomRange(result.getCurrentReservation().getReservedRoom().getType());

        System.out.printf("New Number Of Students (%d - %d) = ", editRange[0], editRange[1]);
        int newPax = scanner.nextInt();

        while(newPax < editRange[0] || newPax > editRange[1]){
            System.out.println("Invalid Input!!!");
            System.out.printf("Please Enter Between the range of (%d - %d)!!!", editRange[0], editRange[1]);
            System.out.printf("New Number Of Students (%d - %d) = ", editRange[0], editRange[1]);
            newPax = scanner.nextInt();
        }

        result.getCurrentReservation().setNumberOfStudents(newPax);
        System.out.println("Successfully Update The Number Of Pax");
    }

    /**
     *  Validating the student id with regex of only digits allowed
     * @param studentID Student ID to validate
     * @return True if valid
     *          False if not valid
     */
    private boolean validateStudentID(String studentID){
        return studentID.matches("\\d+") && studentID.length() == 6;
    }

    /**
     * Generate Report
     *        - Summary Report -> Total Reservations of the Day
     *        - Student History Report -> All the history from the student
     */
    private void generateReport(){

        int choice;
        view.displayReportTypeMenu();
        choice = scanner.nextInt();

        while(choice < 1 || choice > 3){
            System.out.println("Invalid Input !!! Please Enter Between 1 - 3");
            view.displayReportTypeMenu();
            choice = scanner.nextInt();
        }

        if(choice == 3){
            return;
        }

        if(choice == 1){
            view.generateSummaryReport(model.getReservations());
        }
        else {

            System.out.print("Enter The Student ID = ");
            String studentID = scanner.next();
            Student result = model.searchStudentByID(studentID);

            if(result == null){
                System.out.println("No Such Student Found!!");
                return;
            }

            view.generateStudentPreviousHistoryReport(result);

        }

    }

    /**
     * Make Reservation for Student
     *          - If student directory has no such it would automatically register a new one
     *          - Automatically decide the room to student based on the number of pax
     *          - Automatically assign the Student the reservation
     *          - If detects student already have a reservation, if would stop
     */
    private void makeReservation(){
        System.out.println("[ Room Reservation Creation Mode ]");
        String studentID;
        int numberOfStudent;
        int numberOfHours;
        System.out.print("Enter Student ID = ");
        studentID = scanner.next();
        //Get student ID
        while(!validateStudentID(studentID)){
            System.out.println("Invalid Student ID!!!");
            System.out.println("Student ID must be only 6 digits");
            System.out.println("Enter Student ID = ");
            studentID = scanner.next();
        }

        Student currentStudent = model.searchStudentByID(studentID);

        if(currentStudent == null){
            scanner.nextLine();
            System.out.println("Welcome new Student!!!");
            System.out.print("Please enter your name = ");
            String name = scanner.nextLine();

            currentStudent = new Student(studentID, name);

            model.registerNewStudentToDirectory(currentStudent);

        }

        System.out.printf("Welcome %s\n", currentStudent.getName());

        if(currentStudent.getCurrentReservation() != null){
            System.out.println("One Student Can Only Make One Reservation!!!");
            return;
        }

        //if the student does not have a reservation

        System.out.print("Enter number of students = ");
        numberOfStudent = scanner.nextInt();

        while(numberOfStudent < 2 || numberOfStudent > 20)
        {
            System.out.println("Invalid Number!!!");
            System.out.println("Number of students allowed are (2 - 20)");
            System.out.println("Enter number of students = ");
            numberOfStudent = scanner.nextInt();
        }

        System.out.print("Enter number of hours = ");
        numberOfHours = scanner.nextInt();

        while(numberOfHours < 1 || numberOfHours > 2){
            System.out.println("Allowed Hours are only between 1 - 2 hours");
            System.out.print("Enter number of hours = ");
            numberOfHours = scanner.nextInt();
        }

        Room result = model.retrieveRooms(numberOfStudent);

        if(result == null){
            System.out.println("Sorry, currently there is no room available\nto be reserved currently, sorry for causing inconvenience");
            return;
        }

        Reservation reservation = new Reservation(result, currentStudent, numberOfStudent, numberOfHours);

        model.addNewReservation(reservation);
        currentStudent.setCurrentReservation(reservation);

        System.out.println("Successfully made the reservation!!!\n");
        view.displayReservationDetails(reservation);
    }

    /**
     * Display all the available rooms based on size
     */
    private void displayAvailableRooms(){

        view.displayRoomTypeSelection();

        int choice;
        choice = scanner.nextInt();

        while(choice < 1 || choice > 3){
            System.out.println("Invalid Choice!!!");
            view.displayRoomTypeSelection();
            choice = scanner.nextInt();
        }

        RoomType preferredType;

        if(choice == 1){
            preferredType = RoomType.SMALL;
        }
        else if(choice == 2){
            preferredType = RoomType.MEDIUM;
        }
        else {
            preferredType = RoomType.LARGE;
        }
        view.displayAvailableRooms(model.getRoomQueue(preferredType));

    }

    /**
     * Display student details
     */
    private void searchStudent(){

            System.out.print("Enter The Student ID or Name = ");
            String input = scanner.next();
            Student result;
            //Check is id
            if(validateStudentID(input))
            {
                result = model.searchStudentByID(input);
            }
            else{
                result = model.searchStudentByName(input);
            }

            if(result == null){
                System.out.println("No Such Student Found!");
                return;
            }

            if(result.getCurrentReservation() == null){
                System.out.println("The Student Does Not Have Reservation Going On!");
                return;
            }

        view.displayReservationDetails(result.getCurrentReservation());
    }
    /**
     * For Staff to check out the student from room
     */
    private void checkOutFromRoom(){

        System.out.print("Enter The Student ID = ");
        String studentID = scanner.next();
        Student result = model.searchStudentByID(studentID);

        if(result == null){
            System.out.println("No Such Student Found!!");
            return;
        }

        if(result.getCurrentReservation() == null){
            System.out.println("There Is No Reservation Going On With The Student!!");
            return;
        }

        view.displayReservationDetails(result.getCurrentReservation());

        char response;
        System.out.print("Are you sure to check out? (Y/N) = ");
        response = scanner.next().charAt(0);

        while(Character.toUpperCase(response) != 'Y' && Character.toUpperCase(response) != 'N'){

            System.out.println("Invalid Response !!! Please Enter Either (Y / N) ");
            System.out.print("Are you sure to check out? = ");
            response = scanner.next().charAt(0);

        }

        if(response == 'N'){
            System.out.println("Successfully discarded to checkout!!!");
            return;
        }

        Reservation reservation = result.getCurrentReservation();
        result.getPreviousReservation().add(reservation);
        result.setCurrentReservation(null);
        model.updateRoomQueue(reservation.getReservedRoom());

        System.out.println("Successfully Check Out!!");
    }

    public static ReservationController getInstance(){
        if(controller == null)
            controller = new ReservationController();

       return controller;
    }

    public static void main(String[] args) {
        ReservationController controller = ReservationController.getInstance();
        controller.run();
    }

}
