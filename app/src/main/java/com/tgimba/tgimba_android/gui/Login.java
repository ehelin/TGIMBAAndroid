package com.tgimba.tgimba_android.gui;

import com.tgimba.tgimba_android.misc.Constants;
import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.misc.Types;
import com.tgimba.tgimba_android.misc.Utilities;
import com.tgimba.tgimba_android.servicecall.LoginServiceCall;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class Login extends Gui{
    private EditText usrNameET = null;
    private EditText psdWrdET = null;

    public Login(MainActivity pMa) {
        super(pMa);
    }

    protected LinearLayout BuildBodyPanel() {
        LinearLayout loginPanel = GetBasicPanel();

        loginPanel.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, "User Name", Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT));
        usrNameET = GetTextBox(Constants.LOGIN_USERNAME_TEXTBOX_ID, Constants.WHITE, Constants.BLACK, 1200, Constants.TEXTBOX_HEIGHT, Constants.TEXTBOX_FONTSIZE, false, "", true);
        loginPanel.addView(usrNameET);

        loginPanel.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, "Password", Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT));
        psdWrdET = GetTextBox(Constants.LOGIN_PASSWORD_TEXTBOX_ID, Constants.WHITE, Constants.BLACK, 1200, Constants.TEXTBOX_HEIGHT, Constants.TEXTBOX_FONTSIZE, true, "", true);
        loginPanel.addView(psdWrdET);

        loginPanel.addView(GetLabelSpacer());

        Button btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, "Submit", Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                ProcessLogin();
            }
        });
        loginPanel.addView(btn);

        loginPanel.addView(GetLabelSpacer());

        btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, "Register", Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                ShowRegistrationPanel();
            }
        });
        loginPanel.addView(btn);

        return loginPanel;
    }

    //event handlers -------------------------------------
    private void ShowRegistrationPanel(){
        ma.SetGui(Types.GuiStates.RegisterPanel);
    }

    private void ProcessLogin(){
        LoginServiceCall();
    }

    private void LoginServiceCall()
    {
        try
        {
            String baseUser = this.usrNameET.getText().toString();
            String basePass = this.psdWrdET.getText().toString();

            String callUrl = Constants.TGIMBA_BASE_API_URL
                    + Constants.SUB_URL_API_ACCOUNT
                    + "?encodedUser=" + Utilities.EncodeStringBase64(baseUser, this.ma)
                    + "&encodedPass=" + Utilities.EncodeStringBase64(basePass, this.ma);

            LoginServiceCall client = new LoginServiceCall(this.ma, callUrl, baseUser);
            client.execute("");
        }
        catch (Exception e) {
            Utilities.DisplayMsg(ma, e.getMessage());
        }
    }
}
