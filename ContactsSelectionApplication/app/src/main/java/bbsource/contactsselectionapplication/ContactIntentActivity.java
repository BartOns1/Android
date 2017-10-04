package bbsource.contactsselectionapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactIntentActivity extends AppCompatActivity {

    private List<ContactObject> contactList;
    private int RUNTIME_PERMISSION_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_intent);

        ListView intendListView = (ListView) findViewById(R.id.litView1);

        contactList = new ArrayList<>();

        contactList.add(new ContactObject("Android One","111-111-1111", "www.androidATC.com"));
        contactList.add(new ContactObject("Android Two", "222-222-2222","www.androidATC.com"));
        contactList.add(new ContactObject("Android Two", "333-333-3333","www.androidATC.com"));
        contactList.add(new ContactObject("Android Two", "444-444-4444","www.androidATC.com"));

        List<String> listName = new ArrayList<>();
        for(int i=0; i< contactList.size();i++){
            listName.add(contactList.get(i).getName());
        }

        //Initialize the ArrayAdapter object
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ContactIntentActivity.this, android.R.layout.simple_list_item_1, listName);
        intendListView.setAdapter(adapter);


        intendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ContactIntentActivity.this, ContactPageActivity.class);
                i.putExtra("object", contactList.get(position));
                startActivityForResult(i,0);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }
        Bundle resultData = data.getExtras();
        String value = resultData.getString("value");
        switch (resultCode) {
            case Constants.PHONE:
                //implicit intent to make a call
                makeCall(value);
                break;

            case Constants.WEBSITE:
                //implicit intent to visit website
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + value)));
                break;
        }
    }

    private void makeCall(String value){
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if(result == PackageManager.PERMISSION_GRANTED){
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ value)));
        } else {
            requestCallPermission();
        }
    }

    private void requestCallPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)){
            //If the user has denied the permission previously your code will come to this block
            explainPermissionDialog();
        }

        //ask for permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, RUNTIME_PERMISSION_CODE);
    }

    private void explainPermissionDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //set title
        alertDialogBuilder.setTitle("Call Permission Required");

        //set dialog messag
        alertDialogBuilder.setMessage("This application requires call permission to make phone calls. Please grant the pemission")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        //create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        //show it
        alertDialog.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        //checking the request code of your request
        if(requestCode == RUNTIME_PERMISSION_CODE){
            //if permission is granted
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //Displaying a toast
                Toast.makeText(this,"Permission granted now you can make athe phone call", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"You just denied the permission", Toast.LENGTH_LONG).show();
            }
        }

    }

}
