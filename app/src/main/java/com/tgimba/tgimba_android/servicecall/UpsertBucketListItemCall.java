package com.tgimba.tgimba_android.servicecall;

import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.misc.Types;
import com.tgimba.tgimba_android.misc.Utilities;

public class UpsertBucketListItemCall extends BaseServiceCall
{
    public UpsertBucketListItemCall(MainActivity pMa,
                                    String pUrl)
    {
        super(pMa, pUrl);
    }

    @Override
    protected void onPostExecute(String bucketListItems)
    {
        //TODO - better test
        if (bucketListItems != null)
        {
            this.ma.SetGui(Types.GuiStates.ReloadBucketItemList , "", null);
        }
        else
            Utilities.DisplayMsg(ma, "Insert failed");
    }
}
