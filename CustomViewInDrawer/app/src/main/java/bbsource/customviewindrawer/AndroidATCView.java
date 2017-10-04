package bbsource.customviewindrawer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by vdabcursist on 02/10/2017.
 */

public class AndroidATCView extends View {
    //circle and text colors
    private int mSquareCol, mLabelCol;
    //label text
    private String mSquareText;
    //paint for drawing custom view
    private Paint mSquarePaint;

    public AndroidATCView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //paint object for drawing onDraw
        mSquarePaint = new Paint();
        //get the attributes spcified in attrs.xml
        //using the name you included
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AndroidATCView, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            mSquareText = typedArray.getString(R.styleable.AndroidATCView_squareLabel);
            //0 is default
            mSquareCol = typedArray.getInteger(R.styleable.AndroidATCView_squareColor, 0);
            mLabelCol = typedArray.getInteger(R.styleable.AndroidATCView_labelColor, 0);
        } finally {
            typedArray.recycle();
        }
    }

    public int getSquareCol() {
        return mSquareCol;
    }

    public void setSquareColor(int newCol) {
        this.mSquareCol = newCol;
        //redraw the view
        invalidate();
        requestLayout();
    }

    public int getLabelCol() {
        return mLabelCol;
    }

    public void setLabelColor(int newCol) {
        this.mLabelCol = newCol;
        invalidate();
        requestLayout();
    }

    public String getSquareText() {
        return mSquareText;
    }

    public void setLabelText(String newLabel) {
        this.mSquareText = newLabel;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isInEditMode())
        {
        //set drawing properties
        mSquarePaint.setStyle(Paint.Style.FILL);
        mSquarePaint.setAntiAlias(true);

        //set the paint color using the circle color specified
        mSquarePaint.setColor(mSquareCol);

        //draw the square using prop defined
        canvas.drawRect(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight(), mSquarePaint);

        //Set the text color using the color specified
        mSquarePaint.setColor(mLabelCol);

        //set text properties
        mSquarePaint.setTextAlign(Paint.Align.CENTER);
        mSquarePaint.setTextSize(50);
        //draw the text using the string attribute and chosen properties
        canvas.drawText(mSquareText, this.getMeasuredWidth()/2, this.getMeasuredHeight()/2, mSquarePaint);
        }
    }
}
