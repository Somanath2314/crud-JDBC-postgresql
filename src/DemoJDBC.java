import java.sql.*;

public class DemoJDBC {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Database connection details
        String url = "jdbc:postgresql://localhost:5432/DemoJDBC"; // JDBC URL of the database
        String user = "postgres"; // Database username
        String pass = "****"; // Database password

        // Step 1: Load and register the PostgreSQL JDBC driver
        Class.forName("org.postgresql.Driver");

        // Step 2: Establish a connection to the database
        Connection con = DriverManager.getConnection(url, user, pass);
        System.out.println("Connected successfully");

        // Step 3: PreparedStatement for inserting records dynamically
        // This avoids the need for string concatenation and helps prevent SQL injection
        String insertSql = "INSERT INTO employee VALUES (?, ?)";
        PreparedStatement st1 = con.prepareStatement(insertSql);

        // Example data to insert
        st1.setInt(1, 1000); // Setting the first placeholder (column 1)
        st1.setString(2, "Shreya"); // Setting the second placeholder (column 2)
        st1.execute(); // Execute the prepared statement
        System.out.println("Record inserted successfully");

        // Step 4: Reading and displaying records from the "employee" table
        // Creating a simple Statement object for executing queries
        String selectQuery = "SELECT * FROM employee";
        Statement st2 = con.createStatement();
        ResultSet res = st2.executeQuery(selectQuery); // Execute the SELECT query

        // Retrieve metadata to automate fetching column names and values
        ResultSetMetaData metaData = res.getMetaData();
        int columnCount = metaData.getColumnCount(); // Get the number of columns

        System.out.println("Displaying all records:");
        while (res.next()) { // Iterate through each row
            for (int i = 1; i <= columnCount; i++) { // Iterate through each column
                String columnName = metaData.getColumnName(i); // Get column name
                String columnValue = res.getString(i); // Get column value
                System.out.print(columnName + " = " + columnValue + "; ");
            }
            System.out.println(); // New line for each row
        }

        // Step 5: Updating a record in the "employee" table
        String updateQuery = "UPDATE employee SET name = ? WHERE id = ?";
        PreparedStatement st3 = con.prepareStatement(updateQuery);
        st3.setString(1, "Updated Name"); // New name
        st3.setInt(2, 1000); // ID of the record to update
        st3.execute();
        System.out.println("Record updated successfully");

        // Step 6: Deleting a record from the "employee" table
        String deleteQuery = "DELETE FROM employee WHERE id = ?";
        PreparedStatement st4 = con.prepareStatement(deleteQuery);
        st4.setInt(1, 1000); // ID of the record to delete
        //1 means first ?, 2 means 2nd ?
        st4.execute();
        System.out.println("Record deleted successfully");

        // Step 7: Close the connection to release resources
        con.close();
        System.out.println("Connection closed");
    }
}
