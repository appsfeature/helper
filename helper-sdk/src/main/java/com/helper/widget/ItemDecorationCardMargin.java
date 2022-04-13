package com.helper.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecorationCardMargin extends RecyclerView.ItemDecoration{

    public static final int EXCLUDE = -1;
    private final int marginTop, marginBottom;
    private final int marginLeft, marginRight;

    /**
     * @implNote use when cardView margins are - top:4, bottom:12, left:16, right:16.
     * @apiNote recyclerView.addItemDecoration(new ItemDecorationCardMargin(activity));
     */
    public ItemDecorationCardMargin(Context context) {
        this(context, 12, 4, EXCLUDE, EXCLUDE);
    }

    public ItemDecorationCardMargin(Context context, int marginTop, int marginBottom) {
        this(context, marginTop, marginBottom, EXCLUDE, EXCLUDE);
    }

    public ItemDecorationCardMargin(Context context, int marginTop, int marginBottom, int marginLeft, int marginRight) {
        this.marginTop = (int) convertDpToPx(context, marginTop);
        this.marginBottom = (int) convertDpToPx(context, marginBottom);
        this.marginLeft = (int) convertDpToPx(context, marginLeft);
        this.marginRight = (int) convertDpToPx(context, marginRight);
    }

    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (marginTop != EXCLUDE && parent.getChildAdapterPosition(view) == 0) {
            outRect.top = marginTop;
        }
        if (marginBottom != EXCLUDE && parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
            outRect.bottom = marginBottom;
        }
        if(marginLeft != EXCLUDE) {
            outRect.left = marginLeft;
        }
        if(marginRight != EXCLUDE) {
            outRect.right = marginRight;
        }
    }
}
