package bbsource.trackslogger.domain;

import java.util.ArrayList;
import java.util.List;


public class Participant {



    private String label;

    private String color="lightblue";

    public Group group;

    private String traceRecord;

    private List<Coordinate> coordinates = new ArrayList<>();


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public Participant(){}
    public Participant(String label, String traceRecord) {

        this.label = label;
        this.traceRecord = traceRecord;

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTraceRecord() {
        return traceRecord;
    }

    public void setTraceRecord(String traceRecord) {
        this.traceRecord = traceRecord;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public Participant setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
        return null;
    }

    @Override
    public String toString() {
        return "Participant{" +
                ", label='" + label + '\'' +
                ", group=" + group +
                ", traceRecord='" + traceRecord + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
