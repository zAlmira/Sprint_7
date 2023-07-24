package ru.praktikum_services.qa_scooter;

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
