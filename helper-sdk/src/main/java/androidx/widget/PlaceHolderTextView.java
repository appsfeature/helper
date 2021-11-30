package androidx.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.helper.R;

public class PlaceHolderTextView extends AppCompatTextView {

    private Paint mPaint;
    private int placeHolderColor = Color.BLACK;

    public PlaceHolderTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PlaceHolderTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PHTextView, 0, 0);
        try {
            placeHolderColor = a.getColor(R.styleable.PHTextView_placeHolderColor, 0xffe5e5e5);
        } finally {
            a.recycle();
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(placeHolderColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(getTextSize());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight() / 2;

        if (mPaint != null && canvas != null) {
            canvas.drawLine(0, height, width, height, mPaint);
        }
        setTextColor(Color.TRANSPARENT);
    }
}