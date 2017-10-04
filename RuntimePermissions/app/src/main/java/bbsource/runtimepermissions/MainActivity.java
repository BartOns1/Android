package bbsource.runtimepermissions;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int RUNTIME_PERMISSION_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonRequestPermission = (Button) findViewById(R.id.buttonRequestPersmission);

        //adding a click listener

        buttonRequestPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //First checking if the app is already having the permission
                if (isCameraAccessAllowed()){
                    //If permission is already having then showing the toast
                    Toast.makeText(v.getContext(),"You already have the permission to access Camera", Toast.LENGTH_LONG).show();
                    //Existing the method with return
                    return;
                }
                requestCameraPermission();
            }
        });
    }

    private boolean isCameraAccessAllowed() {
        //Getting the permission status
        int result= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        //If permission is granted returning true
        if(result== PackageManager.PERMISSION_GRANTED) return true;

        //if permission is not granted returning false
        return false;
    }

    private void requestCameraPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            showAlertDialog();
        }
        //And finaly ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, RUNTIME_PERMISSION_CODE);

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //set title
        alertDialogBuilder.setTitle("Runtime Permissions");

        //set dialog message
        alertDialogBuilder.setMessage("Need your permission" + " or this app will not see your prety face").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
            }
        });

        //create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        //show it
        alertDialog.show();
    }

    //Deze kan je maar 1 keer schrijven en alle permissies staan hier in.  RUNTIME_PERMISSION_CODE had beter CAMERA_PERMISSION_CODE geheten
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        //checking the requestcode of your request
        if (requestCode == RUNTIME_PERMISSION_CODE){
            //if permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Displaying a toast
                Toast.makeText(this,"Permission granted now you can use the camera", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"OOps you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
