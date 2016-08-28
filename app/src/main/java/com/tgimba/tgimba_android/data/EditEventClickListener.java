package com.tgimba.tgimba_android.data;

import android.view.View;
import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.misc.Types;
import com.tgimba.tgimba_android.misc.Utilities;

public class EditEventClickListener implements View.OnClickListener
{
    private BucketItemList item = null;
    private MainActivity ma = null;

    public EditEventClickListener(BucketItemList pItem,
                                  MainActivity pMa)
    {
        this.item = pItem;
        this.ma = pMa;
    }

    @Override
    public void onClick(View v)
    {
        try
        {
            ma.SetGui(Types.GuiStates.Edit, "", this.item);
        }
        catch (Exception e) {
            Utilities.DisplayMsg(ma, e.getMessage());
        }
    }
}