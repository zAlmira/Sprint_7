package ru.praktikum.services.qa.scooter.api.model;

public class MakeOrderResponse {
    private String track;

    public MakeOrderResponse(String track) {
        this.track = track;
    }

    public MakeOrderResponse() {
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}
