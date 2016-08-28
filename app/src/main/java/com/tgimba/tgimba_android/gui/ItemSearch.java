package com.tgimba.tgimba_android.gui;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.data.BucketItemList;
import com.tgimba.tgimba_android.data.DeleteEventClickListener;
import com.tgimba.tgimba_android.data.EditEventClickListener;
import com.tgimba.tgimba_android.misc.Constants;
import com.tgimba.tgimba_android.misc.Types;
import com.tgimba.tgimba_android.misc.Utilities;
import java.util.ArrayList;

public class ItemSearch extends Gui {
    private ArrayList<BucketItemList> itemsListed = null;
    private String srchTerm = null;
    private String userName = null;
    private String token = null;

    public ItemSearch(MainActivity pMa,
                      ArrayList<BucketItemList> pItemsListed,
                      String pSrchTerm,
                      String pUserName,
                      String pToken) {
        super(pMa);

        userName = pUserName;
        token = pToken;
        itemsListed = pItemsListed;
        srchTerm = pSrchTerm;
    }

    protected LinearLayout BuildBodyPanel() {
        LinearLayout body = GetBasicPanel();

        ArrayList<BucketItemList> items = PerformSearch();

        body.addView(buildItemListHeader());
        body.addView(buildItemList(items));

        return body;
    }

    private ArrayList<BucketItemList> PerformSearch()
    {
        ArrayList<BucketItemList> srchResults = new ArrayList<BucketItemList>();

        for(BucketItemList item : itemsListed)
        {
            if (item.Name.toLowerCase().indexOf(this.srchTerm.toLowerCase()) != -1)
            {
                srchResults.add(item);
            }
        }

        return srchResults;
    }

    private LinearLayout buildItemListHeader() {
        LinearLayout itemList = new LinearLayout(ma);

        itemList.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        itemList.setOrientation(LinearLayout.VERTICAL);
        itemList.setHorizontalGravity(Gravity.LEFT);
        itemList.setMinimumWidth(TotalWidth);

        itemList.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, "", 10, Constants.LABEL_HEIGHT));
        itemList.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, Constants.SEARCH_RESULTS, sc.width - Constants.SCREEN_SUBTRACT_VALUE, Constants.LABEL_HEIGHT));
        itemList.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, "", 10, Constants.LABEL_HEIGHT));

        return itemList;
    }

    private LinearLayout buildItemList(ArrayList<BucketItemList> items) {
        LinearLayout itemBg = GetBasicPanel();
        int searchListBtnWidth = 50;
        int itemWidth = TotalWidth - (searchListBtnWidth * 2);

        for(BucketItemList item : items) {
            LinearLayout itemList = new LinearLayout(ma);

            itemList.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
            itemList.setOrientation(LinearLayout.HORIZONTAL);
            itemList.setHorizontalGravity(Gravity.LEFT);
            itemList.setMinimumWidth(TotalWidth);

            ImageButton btn = GetImageButton(Constants.GREEN, Constants.WHITE, 100, 150, Constants.E, Constants.BUTTON_FONTSIZE);
            btn.setOnClickListener(new EditEventClickListener(item, this.ma));
            itemList.addView(btn);
            itemList.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, "  ", 10, Constants.LABEL_HEIGHT));

            btn = GetImageButton(Constants.GREEN, Constants.WHITE, 100, 150, Constants.D, Constants.BUTTON_FONTSIZE);
            btn.setOnClickListener(new DeleteEventClickListener(item.DbId, this.ma, this.userName, this.token));
            itemList.addView(btn);

            itemList.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, "  ", 10, Constants.LABEL_HEIGHT + 20));

            //HACK Alert!: To get multi-line names
            LinearLayout nameLL = Utilities.GetBasicItemLayout(itemWidth, ma);
            nameLL.addView(GetLabelMultipleLines(Constants.BLACK, Constants.WHITE, Constants.LABEL_FONTSIZE, item.Name, itemWidth, Constants.LABEL_HEIGHT + 20, true));
            itemList.addView(nameLL);

            itemBg.addView(itemList);
        }

        itemBg.addView(buildFooter());

        return itemBg;
    }

    private LinearLayout AddItem(String itemName, int itemWidth)
    {
        LinearLayout item = new LinearLayout(ma);

        item.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        item.setOrientation(LinearLayout.HORIZONTAL);
        item.setHorizontalGravity(Gravity.LEFT);
        item.setMinimumWidth(itemWidth);

        TextView itemValue = GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, itemName, itemWidth, Constants.LABEL_HEIGHT);

        itemValue.setGravity(Gravity.LEFT);
        item.addView(itemValue);
        return item;
    }

    private LinearLayout buildFooter(){
        LinearLayout btnBgrnd = new LinearLayout(ma);

        btnBgrnd.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        btnBgrnd.setOrientation(LinearLayout.VERTICAL);
        btnBgrnd.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

        btnBgrnd.addView(GetLabel(Constants.BLACK, 12, "", Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT));
        Button btn = GetButton(Constants.GREEN, Constants.WHITE, 300, 150, Constants.CANCEL, 12);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessExit();
            }
        });
        btnBgrnd.addView(btn);

        return btnBgrnd;
    }

    //event handlers -------------------------------------
    private void ProcessExit(){
        ma.SetGui(Types.GuiStates.Body);
    }
    private void ProcessItemEdit()    {
        ma.SetGui(Types.GuiStates.Edit);
    }
}
