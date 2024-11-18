import java.util.*;
import java.sql.*;

class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/supercar_auction";
    private static final String USER = "root";
    private static final String PASSWORD = "dypsst123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

class Car {
    private int id;
    private String modelName;
    private int year;
    private double startingPrice;
    private String description;

    // Constructor,
    public Car(int id, String modelName, int year, double startingPrice, String description) {
        this.id = id;
        this.modelName = modelName;
        this.year = year;
        this.startingPrice = startingPrice;
        this.description = description;
    }

    public int getId() { 
        return id; 
    }
    public String getModelName() { 
        return modelName; 
    }
    public int getYear() { 
        return year; 
    }
    public double getStartingPrice() { 
        return startingPrice; 
    }
    public String getDescription() { 
        return description; 
    }

    
    @Override
    public String toString() {
        return "Car ID: " + id + ", Model: " + modelName + ", Year: " + year +
               ", Starting Price: $" + startingPrice + ", Description: " + description;
    }
}

class CarDAO {

    // Retrieve all cars
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Car car = new Car(
                    rs.getInt("id"),
                    rs.getString("model_name"),
                    rs.getInt("year"),
                    rs.getDouble("starting_price"),
                    rs.getString("description")
                );
                cars.add(car); //add every car info to arr
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    // Insert a new car
    public void insertCar(Car car) {
        String sql = "INSERT INTO cars (model_name, year, starting_price, description) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, car.getModelName());
            pstmt.setInt(2, car.getYear());
            pstmt.setDouble(3, car.getStartingPrice());
            pstmt.setString(4, car.getDescription());
            pstmt.executeUpdate();

            System.out.println("Car added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update a car’s details
    public void updateCar(int id, Car updatedCar) {
        String sql = "UPDATE cars SET model_name = ?, year = ?, starting_price = ?, description = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, updatedCar.getModelName());
            pstmt.setInt(2, updatedCar.getYear());
            pstmt.setDouble(3, updatedCar.getStartingPrice());
            pstmt.setString(4, updatedCar.getDescription());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();

            System.out.println("Car updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a car by ID
    public void deleteCar(int id) {
        String sql = "DELETE FROM cars WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            System.out.println("Car deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CarDAO carDAO = new CarDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Supercar Auction System ---");
            System.out.println("1. Display all cars");
            System.out.println("2. Add a new car");
            System.out.println("3. Update a car");
            System.out.println("4. Delete a car");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // Display all cars
                    List<Car> cars = carDAO.getAllCars();
                    cars.forEach(System.out::println);
                    break;
                case 2:
                    // Add a new car
                    System.out.print("Enter model name: ");
                    String modelName = scanner.nextLine();
                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();
                    System.out.print("Enter starting price: ");
                    double startingPrice = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();

                    Car newCar = new Car(0, modelName, year, startingPrice, description);
                    carDAO.insertCar(newCar);
                    break;
                case 3:
                    // Update a car
                    System.out.print("Enter ID of the car to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter new model name: ");
                    String newModelName = scanner.nextLine();
                    System.out.print("Enter new year: ");
                    int newYear = scanner.nextInt();
                    System.out.print("Enter new starting price: ");
                    double newStartingPrice = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter new description: ");
                    String newDescription = scanner.nextLine();

                    Car updatedCar = new Car(0, newModelName, newYear, newStartingPrice, newDescription);
                    carDAO.updateCar(updateId, updatedCar);
                    break;
                case 4:
                    // Delete a car
                    System.out.print("Enter ID of the car to delete: ");
                    int deleteId = scanner.nextInt();
                    carDAO.deleteCar(deleteId);
                    break;
                case 5:
                    // Exit
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
