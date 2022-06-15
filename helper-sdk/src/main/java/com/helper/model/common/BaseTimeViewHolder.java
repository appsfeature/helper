package com.helper.model.common;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BaseTimeViewHolder extends RecyclerView.ViewHolder {

    public BaseTimeViewHolder(@NonNull View itemView) {
        super(itemView);
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
            if(TextUtils.isEmpty(serverDateFormat)) return serverDateFormat;
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
