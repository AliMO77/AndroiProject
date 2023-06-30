package lu.uni.sep.group5.UniLux.models;

public class Building {

    private String name;
    private double latitude;
    private double longitude;
    private String shortcut;

    public Building(String name) {
        this.name = name;
    }

    public Building(String name, double latitude, double longitude, String shortcut) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.shortcut = shortcut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }
}
