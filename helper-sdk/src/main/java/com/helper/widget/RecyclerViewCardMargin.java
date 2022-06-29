package com.helper.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewCardMargin extends RecyclerView.ItemDecoration{

    public static final int EXCLUDE = -1;
    private final int marginLeftOrTop, marginRightOrBottom;
    private final @RecyclerView.Orientation int orientation;

    /**
     * @implNote for RecyclerView.VERTICAL
     * @implNote use when cardView margins are - top:4, bottom:12, left:16, right:16.
     * @apiNote recyclerView.addItemDecoration(new ItemDecorationCardMargin(activity, 12, 4));
     */
    public RecyclerViewCardMargin(Context context, int marginLeftOrTop, int marginRightOrBottom) {
        this.marginLeftOrTop = (int) convertDpToPx(context, marginLeftOrTop);
        this.marginRightOrBottom = (int) convertDpToPx(context, marginRightOrBottom);
        this.orientation = RecyclerView.VERTICAL;
    }
    /**
     * @implNote for RecyclerView.HORIZONTAL
     * @implNote use when cardView margins are - top:4, bottom:12, left:8, right:8.
     * @apiNote recyclerView.addItemDecoration(new ItemDecorationCardMargin(activity, 8, 8, RecyclerView.HORIZONTAL));
     */
    public RecyclerViewCardMargin(Context context, int marginLeftOrTop, int marginRightOrBottom, @RecyclerView.Orientation int orientation) {
        this.marginLeftOrTop = (int) convertDpToPx(context, marginLeftOrTop);
        this.marginRightOrBottom = (int) convertDpToPx(context, marginRightOrBottom);
        this.orientation = orientation;
    }

    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (marginLeftOrTop != EXCLUDE && parent.getChildAdapterPosition(view) == 0) {
            if(orientation == RecyclerView.HORIZONTAL) {
                outRect.left = marginLeftOrTop;
            }else {
                outRect.top = marginLeftOrTop;
            }
        }
        if (marginRightOrBottom != EXCLUDE && parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
            if(orientation == RecyclerView.HORIZONTAL) {
                outRect.right = marginRightOrBottom;
            }else {
                outRect.bottom = marginRightOrBottom;
            }
        }
    }
}