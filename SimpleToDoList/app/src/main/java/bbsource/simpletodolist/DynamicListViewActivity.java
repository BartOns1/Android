package bbsource.simpletodolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import static bbsource.simpletodolist.R.id.add;
import static bbsource.simpletodolist.R.id.itemEditText;

public class DynamicListViewActivity extends AppCompatActivity {

    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private EditText item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_list_view);
        item = (EditText) findViewById(R.id.itemEditText);
        final Button add = (Button) findViewById(R.id.addItemButton);
        ListView dynamicListView  = (ListView) findViewById(R.id.itemsListView);

        //initialize the list and add items
        list = new ArrayList<String>();
        //initialise the arrayadapter
        adapter = new ArrayAdapter<String>(DynamicListViewActivity.this, android.R.layout.simple_expandable_list_item_1,list);
        //setting the adaptor to the listview
        dynamicListView.setAdapter(adapter);


        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                String todoItem = item.getText().toString();
                if (todoItem.length()>0){
                    //add editText Value to the list
                    list.add(item.getText().toString());
                    //apply changes on the adapter to refresh the listview
                    adapter.notifyDataSetChanged();
                    item.setText("");
                }
            }
        });


        dynamicListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //remove the item from the list
                list.remove(position);
                //apply changes to the adapter to refresh the listview
                adapter.notifyDataSetChanged();
                return true;
            }
        });

    }




}
