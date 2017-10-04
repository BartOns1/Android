package bbsource.materialtodolist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ToDoListActivity extends AppCompatActivity {

    private EditText mItemInput;
    private FloatingActionButton mAddButton;
    private ListView mDynamicListView;
    private List<String> mItemList;
    private ArrayAdapter<String> mItemListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        mItemInput = (EditText) findViewById(R.id.itemEditText);
        mAddButton = (FloatingActionButton)  findViewById(R.id.add_item_button);
        mDynamicListView = (ListView) findViewById(R.id.itemListView);

        //Initialise items Arraylist and add a default item
        //so that we always see one item when app is launched
        mItemList = new ArrayList<>();
        mItemList.add("AndroidList");

        //Initialise arrayadapter thatholds the mapping of Strings in arraylist to textview in listView items
        mItemListAdapter = new ArrayAdapter<String>(ToDoListActivity.this, R.layout.list_individual_item, R.id.list_itemText, mItemList);

        //Setting the arrayadapter to the listView
        mDynamicListView.setAdapter(mItemListAdapter);

        //add item to the listView on click floating action button(mAddButton)
        mAddButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String todoItem = mItemInput.getText().toString();
                if (todoItem.length() > 0)
                {
                    //add editText inputto the item list
                    mItemList.add(todoItem);

                    //Apply changes on the ArrayAdapter to refresh the listView
                    mItemListAdapter.notifyDataSetChanged();

                    //Clear the editText
                    mItemInput.setText("");
                }
            }
        });

        //Delete mItemInput on the long click on the mItemInput
        mDynamicListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Remove the itemInput from mItemsList
                mItemList.remove(position);
                mItemListAdapter.notifyDataSetChanged();
                return true;
            }
        });

        mDynamicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ToDoListActivity.this,ToDoListActivity.class);

                //Pass in the text of the item that is clicked, so that the text in Dtails view can be populated accordingly
                String textOfClickedItem = mItemListAdapter.getItem(position);
                intent.putExtra("Name", textOfClickedItem);
                startActivity(intent);
            }
        });
    }



}
