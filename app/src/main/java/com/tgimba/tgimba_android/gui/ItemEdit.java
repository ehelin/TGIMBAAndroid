package com.tgimba.tgimba_android.gui;

import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.data.BucketItemList;
import com.tgimba.tgimba_android.data.EnumRanking;
import com.tgimba.tgimba_android.misc.Constants;
import com.tgimba.tgimba_android.misc.Types;
import com.tgimba.tgimba_android.misc.Utilities;
import com.tgimba.tgimba_android.servicecall.UpsertBucketListItemCall;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemEdit extends Gui
{
    private BucketItemList item = null;
    private boolean add = false;
    private String userName = null;
    private String token = null;
    private EditText itemName = null;
    private Date entered = null;
    Spinner dropdown = null;
    CheckBox cb = null;

    public ItemEdit(MainActivity pMa, BucketItemList pItem, boolean pAdd, String pUserName, String pToken) {
        super(pMa);
        item = pItem;
        add = pAdd;
        userName = pUserName;
        token = pToken;
    }

    protected LinearLayout BuildBodyPanel() {
        LinearLayout body = GetBasicPanel();

        if (item == null)
        {
            body.addView(buildItemScreen(Constants.NAME, Constants.ITEM_NAME_EDIT_TEXTBOX_ID, "", true));

            body.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, " ", 10, 25));

            entered = new Date();
            body.addView(buildItemScreen(Constants.DATE, Constants.ITEM_DATE_EDIT_TEXTBOX_ID, entered.toString(), false));

            body.addView(buildRankingSelection(null));
            body.addView(buildAchieved(false));
        }
        else
        {
            body.addView(buildItemScreen(Constants.NAME, Constants.ITEM_NAME_EDIT_TEXTBOX_ID, item.Name, true));

            body.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, " ", 10, 25));

            entered = item.Entered;
            body.addView(buildItemScreen(Constants.DATE, Constants.ITEM_DATE_EDIT_TEXTBOX_ID, entered.toString(), false));

            body.addView(buildRankingSelection(item.Ranking));
            body.addView(buildAchieved(item.Achieved));
        }

        body.addView(buildButtons(this.add));

        return body;
    }

    protected LinearLayout buildRankingSelection(EnumRanking ranking)
    {
        LinearLayout dropDownlist = new LinearLayout(ma);

        dropDownlist.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        dropDownlist.setOrientation(LinearLayout.HORIZONTAL);
        dropDownlist.setHorizontalGravity(Gravity.CENTER);
        dropDownlist.setMinimumWidth(sc.width - 30);

        dropdown = new Spinner(ma);
        String[] items = new String[]{EnumRanking.Cool.Hot.toString(),
                                      EnumRanking.Cool.Warm.toString(),
                                      EnumRanking.Cool.Cool.toString()};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ma, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropDownlist.addView(dropdown);

        if (ranking == EnumRanking.Cool.Hot)
            dropdown.setSelection(0);
        else if (ranking == EnumRanking.Cool.Warm)
            dropdown.setSelection(1);
        else
            dropdown.setSelection(2);

        return dropDownlist;
    }

    protected LinearLayout buildAchieved(Boolean acheived) {
        LinearLayout cbView = new LinearLayout(ma);

        cbView.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        cbView.setOrientation(LinearLayout.HORIZONTAL);
        cbView.setHorizontalGravity(Gravity.CENTER);
        cbView.setMinimumWidth(sc.width - 30);

        cb = new CheckBox(ma);
        cb.setText(Constants.ACHIEVED);
        cb.setChecked(acheived);

        cbView.addView(cb);

        return cbView;
    }

    private LinearLayout buildItemScreen(String labelName, int txtBoxId, String txtValue, boolean editable)
    {
        LinearLayout itemScreen = new LinearLayout(ma);
        int rowWidth = sc.width - 30;

        itemScreen.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        itemScreen.setOrientation(LinearLayout.HORIZONTAL);
        itemScreen.setHorizontalGravity(Gravity.CENTER);
        itemScreen.setMinimumWidth(rowWidth);

        int labelWidth = 200;
        int txtBoxWidth = rowWidth - 400;

        itemScreen.addView(GetLabel(Constants.BLACK, 12, labelName, labelWidth, Constants.LABEL_HEIGHT));

        String name = "";
        if (item != null)
            name = item.Name;

        if (editable)
        {
            LinearLayout nameLL = Utilities.GetBasicItemLayout(sc.width - 30, ma);

            //HACK Alert!: To get multi-line names
            itemName = GetTextBoxMultipleLines(Constants.ITEM_NAME_EDIT_TEXTBOX_ID, Constants.WHITE, Constants.BLACK, 1200, 500, Constants.TEXTBOX_FONTSIZE, name, true);
            nameLL.addView(itemName);
            itemScreen.addView(nameLL);
        }
        else
        {
            TextView date = GetLabel(Constants.BLACK, 12, txtValue, 600, Constants.LABEL_HEIGHT);
            itemScreen.addView(date);
        }

       return itemScreen;
    }

    private LinearLayout buildButtons(boolean add)
    {
        LinearLayout btns = new LinearLayout(ma);
        Button btn = null;

        int rowWidth = sc.width - Constants.SCREEN_SUBTRACT_VALUE;

        btns.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        btns.setOrientation(LinearLayout.VERTICAL);
        btns.setHorizontalGravity(Gravity.CENTER);
        btns.setMinimumWidth(rowWidth);

        int labelWidth = 200;
        int txtBoxWidth = rowWidth - 400;

        btns.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, " ", 10, 25));

        if (add)
        {
            btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.ADD, Constants.BUTTON_FONTSIZE);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    ProcessAddItem();
                }
            });
        }
        else {
            btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.EDIT, Constants.BUTTON_FONTSIZE);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    ProcessEditItem();
                }
            });
        }
        btns.addView(btn);

        btns.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, " ",10, 25));

        btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.CANCEL, Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessCancel();
            }
        });
        btns.addView(btn);

        return btns;
    }

    private void Upsert()
    {
        try
        {
            String item = "," + this.itemName.getText().toString();
            item += "," + new SimpleDateFormat(Constants.DATE_MMDDYYYY).format(entered).toString();
            item += "," + this.dropdown.getSelectedItem().toString();

            if (this.cb.isChecked())
                item += Constants.COMMA_ONE;
            else
                item +=Constants.COMMA_ZERO;

            String dbId = Constants.ZERO;
            if (this.item != null && this.item.DbId != null)
                dbId = this.item.DbId;

            item += Constants.COMMA + dbId + Constants.COMMA + userName + Constants.COMMA_SEMICOLON;

            String callUrl = Constants.TGIMBA_BASE_API_URL
                    + Constants.SUB_URL_API_GET_UPSERT
                    + "?encodedBucketListItems=" + Utilities.EncodeStringBase64(item, this.ma)
                    +"&encodedUser=" + Utilities.EncodeStringBase64(userName, this.ma)
                    + "&encodedToken=" + Utilities.EncodeStringBase64(token, this.ma);

            UpsertBucketListItemCall client = new UpsertBucketListItemCall(this.ma, callUrl);
            client.execute("");
        }
        catch (Exception e) {
            Utilities.DisplayMsg(ma, e.getMessage());
        }
    }

    //event handlers ---------------------------------------
    private void ProcessAddItem()
    {
        Upsert();
    }
    private void ProcessEditItem(){
        Upsert();
    }
    private void ProcessCancel(){
        ma.SetGui(Types.GuiStates.Body);
    }
}
