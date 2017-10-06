package bbsource.wallpaper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImagePreview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        //get intent data
        Intent i = getIntent();
        //Selected image id
        Integer imageResource = i.getExtras().getInt("id");
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        //imageView.setImageResource(imageResource);
        Picasso.with(getApplicationContext())
                .load(imageResource)
                .into(imageView);
    }
}
