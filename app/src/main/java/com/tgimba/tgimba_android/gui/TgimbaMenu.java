package com.tgimba.tgimba_android.gui;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.misc.Constants;
import com.tgimba.tgimba_android.misc.Types;

public class TgimbaMenu extends Gui{
    public TgimbaMenu(MainActivity pMa) {
        super(pMa);
    }

    protected LinearLayout BuildBodyPanel()
    {
        LinearLayout body = GetBasicPanel();

        Button btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.MENU_ADD, Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessAdd();
            }
        });
        body.addView(btn);

        body.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, " ",10, 25));

        btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.MENU_SORT, Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessSort();
            }
        });
        body.addView(btn);

        body.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, " ",10, 25));

        btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.MENU_LOGOUT, Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessLogout();
            }
        });
        body.addView(btn);

        body.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, " ",10, 25));

        btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.MENU_CANCEL, Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessCancel();
            }
        });
        body.addView(btn);

        return body;
    }

    //event handlers -------------------------------------
    private void ProcessAdd(){
        ma.SetGui(Types.GuiStates.Add, null);
    }

    private void ProcessSort(){
        ma.SetGui(Types.GuiStates.SortMenu);
    }

    private void ProcessLogout(){
        ma.SetGui(Types.GuiStates.LoginPanel);
    }

    private void ProcessCancel(){
        ma.SetGui(Types.GuiStates.Body);
    }
}
