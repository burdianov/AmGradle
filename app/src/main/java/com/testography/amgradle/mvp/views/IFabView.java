package com.testography.amgradle.mvp.views;

import android.view.View;

public interface IFabView {
    void setFab(boolean isVisible, int icon, View.OnClickListener onClickListener);
    void removeFab();
}
