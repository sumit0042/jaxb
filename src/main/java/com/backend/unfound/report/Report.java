package com.backend.unfound.report;

import javax.persistence.*;

import java.util.ArrayList;

/**
 * Created by sumit on 19/12/17.
 */

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDENTITY", updatable = false, nullable = false, insertable = false)
    private Long IDENTITY;

    private String id;
    private String timeStamp;
    private String urlTimestamp;
    private String frame;
    private String power;
    private ArrayList<ArrayList<String>> wheels;
    private String name;

    public Report() {
    }

    public Report(String  timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Report(String id, String filePath) {
        this.id = id;
    }


    public String getUrlTimestamp() {
        return urlTimestamp;
    }

    public void setUrlTimestamp(String urlTimestamp) {
        this.urlTimestamp = urlTimestamp;
    }

    public String getName(){return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public ArrayList<ArrayList<String>> getWheels() {
        return wheels;
    }

    public void setWheels(ArrayList<ArrayList<String>> wheels) {
        this.wheels = wheels;
    }
}
