package com.tgimba.tgimba_android.data;

import android.view.View;
import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.misc.Constants;
import com.tgimba.tgimba_android.misc.Utilities;
import com.tgimba.tgimba_android.servicecall.DeleteBucketListItemCall;

public class DeleteEventClickListener implements View.OnClickListener
{
    private String dbId = "";
    private MainActivity ma = null;
    private String userName = "";
    private String token = "";

    public DeleteEventClickListener(String pDbId,
                                    MainActivity pMa,
                                    String pUserName,
                                    String pToken) {
        this.dbId = pDbId;
        this.ma = pMa;
        this.userName = pUserName;
        this.token = pToken;
    }

    @Override
    public void onClick(View v)
    {
        try
        {
            String callUrl = Constants.TGIMBA_BASE_API_URL
                    + Constants.SUB_URL_API_GET_DELETE
                    + "?dbIdStr=" + dbId
                    +"&encodedUser=" + Utilities.EncodeStringBase64(userName, this.ma)
                    + "&encodedToken=" + Utilities.EncodeStringBase64(token, this.ma);

            DeleteBucketListItemCall client = new DeleteBucketListItemCall(this.ma, callUrl);
            client.execute("");
        }
        catch (Exception e) {
            Utilities.DisplayMsg(ma, e.getMessage());
        }
    }
}