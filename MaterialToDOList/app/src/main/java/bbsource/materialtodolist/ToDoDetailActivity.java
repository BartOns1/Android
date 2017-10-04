package bbsource.materialtodolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ToDoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_details);

        //Get clicked list item, for dysplay before launching the etailActivity, the listactivity added text of clicked item as a stringExtra in the itent
        String text = getIntent().getStringExtra("Name");

        TextView title = (TextView) findViewById(R.id.detailsTitleText);
        title.setText(text);ff

    }
}
