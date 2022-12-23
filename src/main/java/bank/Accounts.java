package bank;

import bank.exception.AmountException;

public class Accounts {
  private int id;
  private String type;
  private double balance;

  public Accounts(int id, String type, double balance) {
    setId(id);
    setType(type);
    setBalance(balance);

  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double getBalance() {
    return this.balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void deposit(Double amount) throws AmountException {
    if (amount < 1) {
      throw new AmountException("Minimum deposit is Rs. 1.00 ");
    } else {
      Double newBalance = balance + amount;
      setBalance(newBalance);
      DataSource.updateBalance(id, newBalance);
    }

  }

  public void withdraw(Double amount) throws AmountException {
    if (amount < 0) {
      throw new AmountException("Minimum withdraw is Rs. 1.00 ");
    } else if (amount > balance) {
      throw new AmountException("Insufficient funds! ");
    } else {
      Double newBalance = balance - amount;
      setBalance(newBalance);
      DataSource.updateBalance(id, newBalance);
    }
  }
}
