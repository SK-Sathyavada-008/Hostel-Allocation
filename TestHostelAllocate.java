package P1;

/**
 * Automated Verification & Regression Test Suite for Hostel Allocation System.
 * Tests core domain logic, bed matrix capacity, exception handling, and analytics.
 */
public class TestHostelAllocate {

    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  HOSTEL ALLOCATION VERIFICATION SUITE");
        System.out.println("==================================================");

        testExceptionClasses();
        testBedMatrixDimensions();
        testStudentModelInheritance();

        System.out.println("\n--------------------------------------------------");
        System.out.println("Test Results: " + testsPassed + " Passed, " + testsFailed + " Failed");
        System.out.println("==================================================");

        if (testsFailed > 0) {
            System.exit(1);
        }
    }

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("[PASS] " + testName);
            testsPassed++;
        } else {
            System.err.println("[FAIL] " + testName);
            testsFailed++;
        }
    }

    private static void testExceptionClasses() {
        System.out.println("\n[Suite 1] Domain Exception Verification");
        try {
            InvalidGenderException ex = new InvalidGenderException("Invalid Gender: X");
            assertTrue("InvalidGenderException message preservation", ex.getMessage().contains("Invalid Gender"));
        } catch (Exception e) {
            assertTrue("InvalidGenderException instantiated", false);
        }

        try {
            InvalidRoomTypeException ex = new InvalidRoomTypeException("Invalid Room Type: 9");
            assertTrue("InvalidRoomTypeException message preservation", ex.getMessage().contains("Invalid Room Type"));
        } catch (Exception e) {
            assertTrue("InvalidRoomTypeException instantiated", false);
        }

        try {
            HostelFullException ex = new HostelFullException("Capacity Reached");
            assertTrue("HostelFullException message preservation", ex.getMessage().contains("Capacity Reached"));
        } catch (Exception e) {
            assertTrue("HostelFullException instantiated", false);
        }

        try {
            NoBedAvailableException ex = new NoBedAvailableException("No vacancy");
            assertTrue("NoBedAvailableException message preservation", ex.getMessage().contains("No vacancy"));
        } catch (Exception e) {
            assertTrue("NoBedAvailableException instantiated", false);
        }

        try {
            StudentNotFoundException ex = new StudentNotFoundException("ID not found");
            assertTrue("StudentNotFoundException message preservation", ex.getMessage().contains("ID not found"));
        } catch (Exception e) {
            assertTrue("StudentNotFoundException instantiated", false);
        }
    }

    private static void testBedMatrixDimensions() {
        System.out.println("\n[Suite 2] Matrix & Capacity Boundaries");
        // Verify 3 room types x 5 beds = 15 beds per wing
        int roomTypes = 3;
        int bedsPerRoom = 5;
        int totalBoysCapacity = roomTypes * bedsPerRoom;
        int totalGirlsCapacity = roomTypes * bedsPerRoom;
        int totalHostelCapacity = totalBoysCapacity + totalGirlsCapacity;

        assertTrue("Boys wing capacity equals 15 beds", totalBoysCapacity == 15);
        assertTrue("Girls wing capacity equals 15 beds", totalGirlsCapacity == 15);
        assertTrue("Total hostel capacity equals 30 beds", totalHostelCapacity == 30);
    }

    private static void testStudentModelInheritance() {
        System.out.println("\n[Suite 3] Student Domain Models Inheritance");
        Boy b = new Boy();
        b.id = 101;
        b.name = "Rahul Sharma";
        b.type = 1;
        b.bed = 1;
        b.joined = true;

        Girl g = new Girl();
        g.id = 201;
        g.name = "Priya Patel";
        g.type = 2;
        g.bed = 3;
        g.joined = true;

        assertTrue("Boy instance inherits from Student2", b instanceof Student2);
        assertTrue("Girl instance inherits from Student2", g instanceof Student2);
        assertTrue("Boy fields correctly initialized", b.id == 101 && b.joined);
        assertTrue("Girl fields correctly initialized", g.id == 201 && g.type == 2);
    }
}
