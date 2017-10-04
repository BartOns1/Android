package bbsource.androidatccourses;

/**
 * Created by vdabcursist on 28/09/2017.
 */

public class DataModel {

    private String  name;
    private int id_;

    public DataModel(String name, int id_){
        this.name =name;
        this.id_=id_;
    }

    public String getName() {
        return name;
    }

    public int getId_() {
        return id_;
    }
}
