import java.sql.*;
import java.util.Scanner;

public class student {
   
    static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "root"; // change to your MySQL username
    static final String PASSWORD = "Password"; // change to your MySQL password

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Connect to Database
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            while (true) {
                System.out.println("\n===== Student Management System =====");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice;
                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("⚠ Invalid input! Please enter a number.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        addStudent(conn, sc);
                        break;
                    case 2:
                        viewStudents(conn);
                        break;
                    case 3:
                        updateStudent(conn, sc);
                        break;
                    case 4:
                        deleteStudent(conn, sc);
                        break;
                    case 5:
                        System.out.println("Exiting... Goodbye!");
                        conn.close();
                        sc.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("⚠ Invalid choice! Try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add Student
    public static void addStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = Integer.parseInt(sc.nextLine());
        System.out.print("Enter Grade: ");
        String grade = sc.nextLine();

        String sql = "INSERT INTO students (name, age, grade) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setInt(2, age);
        stmt.setString(3, grade);
        stmt.executeUpdate();
        System.out.println("✅ Student added successfully!");
    }

    // View Students
    public static void viewStudents(Connection conn) throws SQLException {
        String sql = "SELECT * FROM students";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("\n--- Student Records ---");
        System.out.printf("%-5s %-20s %-5s %-5s%n", "ID", "Name", "Age", "Grade");
        System.out.println("-------------------------------------");
        while (rs.next()) {
            System.out.printf("%-5d %-20s %-5d %-5s%n",
                              rs.getInt("id"),
                              rs.getString("name"),
                              rs.getInt("age"),
                              rs.getString("grade"));
        }
    }

    // Update Student
    public static void updateStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Student ID to update: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Enter New Name: ");
        String name = sc.nextLine();
        System.out.print("Enter New Age: ");
        int age = Integer.parseInt(sc.nextLine());
        System.out.print("Enter New Grade: ");
        String grade = sc.nextLine();

        String sql = "UPDATE students SET name=?, age=?, grade=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setInt(2, age);
        stmt.setString(3, grade);
        stmt.setInt(4, id);

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("✅ Student updated successfully!");
        } else {
            System.out.println("⚠ No student found with that ID.");
        }
    }

    // Delete Student
    public static void deleteStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Student ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "DELETE FROM students WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("✅ Student deleted successfully!");
        } else {
            System.out.println("⚠ No student found with that ID.");
        }
	}
}
    

