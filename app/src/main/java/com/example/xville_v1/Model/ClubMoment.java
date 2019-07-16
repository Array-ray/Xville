package com.example.xville_v1.Model;

public class ClubMoment {
    private String clubProfile;
    private String clubName;
    private String eventPoster;

    public ClubMoment(String clubProfile, String clubName, String eventPoster) {
        this.clubProfile = clubProfile;
        this.clubName = clubName;
        this.eventPoster = eventPoster;
    }

    public ClubMoment() {
    }

    public String getClubProfile() {
        return clubProfile;
    }

    public void setClubProfile(String clubProfile) {
        this.clubProfile = clubProfile;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getEventPoster() {
        return eventPoster;
    }

    public void setEventPoster(String eventPoster) {
        this.eventPoster = eventPoster;
    }
}
