package com.tgimba.tgimba_android.gui;

import com.tgimba.tgimba_android.MainActivity;
import android.util.DisplayMetrics;

public class ScreenDim {
    public int height = 0;
    public int width = 0;
    private MainActivity mainClass = null;

    public ScreenDim(MainActivity pMainClass)
    {
        mainClass = pMainClass;

        setMembers();
    }

    private void setMembers()
    {
        DisplayMetrics dm = new DisplayMetrics();
        mainClass.getWindowManager().getDefaultDisplay().getMetrics(dm);
        height = dm.heightPixels;
        width = dm.widthPixels;
    }
}