package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

public class Menu {
  private Scanner scanner;

  public static void main(String[] args) {
    System.out.println("Welcome to Shivam's Bank");

    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);
    Customer customer = menu.authenticateUser();

    if (customer != null) {
      Accounts account = DataSource.getAccount(customer.getAccount_id());
      menu.showMenu(customer, account);
    }

    menu.scanner.close();
  }

  private Customer authenticateUser() {
    System.out.println("Enter username: ");
    String username = scanner.next();

    System.out.println("Enter password: ");
    String password = scanner.next();

    Customer customer = null;
    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println("There was some error: " + e.getMessage());
    }

    return customer;
  }

  public void showMenu(Customer customer, Accounts account) {

    int selection = 0;

    while (selection != 4 && customer.isAuthenticated()) {

      System.out.println("===========================");
      System.out.println("Please Select An Option!");
      System.out.println("1: Deposit");
      System.out.println("2: Withdraw");
      System.out.println("3: Check Balance");
      System.out.println("4: Exit");
      System.out.println("===========================");

      selection = scanner.nextInt();
      double amount = 0;
      switch (selection) {
        case 1:
          System.out.println("How much do you want to Deposit? ");
          amount = scanner.nextDouble();
          try {
            account.deposit(amount);
          } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Try again!");
          }

          break;
        case 2:
          System.out.println("How much do you want to Withdraw? ");
          amount = scanner.nextDouble();
          try {
            account.withdraw(amount);
          } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Try again!");
          }

          break;
        case 3:
          System.out.println("Your Account Balance is: Rs. " + account.getBalance());
          break;
        case 4:
          Authenticator.logout(customer);
          System.out.println("Thank You for Banking with us! ");
          break;
        default:
          System.out.println("Invalid input! ");
          break;

      }
    }

  }

}
