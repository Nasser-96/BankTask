package com.example.BankTask.Deposit;

public class Deposit {
    private int deposit;
    private String email;
    private String password;

    public Deposit(int deposit, String email, String password) {
        this.deposit = deposit;
        this.email = email;
        this.password = password;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
