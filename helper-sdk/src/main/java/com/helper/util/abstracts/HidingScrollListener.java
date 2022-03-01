package com.helper.util.abstracts;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @apiNote Usage: RecyclerView.addOnScrollListener(new HidingScrollListener(){});
 */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

    private static final int minItemCount = 4;
    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (recyclerView.getAdapter() != null) {
            if(recyclerView.getAdapter().getItemCount() <= minItemCount){
                return;
            }
        }
        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
            onHideView();
            controlsVisible = false;
            scrolledDistance = 0;
        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            onShowView();
            controlsVisible = true;
            scrolledDistance = 0;
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }
    }

    public abstract void onHideView();

    public abstract void onShowView();
}
