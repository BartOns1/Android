package bbsource.contactsselectionapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactPageActivity extends AppCompatActivity implements View.OnClickListener {

    private ContactObject contactObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);
        TextView contactName=(TextView) findViewById(R.id.contactName);
        TextView contactPhone=(TextView) findViewById(R.id.contactPhone);
        TextView contactWebsite = (TextView) findViewById(R.id.contactWebsite);

        Bundle extras = getIntent().getExtras();
        if(extras ==null)
            return;

        contactObject = (ContactObject) getIntent().getSerializableExtra("object");
        contactName.setText(contactObject.getName());
        contactPhone.setText(String.format("Phone:%s", contactObject.getPhone()));
        contactWebsite.setText(String.format("website:%s", contactObject.getWebsite()));
        contactPhone.setOnClickListener(this);
        contactWebsite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.contactPhone:
                Intent i = new Intent();
                i.putExtra("value", contactObject.getPhone());
                setResult(Constants.PHONE, i);
                finish();
                break;
            case R.id.contactWebsite:
                i=new Intent();
                i.putExtra("value", contactObject.getWebsite());
                setResult(Constants.WEBSITE,i);
                finish();
                break;
        }
    }

}
