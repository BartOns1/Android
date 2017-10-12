package bbsource.trackslogger.domain;

import java.util.Date;

/**
 * Created by vdabcursist on 11/10/2017.
 */

public class Coordinate {


    private Date timeStamp;
    private double latitude;
    private double longitude;

    public Coordinate() {
    }

    public Coordinate(Date timeStamp, double latitude, double longitude) {
        this.timeStamp = timeStamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Date getTime() {
        return timeStamp;
    }

    public void setTime(Date time) {
        this.timeStamp = time;
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


}
