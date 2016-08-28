package com.tgimba.tgimba_android.servicecall;

import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.misc.Types;
import com.tgimba.tgimba_android.misc.Utilities;

public class DeleteBucketListItemCall extends BaseServiceCall
{
    public DeleteBucketListItemCall(MainActivity pMa,
                                    String pUrl)
    {
        super(pMa, pUrl);
    }

    @Override
    protected void onPostExecute(String bucketListItems)
    {
        if (bucketListItems != null)
        {
            this.ma.SetGui(Types.GuiStates.ReloadBucketItemList , "", null);
        }
        else
            Utilities.DisplayMsg(ma, "Delete failed");
    }
}
