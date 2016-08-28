package com.tgimba.tgimba_android.gui;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.EditText;
import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.data.BucketItemList;
import com.tgimba.tgimba_android.data.DeleteEventClickListener;
import com.tgimba.tgimba_android.data.EditEventClickListener;
import com.tgimba.tgimba_android.data.EnumRanking;
import com.tgimba.tgimba_android.misc.Constants;
import com.tgimba.tgimba_android.misc.Types;
import com.tgimba.tgimba_android.misc.Utilities;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ItemList extends Gui {
    private String items = null;
    private ArrayList<BucketItemList> itemsListed = null;
    private EditText srchTerm = null;
    private String userName = null;
    private String token = null;

    public ItemList(MainActivity pMa,
                    String pItems,
                    String pUserName,
                    String pToken) {
        super(pMa);
        items = pItems;
        userName = pUserName;
        token = pToken;

        items = items.replace("[","");
        items = items.replace("]","");
        items = items.replace("\",\"",";");
        items = items.replace("\"","");
    }

    public ArrayList<BucketItemList> GetItemsListed()
    {
        return itemsListed;
    }

    protected LinearLayout BuildBodyPanel() {
        LinearLayout body = GetBasicPanel();

        itemsListed = GetItems();

        body.addView(buildMenuSearch());
        body.addView(buildItemListHeader());
        body.addView(buildItemList(itemsListed));

        return body;
    }

    private Date GetDate(String val) {
        Date date = null;

        try
        {
            date = new SimpleDateFormat(Constants.DATE_MMDDYYYY, Locale.ENGLISH).parse(val);
        }
        catch (ParseException e)
        {
            Utilities.DisplayMsg(ma, e.getMessage());
        }

        return date;
    }

    private EnumRanking GetRanking(String val) {
        EnumRanking ranking = EnumRanking.Cool;

        if (val.equals(EnumRanking.Hot.toString()))
            ranking = EnumRanking.Hot;
        else if (val.equals(EnumRanking.Warm.toString()))
            ranking = EnumRanking.Warm;

        return ranking;
    }

    private Boolean GetAcheived(String val) {
        Boolean acheived = false;

        if (val.equals(Constants.ONE))
            acheived = true;

        return acheived;
    }

    private ArrayList<BucketItemList> GetItems() {
        ArrayList<BucketItemList> itemsList = new ArrayList<BucketItemList>();
        String[] itemsArr = items.split(";");

        if (itemsArr != null) {
            if (!itemsArr[0].equals(Constants.NO_ITEMS)) {
                for (int i = 0; i < itemsArr.length; i++) {
                    String[] itemArr = itemsArr[i].split(",");

                    String name = itemArr[1];
                    Date date = GetDate(itemArr[2]);
                    EnumRanking ranking = GetRanking(itemArr[3]);
                    Boolean achieved = GetAcheived(itemArr[4]);
                    String dbId = itemArr[5];

                    BucketItemList bil = new BucketItemList(name,
                            date,
                            ranking,
                            achieved,
                            dbId);

                    itemsList.add(bil);
                }
            }
        }

        return itemsList;
    }

    private LinearLayout buildMenuSearch() {
        LinearLayout mnuSearch = new LinearLayout(ma);

        mnuSearch.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        mnuSearch.setOrientation(LinearLayout.HORIZONTAL);
        mnuSearch.setHorizontalGravity(Gravity.FILL_HORIZONTAL);
        mnuSearch.setMinimumWidth(sc.width - Constants.SCREEN_SUBTRACT_VALUE);

        LinearLayout mnuBtn = new LinearLayout(ma);
        mnuBtn.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        mnuBtn.setOrientation(LinearLayout.HORIZONTAL);
        mnuBtn.setHorizontalGravity(Gravity.FILL_HORIZONTAL);

        Button btn = GetButton(Constants.GREEN, Constants.WHITE, Constants.BUTTON_WIDTH, 150, "Menu", Constants.BUTTON_FONTSIZE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessMenu();
            }
        });
        mnuBtn.addView(btn);
        mnuSearch.addView(mnuBtn);

        LinearLayout srchBtnTxt = new LinearLayout(ma);
        srchBtnTxt.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        srchBtnTxt.setOrientation(LinearLayout.HORIZONTAL);
        srchBtnTxt.setHorizontalGravity(Gravity.RIGHT);
        srchBtnTxt.setMinimumWidth(1100);

        srchTerm = GetTextBox(Constants.SEARCH_TEXTBOX_ID, Constants.WHITE, Constants.BLACK, Constants.TEXTBOX_WIDTH, 150, Constants.TEXTBOX_FONTSIZE, false, "", true);
        srchTerm.setGravity(Gravity.LEFT);
        srchBtnTxt.addView(srchTerm);

        srchBtnTxt.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, "  ", 10, Constants.LABEL_HEIGHT + 20));

        ImageButton btn2 = GetImageButton(Constants.BLACK, Constants.BLACK, 120, 120, Constants.SEARCH, Constants.BUTTON_FONTSIZE);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ProcessSearch();
            }
        });
        srchBtnTxt.addView(btn2);
        mnuSearch.addView(srchBtnTxt);

        return mnuSearch;
    }

    private LinearLayout buildItemListHeader()
    {
        LinearLayout itemList = new LinearLayout(ma);

        itemList.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        itemList.setOrientation(LinearLayout.VERTICAL);
        itemList.setHorizontalGravity(Gravity.LEFT);
        itemList.setMinimumWidth(sc.width - Constants.SCREEN_SUBTRACT_VALUE);

        itemList.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, "", Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT));
        itemList.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, Constants.MY_BUCKET_LIST, sc.width - Constants.SCREEN_SUBTRACT_VALUE, Constants.LABEL_HEIGHT + 20));
        itemList.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, "", Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT));

        return itemList;
    }

    private ScrollView buildItemList(ArrayList<BucketItemList> items) {
        ScrollView sv = new ScrollView(ma);
        LinearLayout itemBg = GetBasicPanel();
        int ctr = 1;
        int screenWidth = sc.width - Constants.SCREEN_SUBTRACT_VALUE;

        //HACK Alert! - refactor
        if (items == null || items.size() <= 0)
        {
            LinearLayout itemList = Utilities.GetBasicItemLayout(screenWidth, ma);

            //HACK Alert!: To get multi-line names
            LinearLayout nameLL = Utilities.GetBasicItemLayout(screenWidth - 20, ma);
            String name = Constants.NO_RESULTS;
            nameLL.addView(GetLabelMultipleLines(Constants.BLACK, Constants.WHITE, Constants.LABEL_FONTSIZE, name, screenWidth - 20, Constants.LABEL_HEIGHT + 20, false));
            itemList.addView(nameLL);

            itemBg.addView(itemList);
        }
        else {
            for (BucketItemList item : items) {
                LinearLayout itemList = Utilities.GetBasicItemLayout(screenWidth, ma);

                //HACK Alert!: To get multi-line names
                LinearLayout nameLL = Utilities.GetBasicItemLayout(screenWidth - 500, ma);
                String name = Integer.toString(ctr) + " - " + item.Name;
                nameLL.addView(GetLabelMultipleLines(Constants.BLACK, Constants.WHITE, Constants.LABEL_FONTSIZE, name, screenWidth-330, Constants.LABEL_HEIGHT + 20, true));
                itemList.addView(nameLL);

                itemList.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, "  ", 10, Constants.LABEL_HEIGHT + 20));
                itemList.addView(GetLabelRanking(Constants.BLACK, Constants.LABEL_FONTSIZE, item.Ranking.toString(), 60, Constants.LABEL_HEIGHT + 20));
                itemList.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, "  ", 10, Constants.LABEL_HEIGHT + 20));

                ImageButton btn = GetImageButton(Constants.GREEN, Constants.WHITE, 100, 150, Constants.E, Constants.BUTTON_FONTSIZE);
                btn.setOnClickListener(new EditEventClickListener(item, this.ma));
                itemList.addView(btn);
                itemList.addView(GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, "  ", 10, Constants.LABEL_HEIGHT));

                btn = GetImageButton(Constants.GREEN, Constants.WHITE, 100, 150, Constants.D, Constants.BUTTON_FONTSIZE);
                btn.setOnClickListener(new DeleteEventClickListener(item.DbId, this.ma, this.userName, this.token));

                itemList.addView(btn);

                ctr++;

                //add space at end of list
                if (ctr == items.size()+1)
                    itemList.setMinimumHeight(itemList.getHeight()+400);

                itemBg.addView(itemList);
            }
        }

        sv.addView(itemBg);

        return sv;
    }

    //event handlers -------------------------------------
    private void ProcessExit(){
        ma.SetGui(Types.GuiStates.LoginPanel);
    }

    private void ProcessMenu(){
        ma.SetGui(Types.GuiStates.Menu);
    }

    private void ProcessSearch(){
        String srchTerm = this.srchTerm.getText().toString();

        if (srchTerm != null && srchTerm.length()>0)
            ma.SetGui(Types.GuiStates.SearchQuery, srchTerm, null);
    }

    private void ProcessItemEdit(){
        ma.SetGui(Types.GuiStates.Edit);
    }
}
