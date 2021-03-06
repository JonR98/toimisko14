package app.dao;
 
import java.sql.*;

import app.model.Users;
 
// Made by Joona Rinta-Könnö

public class UserDao {
 //tarkistaa tietokannasta onko kirjautumistiedot adminilla oikein.
    public Users checkLogin(String email, String password) throws SQLException,
            ClassNotFoundException {
        String jdbcURL = "jdbc:mysql://localhost:3306/vaalikone";
        String dbUser = "pena";
        String dbPassword = "kukkuu";
 
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        String sql = "SELECT * FROM users WHERE email = ? and password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, password);
 
        ResultSet result = statement.executeQuery();
 
        Users user = null;
 
        if (result.next()) {
            user = new Users();
            user.setFullname(result.getString("fullname"));
            user.setEmail(email);
        }
 
        connection.close();
 
        return user;
    }
}