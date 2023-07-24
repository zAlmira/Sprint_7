package ru.praktikum_services.qa_scooter;

public class CourierLoginWithoutLogin {
    private String password;

    public CourierLoginWithoutLogin(String password) {
        this.password = password;
    }

    public CourierLoginWithoutLogin() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
