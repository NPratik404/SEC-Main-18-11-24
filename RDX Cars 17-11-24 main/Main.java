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
    private int carId;
    private String carModelName;
    private int carModelYear;
    private String carLocation;
    private String carStartingPrice;

    // Constructor
    public Car(int carId, String carModelName, int carModelYear, String carLocation, String carStartingPrice) {
        this.carId = carId;
        this.carModelName = carModelName;
        this.carModelYear = carModelYear;
        this.carLocation = carLocation;
        this.carStartingPrice = carStartingPrice;
    }

    // Getters
    public int getCarId() {
        return carId;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public int getCarModelYear() {
        return carModelYear;
    }

    public String getCarLocation() {
        return carLocation;
    }

    public String getCarStartingPrice() {
        return carStartingPrice;
    }

    @Override
    public String toString() {
        return "Car ID: " + carId + ", Model: " + carModelName + ", Year: " + carModelYear +
               ", Location: " + carLocation + ", Starting Price: " + carStartingPrice;
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
                    rs.getInt("car_id"),
                    rs.getString("car_model_name"),
                    rs.getInt("car_model_year"),
                    rs.getString("car_location"),
                    rs.getString("car_starting_price")
                );
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    // Insert a new car
    public void insertCar(Car car) {
        String sql = "INSERT INTO cars (car_model_name, car_model_year, car_location, car_starting_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, car.getCarModelName());
            pstmt.setInt(2, car.getCarModelYear());
            pstmt.setString(3, car.getCarLocation());
            pstmt.setString(4, car.getCarStartingPrice());
            pstmt.executeUpdate();

            System.out.println("Car added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update a car’s details
    public void updateCar(int carId, Car updatedCar) {
        String sql = "UPDATE cars SET car_model_name = ?, car_model_year = ?, car_location = ?, car_starting_price = ? WHERE car_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, updatedCar.getCarModelName());
            pstmt.setInt(2, updatedCar.getCarModelYear());
            pstmt.setString(3, updatedCar.getCarLocation());
            pstmt.setString(4, updatedCar.getCarStartingPrice());
            pstmt.setInt(5, carId);
            pstmt.executeUpdate();

            System.out.println("Car updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a car by ID
    public void deleteCar(int carId) {
        String sql = "DELETE FROM cars WHERE car_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, carId);
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
                    System.out.print("Enter car model name: ");
                    String carModelName = scanner.nextLine();
                    System.out.print("Enter car model year: ");
                    int carModelYear = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter car location: ");
                    String carLocation = scanner.nextLine();
                    System.out.print("Enter car starting price: ");
                    String carStartingPrice = scanner.nextLine();

                    Car newCar = new Car(0, carModelName, carModelYear, carLocation, carStartingPrice);
                    carDAO.insertCar(newCar);
                    break;
                case 3:
                    // Update a car
                    System.out.print("Enter ID of the car to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter new car model name: ");
                    String newCarModelName = scanner.nextLine();
                    System.out.print("Enter new car model year: ");
                    int newCarModelYear = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter new car location: ");
                    String newCarLocation = scanner.nextLine();
                    System.out.print("Enter new car starting price: ");
                    String newCarStartingPrice = scanner.nextLine();

                    Car updatedCar = new Car(0, newCarModelName, newCarModelYear, newCarLocation, newCarStartingPrice);
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
