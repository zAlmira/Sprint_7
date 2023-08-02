package ru.praktikum.services.qa.scooter.api.model;

public class MakeCourierWithoutLogin {
    private String password;
    private String firstName;

    public MakeCourierWithoutLogin(String password, String firstName) {
        this.password = password;
        this.firstName = firstName;
    }

    public MakeCourierWithoutLogin() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
