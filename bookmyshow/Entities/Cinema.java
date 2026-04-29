package Entities;


import java.util.List;

public class Cinema {
    private final String id;
    private final String cityId;
    private final List<Screen> screens;

    public Cinema(String id, String cityId, List<Screen> screens) {
        this.id = id; //TODO: Add id generator
        this.cityId = cityId;
        this.screens = screens;
    }

    public String getId() { return id; }
    public String getCityId() { return cityId; }
    public List<Screen> getScreens() { return screens; }

    // Can i add add screen method here?
}