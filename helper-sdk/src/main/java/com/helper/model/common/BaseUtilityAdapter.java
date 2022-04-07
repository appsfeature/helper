package com.helper.model.common;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class BaseUtilityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    /**
     * @param drawable : itemView.getBackground()
     * @param color    : ContextCompat.getColor(context, R.color.color1);
     */
    @SuppressWarnings("deprecation")
    public void setColorFilter(@NonNull Drawable drawable, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }


    /**
     * @param colors   : int[] colors = context.getResources().getIntArray(R.array.dynamic_colors);
     * @param position : adapter position
     * @apiNote : cardView.setCardBackgroundColor(getSequentialColor(position));
     */
    public int getSequentialColor(int[] colors, int position) {
        if(colors == null) return Color.BLACK;
        if (position % colors.length == 0) {
            return colors[0];
        } else {
            for (int i = 1; i < colors.length; i++) {
                if (position == i || (position - i) % colors.length == 0) {
                    return colors[i];
                }
            }
            return colors[0];
        }
    }

    /**
     * @param colors   : String[] colors = context.getResources().getStringArray(R.array.dynamic_colors);
     * @param position : adapter position
     * @apiNote : cardView.setCardBackgroundColor(getSequentialColor(position));
     */
    public int getSequentialColor(String[] colors, int position) {
        if(colors == null) return Color.BLACK;
        if (position % colors.length == 0) {
            return Color.parseColor(colors[0]);
        } else {
            for (int i = 1; i < colors.length; i++) {
                if (position == i || (position - i) % colors.length == 0) {
                    return Color.parseColor(colors[i]);
                }
            }
            return Color.parseColor(colors[0]);
        }
    }

    /**
     * @param startColors : gradient start color integer array list
     * @param endColors : gradient end color integer array list
     * @param position : adapter position
     * @apiNote : llCard.setBackground(getGradientDrawable(startColors, endColors, position));
     */
    public Drawable getGradientDrawable(int[] startColors, int[] endColors, int position) {
        return getGradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, 16f, startColors, endColors, position);
    }
    public Drawable getGradientDrawable(GradientDrawable.Orientation orientation, float radius, int[] startColors, int[] endColors, int position) {
        GradientDrawable gd = new GradientDrawable(
                orientation,
                new int[]{
                        startColors != null ? getSequentialColor(startColors, position) : Color.BLACK,
                        endColors != null ? getSequentialColor(endColors, position) : Color.WHITE
                });
        gd.setCornerRadius(radius);
        return gd;
    }

    public String getTimeInReadableFormat(String inputDate) {
        return getTimeInReadableFormat(inputDate, "dd-MMM-yy hh:mm:a");
    }

    public String getTimeInReadableFormat(String inputDate, String outputFormat) {
        try {
            if(TextUtils.isEmpty(inputDate)) return inputDate;
            Date date = getDateFormat().parse(inputDate);
            if(date != null) return new SimpleDateFormat(outputFormat, Locale.US).format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputDate;
    }

    public String getTimeInDaysAgoFormat(String serverDateFormat){
        try {
            Date mDate = getDateFormat().parse(serverDateFormat);
            if(mDate != null) return getTimeInDaysAgoFormat(mDate.getTime()).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return serverDateFormat;
    }

    /**
     * @param mileSecond enter time in millis
     * @return Returns a string describing 'time' as a time relative to 'now'.
     * Time spans in the past are formatted like "42 minutes ago". Time spans in the future are formatted like "In 42 minutes".
     * i.e: 5 days ago, or 5 minutes ago.
     */
    public CharSequence getTimeInDaysAgoFormat(long mileSecond){
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_ABBREV_MONTH | DateUtils.FORMAT_SHOW_TIME;
        return getTimeInDaysAgoFormat(mileSecond, flags);
    }

    public CharSequence getTimeInDaysAgoFormat(long mileSecond, int flags){
        return DateUtils.getRelativeTimeSpanString(mileSecond, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, flags);
    }

    private SimpleDateFormat simpleDateFormat;

    private SimpleDateFormat getDateFormat() {
        if(simpleDateFormat == null) simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return simpleDateFormat;
    }
}
