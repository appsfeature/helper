package com.helper.util;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.format.DateUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public abstract class BaseDatabaseHelper extends SQLiteOpenHelper {

    public BaseDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BaseDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public BaseDatabaseHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    public String removePadding(String s) {
        if (s != null) {
            if (s.contains("\r\n"))
                s = s.replaceAll("\r\n", "");
            if (s.contains("<p>")) {
                s = s.replaceAll("<p>", "");
                s = s.replaceAll("</p>", "");
            }
        }
        return s;
    }

    /**
     * @param s input html content
     * @return removed bottom padding form html content
     */
    public String removeBottomPadding(String s) {
        if (s.contains("<p>&nbsp;</p>"))
            s = s.replaceAll("<p>&nbsp;</p>", "");
        return s;
    }

    private SimpleDateFormat yyyyMMdd;
    private SimpleDateFormat ddMMMyyyy;

    public String convertTime(String value) {
        String output = "";
        if (value == null || value.equalsIgnoreCase("null")) {
            return "";
        }
        try {
            if(yyyyMMdd == null){
                yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                yyyyMMdd.setLenient(false);
            }
            Date date = yyyyMMdd.parse(value);
            if (date != null) {
                if(ddMMMyyyy == null){
                    ddMMMyyyy = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                    ddMMMyyyy.setLenient(false);
                }
                output = ddMMMyyyy.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }


    protected SimpleDateFormat simpleDateFormat;

    public String getDate(long time) {
        if (simpleDateFormat == null)
            simpleDateFormat = new SimpleDateFormat("dd MMM yyyy | hh:mm a", Locale.US);
        return simpleDateFormat.format(new Date(time));
    }

    public String getServerTimeStamp(long time) {
        if (simpleDateFormat == null)
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return simpleDateFormat.format(new Date(time));
    }

    public String getTimeTaken(long time) {
        return timeTaken(time);
    }

    public String timeTaken(long time) {
        return String.format(Locale.US, "%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(time),
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
        );
    }

    public String getFormattedViews(int number) {
        return convertNumberUSFormat(number);
    }

    //US format
    public String convertNumberUSFormat(int number){
        try {
            String[] suffix = new String[]{"K","M","B","T"};
            int size = (number != 0) ? (int) Math.log10(number) : 0;
            if (size >= 3){
                while (size % 3 != 0) {
                    size = size - 1;
                }
            }
            double notation = Math.pow(10, size);
            return (size >= 3) ? + (Math.round((number / notation) * 100) / 100.0d)+suffix[(size/3) - 1] : + number + "";
        } catch (Exception e) {
            e.printStackTrace();
            return number + "";
        }
    }

    // indian format
    public String convertNumberINFormat(int n) {
        try {
            String[] c = new String[]{"K", "L", "Cr"};
            int size = String.valueOf(n).length();
            if (size>=4 && size<6) {
                int value = (int) Math.pow(10, 1);
                double d = (double) Math.round(n/1000.0 * value) / value;
                return (double) Math.round(n/1000.0 * value) / value+" "+c[0];
            } else if(size>5 && size<8) {
                int value = (int) Math.pow(10, 1);
                return (double) Math.round(n/100000.0 * value) / value+" "+c[1];
            } else if(size>=8) {
                int value = (int) Math.pow(10, 1);
                return (double) Math.round(n/10000000.0 * value) / value+" "+c[2];
            } else {
                return n+"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return n + "";
        }
    }


    /**
     * @param mileSecond enter time in millis
     * @return Returns a string describing 'time' as a time relative to 'now'.
     * Time spans in the past are formatted like "42 minutes ago". Time spans in the future are formatted like "In 42 minutes".
     * i.e: 5 days ago, or 5 minutes ago.
     */
    public CharSequence convertTimeStamp(String mileSecond){
        return DateUtils.getRelativeTimeSpanString(Long.parseLong(mileSecond), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }
}
