package bbsource.wallpaper;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends BaseAdapter {

    public Integer[] mThumbIds = {
            R.drawable.pic09, R.drawable.pic10, R.drawable.pic11, R.drawable.pic12, R.drawable.pic13, R.drawable.pic14, R.drawable.pic15,
            R.drawable.pic16, R.drawable.pic17, R.drawable.pic18, R.drawable.pic01, R.drawable.pic02, R.drawable.pic03, R.drawable.pic04,
            R.drawable.pic05, R.drawable.pic06, R.drawable.pic07, R.drawable.pic08
    };

    private Context mContext;

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
       // imageView.setImageResource(mThumbIds[position]);
       // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
       // imageView.setLayoutParams(new GridView.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,200));

        Picasso.with(mContext)
                .load(mThumbIds[position])
                .noFade()
                .resize(330,350)
                .centerCrop()
                .into(imageView);

        return imageView;
    }
}
