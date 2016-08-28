package com.tgimba.tgimba_android.gui;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.misc.Constants;
import com.tgimba.tgimba_android.misc.Types;

public class SortMenu extends Gui{
    private CheckBox checkBox = null;

    public SortMenu(MainActivity pMa) {
        super(pMa);
    }

    protected LinearLayout BuildBodyPanel() {
        LinearLayout body = GetBasicPanel();

        Button btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.SORT_TITLE, Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessSortTitle();
            }
        });
        body.addView(btn);

        body.addView(GetLabelSpacer());

        btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.SORT_RANKING, Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessSortRanking();
            }
        });
        body.addView(btn);

        body.addView(GetLabelSpacer());

        btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.SORT_ACHIEVED, Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessSortAchieved();
            }
        });
        body.addView(btn);

        body.addView(GetLabelSpacer());

        btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.SORT_ENTERED, Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessSortEntered();
            }
        });
        body.addView(btn);

        body.addView(GetLabelSpacer());

        body.addView(buildSortDescAsecDb(ma.GetSortDirection()));

        body.addView(GetLabelSpacer());

        btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.SORT_CLEAR_SORT, Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessSortClear();
            }
        });
        body.addView(btn);

        body.addView(GetLabelSpacer());

        btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, Constants.SORT_CANCEL, Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessCancel();
            }
        });
        body.addView(btn);

        return body;
    }
    protected LinearLayout buildSortDescAsecDb(Boolean asc) {
        LinearLayout cbView = new LinearLayout(ma);

        cbView.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        cbView.setOrientation(LinearLayout.HORIZONTAL);
        cbView.setHorizontalGravity(Gravity.CENTER);
        cbView.setMinimumWidth(sc.width - 30);

        checkBox = new CheckBox(ma);
        checkBox.setText("Descending");

        if (asc)
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);

        cbView.addView(checkBox);

        return cbView;
    }

    //event handlers -------------------------------------
    private void ProcessSortTitle(){
        LoadSortedList(Constants.SORT_PROCESS_LIST_ITEM_NAME);
    }

    private void ProcessSortRanking(){
        LoadSortedList(Constants.SORT_PROCESS_CATEGORY);
    }

    private void ProcessSortAchieved(){
        LoadSortedList(Constants.SORT_PROCESS_ACHIEVED);
    }

    private void ProcessSortEntered(){
        LoadSortedList(Constants.SORT_PROCESS_CREATED);
    }

    private void ProcessSortClear(){
        ma.SetSortDirection(false);
        ma.SetGui(Types.GuiStates.ReloadBucketItemList);
    }
    private void ProcessCancel(){
        ma.SetGui(Types.GuiStates.ReloadBucketItemList);
    }

    private void ProcessStoreSortDirection()
    {
        boolean set = checkBox.isChecked();
        ma.SetSortDirection(set);
    }

    private void LoadSortedList(String sortCatetory)
    {
        String sortString = "";

        if (sortCatetory != null)
            sortString = " order by " + sortCatetory;

        if (checkBox.isChecked())
            sortString += " desc";

        ProcessStoreSortDirection();
        this.ma.SetGui(Types.GuiStates.ReloadBucketItemListSorted , sortString, null);
    }
}
