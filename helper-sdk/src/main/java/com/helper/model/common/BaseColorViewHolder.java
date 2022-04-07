package com.helper.model.common;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseColorViewHolder extends RecyclerView.ViewHolder {

    public BaseColorViewHolder(@NonNull View itemView) {
        super(itemView);
    }

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
}
