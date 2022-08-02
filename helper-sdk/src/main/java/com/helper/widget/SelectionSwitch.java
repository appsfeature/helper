package com.helper.widget;

import android.view.View;

/**
 * @apiNote : Usage method
 *      SelectionSwitch swGender = new SelectionSwitch(filterMale, filterFemale)
 *              .setSelected(AppPreference.isGenderMale());
 *      swGender.isFirstSelected()
 *
 *  drawable/selector_tabs_text.xml
    <?xml version="1.0" encoding="utf-8"?>
    <selector xmlns:android="http://schemas.android.com/apk/res/android">
      <item android:color="@android:color/white" android:state_selected="true"/>
      <item android:color="@color/themeTextColorLite" android:state_selected="false"/>
    </selector>

 *  drawable/selector_tabs_background.xml
    <?xml version="1.0" encoding="utf-8"?>
    <selector xmlns:android="http://schemas.android.com/apk/res/android">
      <item android:drawable="@drawable/bg_shape_filter_selected" android:state_selected="true"/>
      <item android:drawable="@android:color/transparent" android:state_selected="false"/>
    </selector>
 */
public class SelectionSwitch {

    private final View tvSwitch1;
    private final View tvSwitch2;
    private boolean isFirstSelected;

    public SelectionSwitch(View tvSwitch1, View tvSwitch2) {
        this.tvSwitch1 = tvSwitch1;
        this.tvSwitch2 = tvSwitch2;
        addSwitchListener();
    }

    private void addSwitchListener() {
        if (tvSwitch1 != null) {
            tvSwitch1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelected(true);
                }
            });
        }
        if (tvSwitch2 != null) {
            tvSwitch2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelected(false);
                }
            });
        }
    }

    public SelectionSwitch setSelected(boolean isFirstSelected) {
        this.isFirstSelected = isFirstSelected;
        if(isFirstSelected){
            if (tvSwitch1 != null) {
                tvSwitch1.setSelected(true);
            }
            if (tvSwitch2 != null) {
                tvSwitch2.setSelected(false);
            }
        }else {
            if (tvSwitch1 != null) {
                tvSwitch1.setSelected(false);
            }
            if (tvSwitch2 != null) {
                tvSwitch2.setSelected(true);
            }
        }
        return this;
    }

    public boolean isFirstSelected() {
        return isFirstSelected;
    }

    public boolean isSecondSelected() {
        return !isFirstSelected;
    }

    public void setFirstSelected(boolean firstSelected) {
        isFirstSelected = firstSelected;
    }

    public void setSecondSelected(boolean secondSelected) {
        isFirstSelected = !secondSelected;
    }
}
