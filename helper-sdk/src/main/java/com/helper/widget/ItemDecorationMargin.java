package com.helper.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecorationMargin extends RecyclerView.ItemDecoration{

    private static final float DEFAULT_MARGIN = 16;
    private final int defaultMargin;
    private final boolean isAddOnBottom;

    public ItemDecorationMargin(Context context) {
        this.isAddOnBottom = false;
        this.defaultMargin = (int) convertDpToPx(context, DEFAULT_MARGIN);
    }

    public ItemDecorationMargin(Context context, int defaultMargin) {
        this.isAddOnBottom = false;
        this.defaultMargin = (int) convertDpToPx(context, defaultMargin);
    }

    /**
     * @param defaultMargin : 5
     * @param isAddLastItemMargin : true when layout_margin="5dp" use in slot cardView
     */
    public ItemDecorationMargin(Context context, int defaultMargin, boolean isAddLastItemMargin) {
        this.isAddOnBottom = isAddLastItemMargin;
        this.defaultMargin = (int) convertDpToPx(context, defaultMargin);
    }

    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = defaultMargin;
        }
        if(isAddOnBottom) {
            if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
                outRect.bottom = defaultMargin;
            }
        }else {
            outRect.bottom = defaultMargin;
        }
        outRect.left =  defaultMargin;
        outRect.right = defaultMargin;
    }

    private boolean isLastItem(RecyclerView parent, View view, RecyclerView.State state){
        return parent.getChildAdapterPosition(view) == state.getItemCount() - 1;
    }
}
