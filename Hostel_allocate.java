package P1;

import java.util.Scanner;
import java.io.*;

// -------- Student Classes --------
class Student2 {
    String name;
}

class Boy extends Student2 {
    int id;
    int type;
    int bed;
    boolean joined;
}

class Girl extends Student2 {
    int id;
    int type;
    int bed;
    boolean joined;
}

// -------- Main Class --------
public class Hostel_allocate {

    static Scanner sc = new Scanner(System.in);

    static Boy[] boy = new Boy[30];
    static Girl[] girl = new Girl[30];

    static int boyCount = 0;
    static int girlCount = 0;

    // 3 room types, 5 beds each
    static int[][] beds = new int[3][5];
    static int[][] gbeds = new int[3][5];

    public static void main(String[] args) {

        for (int i = 0; i < 30; i++) {
            boy[i] = new Boy();
            girl[i] = new Girl();
        }

        int choice;

        do {
            System.out.println("\n--- HOSTEL ALLOTMENT ---");
            System.out.println("1. Allocate Student");
            System.out.println("2. Disallocate Student");
            System.out.println("3. List Students");
            System.out.println("4. Occupancy Analytics");
            System.out.println("5. Search Student");
            System.out.println("6. Report");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1: join(); break;
                    case 2: discharge(); break;
                    case 3: list(); break;
                    case 4: analytics(); break;
                    case 5: search(); break;
                    case 6: report(); break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } while (choice != 7);

        System.out.println("Thank you!");
    }

    // -------- Allocate Student --------
    static void join() throws Exception {

        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        // check duplicate ID
        if (findBoy(id) != null || findGirl(id) != null)
            throw new Exception("ID Already Exists");

        System.out.print("Enter Gender (M/F): ");
        char gender = sc.next().charAt(0);

        if (!(gender == 'M' || gender == 'm' || gender == 'F' || gender == 'f'))
            throw new InvalidGenderException("Invalid Gender");

        System.out.print("Enter Student Name: ");
        sc.nextLine();
        String name = sc.nextLine();

        System.out.print("Select Room Type (1-3): ");
        int ward = sc.nextInt();

        if (ward < 1 || ward > 3)
            throw new InvalidRoomTypeException("Invalid Room Type");

        if (gender == 'M' || gender == 'm') {

            if (boyCount == 30)
                throw new HostelFullException("Boys Hostel Full");

            int bedFound = -1;
            for (int i = 0; i < 5; i++) {
                if (beds[ward - 1][i] == 0) {
                    bedFound = i;
                    break;
                }
            }

            if (bedFound == -1)
                throw new NoBedAvailableException("No Bed Available");

            Boy b = boy[boyCount];
            b.id = id;
            b.name = name;
            b.type = ward;
            b.bed = bedFound + 1;
            b.joined = true;

            beds[ward - 1][bedFound] = id;
            boyCount++;

            appendToFile(id, name, "M", "B" + ward, b.bed);

        } else {

            if (girlCount == 30)
                throw new HostelFullException("Girls Hostel Full");

            int bedFound = -1;
            for (int i = 0; i < 5; i++) {
                if (gbeds[ward - 1][i] == 0) {
                    bedFound = i;
                    break;
                }
            }

            if (bedFound == -1)
                throw new NoBedAvailableException("No Bed Available");

            Girl g = girl[girlCount];
            g.id = id;
            g.name = name;
            g.type = ward;
            g.bed = bedFound + 1;
            g.joined = true;

            gbeds[ward - 1][bedFound] = id;
            girlCount++;

            appendToFile(id, name, "F", "G" + ward, g.bed);
        }

        System.out.println("Student Allocated Successfully");
    }

    // -------- Disallocate Student (ONLY ID) --------
    static void discharge() throws Exception {

        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        Boy b = findBoy(id);
        if (b != null && b.joined) {
            beds[b.type - 1][b.bed - 1] = 0;
            b.joined = false;
            System.out.println("Student Disallocated");
            return;
        }

        Girl g = findGirl(id);
        if (g != null && g.joined) {
            gbeds[g.type - 1][g.bed - 1] = 0;
            g.joined = false;
            System.out.println("Student Disallocated");
            return;
        }

        throw new StudentNotFoundException("Student Not Found");
    }

    // -------- File Writing --------
    static void appendToFile(int id, String name, String gender, String room, int bed) {
        try {
            File file = new File("students.txt");
            boolean newFile = !file.exists();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

            if (newFile) {
                bw.write("ID | Name | Gender | Room | Bed");
                bw.newLine();
            }

            bw.write(id + " | " + name + " | " + gender + " | " + room + " | " + bed);
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            System.out.println("File Error");
        }
    }

    // -------- List Students --------
    static void list() {
        System.out.println("\nCURRENT STUDENTS");

        for (int i = 0; i < boyCount; i++)
            if (boy[i].joined)
                System.out.println("ID " + boy[i].id + " | " + boy[i].name +
                        " | B" + boy[i].type + " | Bed " + boy[i].bed);

        for (int i = 0; i < girlCount; i++)
            if (girl[i].joined)
                System.out.println("ID " + girl[i].id + " | " + girl[i].name +
                        " | G" + girl[i].type + " | Bed " + girl[i].bed);
    }

    // -------- Search --------
    static void search() throws Exception {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        Boy b = findBoy(id);
        if (b != null && b.joined) {
            System.out.println("Name: " + b.name + " Room: B" + b.type);
            return;
        }

        Girl g = findGirl(id);
        if (g != null && g.joined) {
            System.out.println("Name: " + g.name + " Room: G" + g.type);
            return;
        }

        throw new StudentNotFoundException("Student Not Found");
    }

    // -------- Analytics --------
    static void analytics() {
        int occupied = 0;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 5; j++) {
                if (beds[i][j] != 0) occupied++;
                if (gbeds[i][j] != 0) occupied++;
            }

        int totalBeds = 30;
        double percent = (occupied * 100.0) / totalBeds;

        System.out.println("Total Beds: " + totalBeds);
        System.out.println("Occupied Beds: " + occupied);
        System.out.println("Occupancy: " + percent + "%");
    }

    // -------- Report --------
    static void report() {
        System.out.println("\nHOSTEL REPORT");

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 5; j++) {

                if (beds[i][j] != 0) {
                    Boy b = findBoy(beds[i][j]);
                    System.out.println("B" + (i + 1) + " Bed " + (j + 1) +
                            " | " + b.id + " | " + b.name);
                }

                if (gbeds[i][j] != 0) {
                    Girl g = findGirl(gbeds[i][j]);
                    System.out.println("G" + (i + 1) + " Bed " + (j + 1) +
                            " | " + g.id + " | " + g.name);
                }
            }
    }

    // -------- Search Helpers --------
    static Boy findBoy(int id) {
        for (int i = 0; i < boyCount; i++)
            if (boy[i].id == id)
                return boy[i];
        return null;
    }

    static Girl findGirl(int id) {
        for (int i = 0; i < girlCount; i++)
            if (girl[i].id == id)
                return girl[i];
        return null;
    }
}

// -------- Custom Exceptions --------
class InvalidGenderException extends Exception {
    public InvalidGenderException(String msg) {
        super(msg);
    }
}

class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(String msg) {
        super(msg);
    }
}

class HostelFullException extends Exception {
    public HostelFullException(String msg) {
        super(msg);
    }
}

class NoBedAvailableException extends Exception {
    public NoBedAvailableException(String msg) {
        super(msg);
    }
}

class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String msg) {
        super(msg);
    }
}
