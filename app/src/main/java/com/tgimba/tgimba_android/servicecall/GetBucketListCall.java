package com.tgimba.tgimba_android.servicecall;

import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.misc.Types;
import com.tgimba.tgimba_android.misc.Utilities;

public class GetBucketListCall extends BaseServiceCall
{
    public GetBucketListCall(MainActivity pMa,
                             String pUrl)
    {
        super(pMa, pUrl);
    }

    @Override
    protected void onPostExecute(String bucketListItems)
    {
        if (bucketListItems != null && bucketListItems.length()> 0)
        {
            this.ma.SetGui(Types.GuiStates.BucketItemQuery, bucketListItems, null);
        }
        else
            Utilities.DisplayMsg(ma, "Login failed");
    }
}
