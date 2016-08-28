package com.tgimba.tgimba_android.servicecall;

import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.misc.Types;
import com.tgimba.tgimba_android.misc.Utilities;

public class RegistrationServiceCall extends BaseServiceCall
{
    public RegistrationServiceCall(MainActivity pMa,
                                   String pUrl)
    {
        super(pMa, pUrl);
    }

    @Override
    protected void onPostExecute(String result)
    {
        if (result.equals("true"))
        {
            this.ma.SetGui(Types.GuiStates.LoginPanel);
        }
        else
            Utilities.DisplayMsg(ma, "Registration failed");
    }
}
