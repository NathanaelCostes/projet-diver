package com.projetdiver.spot;

public class Spot {

    /** id of the spot */
    private Integer spotId;

    /** name of the spot */
    private String name;

    /** latitude of the spot */
    private Float latitude;

    /** longitude of the spot */
    private Float longitude;

    /** max depth of the spot */
    private Integer maxDepth;

    /** type of the spot */
    private String type;

    /** point of interest of the spot */
    private String poi;

    /** level advised of the spot */
    private String level;

    /**
     * Create a spot
     * @param name of the spot
     * @param latitude of the spot
     * @param longitude of the spot
     * @param maxDepth of the spot
     * @param type of the spot
     * @param poi of the spot
     * @param level of the spot
     */
    public Spot(Integer spotId, String name, Float latitude, Float longitude, Integer maxDepth, String type, String poi, String level) {
        this.spotId = spotId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.maxDepth = maxDepth;
        this.type = type;
        this.poi = poi;
        this.level = level;
    }

    /** Default constructor */
    public Spot() {}

    /**
     * @return spotId
     */
    public Integer getSpotId() { return spotId; }

    /**
     * @param spotId the spotId to set
     */
    public void setSpotId(Integer spotId) { this.spotId = spotId; }

    /**
     * @return name
     */
    public String getName() { return name; }

    /**
     * @param name the name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * @return latitude
     */
    public Float getLatitude() { return latitude; }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Float latitude) { this.latitude = latitude; }

    /**
     * @return longitude
     */
    public Float getLongitude() { return longitude; }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Float longitude) { this.longitude = longitude; }

    /**
     * @return maxDepth
     */
    public Integer getMaxDepth() { return maxDepth; }

    /**
     * @param maxDepth the maxDepth to set
     */
    public void setMaxDepth(Integer maxDepth) { this.maxDepth = maxDepth; }

    /**
     * @return type
     */
    public String getType() { return type; }

    /**
     * @param type the type to set
     */
    public void setType(String type) { this.type = type; }

    /**
     * @param poi the poi to set
     */
    public void setPoi(String poi) { this.poi = poi; }

    /**
     * @return poi
     */
    public String getPoi() { return poi; }

    /**
     * @param level the level to set
     */
    public void setLevel(String level) { this.level = level; }

    /**
     * @return level
     */
    public String getLevel() { return level; }

    @Override
    public String toString() {
        return "Spot{" +
                "spotId=" + spotId +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", maxDepth=" + maxDepth +
                ", type='" + type + '\'' +
                ", poi='" + poi + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
