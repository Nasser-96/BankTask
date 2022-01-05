package com.example.BankTask.WithDraw;

public class WithDraw {
    private int withDraw;
    private String email;
    private String password;

    public WithDraw(int withDraw, String email, String password) {
        this.withDraw = withDraw;
        this.email = email;
        this.password = password;
    }

    public int getWithDraw() {
        return withDraw;
    }

    public void setWithDraw(int withDraw) {
        this.withDraw = withDraw;
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
