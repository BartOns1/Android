package bbsource.myormliteexample;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by vdabcursist on 06/10/2017.
 */

public class Person {
    @DatabaseField(generatedId = true)
    private int accountId;

    @DatabaseField
    private String name;

    public int getAccountId(){
        return accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccountId(int accountId){
        this.accountId=accountId;

    }

}
