package bbsource.myormliteexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vdabcursist on 06/10/2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME ="bank";
    private static final int DATABASE_VERSION = 1;

    private Context context;
    private Dao<Person, Long> simpleDAO = null;

    public Context getContext() {
        return context;
    }


    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        this.context =context;
    }
    public Dao<Person, Long> getSimpleDAO() {
        return simpleDAO;
    }

    public Dao<Person, Long> getDao() throws SQLException {
        {
            if (simpleDAO == null) {
                simpleDAO = getDao(Person.class);
            }
            return simpleDAO;
        }
    }


    //Fetch all persons records and return list from database

    public List<Person> getData(){
        DatabaseHelper helper = new DatabaseHelper(context);
        try {
            Dao<Person, Long> localSimpleDao = helper.getDao();
            return localSimpleDao.queryForAll();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    //add person to database
    // @param person Person object to persist to the database and @return is number of rows updated in database, should be 1
    public int addData(Person person){
        try {
            Dao<Person,Long> dao = getDao();
            return dao.create(person);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void  deleteAll(){
        try {
            Dao<Person, Long> dao = getDao();
            List<Person> list = dao.queryForAll();
            dao.delete(list);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Person.class);
        } catch (java.sql.SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Cannot create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource,Person.class,true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Cannot drop databases", e);
            throw new RuntimeException(e);
        }
    }
}


