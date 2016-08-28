package com.tgimba.tgimba_android.servicecall;

import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.misc.Types;
import com.tgimba.tgimba_android.misc.Utilities;

public class LoginServiceCall extends BaseServiceCall
{
    private String userName = "";

    public LoginServiceCall(MainActivity pMa,
                            String pUrl,
                            String pUserName)
    {
        super(pMa, pUrl);
        userName = pUserName;
    }

    @Override
    protected void onPostExecute(String token)
    {
        if (token != null && !token.equals("") && !token.equals("\"\""))
        {
            this.ma.SetUserName(this.userName);
            this.ma.SetGui(Types.GuiStates.LoggedIn, token, null);
        }
        else
            Utilities.DisplayMsg(ma, "Login failed");
    }
}
