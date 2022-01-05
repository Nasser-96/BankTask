package com.example.BankTask.Loan;

public class Loan {
    private int loan;
    private String password;
    private String email;

    public Loan(int loan, String password, String email) {
        this.loan = loan;
        this.password = password;
        this.email = email;
    }

    public int getLoan() {
        return loan;
    }

    public void setLoan(int loan) {
        this.loan = loan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
