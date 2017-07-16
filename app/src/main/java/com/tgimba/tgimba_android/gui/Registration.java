package com.tgimba.tgimba_android.gui;

import com.tgimba.tgimba_android.misc.Constants;
import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.misc.Utilities;
import com.tgimba.tgimba_android.servicecall.RegistrationServiceCall;
import com.tgimba.tgimba_android.misc.Types;
import android.widget.LinearLayout;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

public class Registration extends Gui{
    private EditText usrNameET = null;
    private EditText emailET = null;
    private EditText psdWrdET = null;
    private EditText confPsdWrdET = null;

    public Registration(MainActivity pMa) {
        super(pMa);
    }

    protected LinearLayout BuildBodyPanel() {
        LinearLayout rPanel = GetBasicPanel();

        rPanel.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, Constants.REGISTRATION_USER_NAME, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT));
        usrNameET = GetTextBox(Constants.REGISTRATION_USERNAME_TEXTBOX_ID, Constants.WHITE, Constants.BLACK, 1200, Constants.TEXTBOX_HEIGHT, Constants.TEXTBOX_FONTSIZE, false, "", true);
        rPanel.addView(usrNameET);

        rPanel.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, Constants.REGISTRATION_EMAIL, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT));
        emailET = GetTextBox(Constants.REGISTRATION_EMAIL_TEXTBOX_ID, Constants.WHITE, Constants.BLACK, 1200, Constants.TEXTBOX_HEIGHT, Constants.TEXTBOX_FONTSIZE, false, "", true);
        rPanel.addView(emailET);

        rPanel.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, Constants.REGISTRATION_PASSWORD, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT));
        psdWrdET = GetTextBox(Constants.REGISTRATION_PASSWORD_TEXTBOX_ID, Constants.WHITE, Constants.BLACK, 1200, Constants.TEXTBOX_HEIGHT, Constants.TEXTBOX_FONTSIZE, true, "", true);
        rPanel.addView(psdWrdET);

        rPanel.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, Constants.REGISTRATION_CONFIRM_PASSWORD, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT));
        confPsdWrdET = GetTextBox(Constants.REGISTRATION_CONFIRM_PASSWORD_TEXTBOX_ID, Constants.WHITE, Constants.BLACK, 1200, Constants.TEXTBOX_HEIGHT, Constants.TEXTBOX_FONTSIZE, true, "", true);
        rPanel.addView(confPsdWrdET);

        rPanel.addView(GetLabelSpacer());

        Button btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.REGISTRATION_SUBMIT, Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessRegistration();
            }
        });
        rPanel.addView(btn);

        rPanel.addView(GetLabelSpacer());

        btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.REGISTRATION_CANCEL, Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessRegistrationCancel();
            }
        });
        rPanel.addView(btn);

        return rPanel;
    }

    //event handlers -------------------------------------
    private void ProcessRegistrationCancel(){
        ma.SetGui(Types.GuiStates.LoginPanel);
    }

    private void ProcessRegistration(){
        String userName = this.usrNameET.getText().toString();
        String passWord = this.psdWrdET.getText().toString();
        String email = this.emailET.getText().toString();

        String callUrl =  Constants.SUB_URL_API_REGISTRATION
                + "?encodedUser=" + Utilities.EncodeStringBase64(userName, this.ma)
                +"&encodedEmail=" + Utilities.EncodeStringBase64(email, this.ma)
                + "&encodedPass=" + Utilities.EncodeStringBase64(passWord, this.ma);

        RegistrationServiceCall client = new RegistrationServiceCall(this.ma, callUrl);
        client.execute("");
    }
}
