package bank;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataSource {
  public static Connection connect() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;

    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("we are connected!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;

  }

  public static Customer getCustomer(String username) {
    String sql = "select * from Customers where username = ?";
    Customer customer = null;

    try (Connection connection = connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        customer = new Customer(resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getInt("account_id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return customer;

  }

  public static Accounts getAccount(int accountId) {
    String sql = "select * from Accounts where id = ?";
    Accounts account = null;

    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, accountId);
      try (ResultSet resultSet = statement.executeQuery()) {
        account = new Accounts(resultSet.getInt("id"),
            resultSet.getString("type"),
            resultSet.getDouble("balance"));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return account;
  }

  public static void updateBalance(int account_id, Double balance) {
    String sql = "update accounts set balance = ? where id = ? ";

    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setDouble(1, balance);
      statement.setInt(2, account_id);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}
