package com.tgimba.tgimba_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import com.tgimba.tgimba_android.data.BucketItemList;
import com.tgimba.tgimba_android.gui.ItemEdit;
import com.tgimba.tgimba_android.gui.ItemList;
import com.tgimba.tgimba_android.gui.Registration;
import com.tgimba.tgimba_android.gui.ScreenDim;
import com.tgimba.tgimba_android.gui.ItemSearch;
import com.tgimba.tgimba_android.gui.TgimbaMenu;
import com.tgimba.tgimba_android.misc.Constants;
import com.tgimba.tgimba_android.misc.Types;
import com.tgimba.tgimba_android.gui.Login;
import com.tgimba.tgimba_android.gui.SortMenu;
import com.tgimba.tgimba_android.misc.Utilities;
import com.tgimba.tgimba_android.servicecall.GetBucketListCall;

public class MainActivity extends AppCompatActivity {
    private Types.GuiStates guiState = Types.GuiStates.NotLoggedIn;
    private LinearLayout body = null;
    private Boolean sortAsc = false;
    private ItemList il = null;
    private String userName = "";
    private String token = "";
    private String srchTerm = "";
    private String sortString = "";
    private BucketItemList item = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.SetGui(Types.GuiStates.LoginPanel);
    }

    public int GetR(int index)
    {
        int id = 0;

        if (index == Constants.IMAGE_CLEAR_FILTER)
            id = R.drawable.clear_filter;
        else if (index == Constants.IMAGE_COLD)
            id = R.drawable.cold;
        else if (index == Constants.IMAGE_COLD_FILTER)
            id = R.drawable.cold_filter;
        else if (index == Constants.IMAGE_COLD_MOBILE)
            id = R.drawable.cold_mobile;
        else if (index == Constants.IMAGE_DELETE_32)
            id = R.drawable.delete32;
        else if (index == Constants.IMAGE_EARTH)
            id = R.drawable.earth;
        else if (index == Constants.IMAGE_HOT)
            id = R.drawable.hot;
        else if (index == Constants.IMAGE_HOT_FILTER)
            id = R.drawable.hot_filter;
        else if (index == Constants.IMAGE_HOT_MOBILE)
            id = R.drawable.hot_mobile;
        else if (index == Constants.IMAGE_PENCIL_32)
            id = R.drawable.pencil32;
        else if (index == Constants.IMAGE_SEARCH)
            id = R.drawable.search;
        else if (index == Constants.IMAGE_SEARCH_24)
            id = R.drawable.search24;
        else if (index == Constants.IMAGE_SEARCH_V2)
            id = R.drawable.searchv2;
        else if (index == Constants.IMAGE_WARM)
            id = R.drawable.warm;
        else if (index == Constants.IMAGE_WARM_FILTER)
            id = R.drawable.warm_filter;
        else if (index == Constants.IMAGE_WARM_MOBILE)
            id = R.drawable.warm_mobile;

        return id;
    }

    public boolean GetSortDirection()
    {
        return sortAsc;
    }

    public void SetSortDirection(boolean up) {
        if (up)
            sortAsc = true;
        else
            sortAsc = false;
    }

    public void SetGui(Types.GuiStates guiType, BucketItemList pItem){
        SetGui(guiType, "", pItem);
    }

    public void SetGui(Types.GuiStates guiType){
        SetGui(guiType, "", null);
    }

    public void SetUserName(String pUserName)
    {
        userName = pUserName;
    }

    public void SetGui(Types.GuiStates guiType, String result, BucketItemList pItem)
    {
        sortString = "";
        srchTerm = "";

        if (guiType == Types.GuiStates.LoginPanel) {
            userName = result;
        }
        else if (guiType == Types.GuiStates.LoggedIn) {
            token = result;
            sortString = "";
            String url = Constants.TGIMBA_BASE_API_URL
                            + Constants.SUB_URL_API_GET_BUCKETLIST
                                + "?encodedUserName=" + Utilities.EncodeStringBase64(userName, this)
                                +"&encodedSortString=" + Utilities.EncodeStringBase64(sortString, this)
                                + "&encodedToken=" + Utilities.EncodeStringBase64(token, this);
            GetBucketListCall client = new GetBucketListCall(this, url);
            client.execute("");
        }
        else if (guiType == Types.GuiStates.ReloadBucketItemListSorted)
        {
            sortString = result;
            String url = Constants.TGIMBA_BASE_API_URL
                    + Constants.SUB_URL_API_GET_BUCKETLIST
                    + "?encodedUserName=" + Utilities.EncodeStringBase64(userName, this)
                    +"&encodedSortString=" + Utilities.EncodeStringBase64(sortString, this)
                    + "&encodedToken=" + Utilities.EncodeStringBase64(token, this);
            GetBucketListCall client = new GetBucketListCall(this, url);
            client.execute("");
        }
        else if (guiType == Types.GuiStates.ReloadBucketItemList)
        {
            String url = Constants.TGIMBA_BASE_API_URL
                    + Constants.SUB_URL_API_GET_BUCKETLIST
                    + "?encodedUserName=" + Utilities.EncodeStringBase64(userName, this)
                    +"&encodedSortString=" + Utilities.EncodeStringBase64(sortString, this)
                    + "&encodedToken=" + Utilities.EncodeStringBase64(token, this);
            GetBucketListCall client = new GetBucketListCall(this, url);
            client.execute("");
        }
        else if (guiType == Types.GuiStates.BucketItemQuery)
        {
            il = new ItemList(this, result, userName, token);
            guiType = Types.GuiStates.Body;
        }
        else if (guiType == Types.GuiStates.SearchQuery) {
            srchTerm = result;
        }
        else if (guiType == Types.GuiStates.Add)
        {
            item = pItem;
        }
        else if (guiType == Types.GuiStates.Edit)
        {
            item = pItem;
        }

        this.guiState = guiType;
        buildGui();
    }

    private void preBuildGui()
    {
        if (body == null) {
            body = SetBody();
        }

        if(body.getChildCount() > 0){
            body.removeAllViews();
        }
    }

    private void buildGui() {
        preBuildGui();

        if (this.guiState == Types.GuiStates.LoginPanel) {
            Login l = new Login(this);
            body.addView(l.GetBody());
        }
        else if (this.guiState == Types.GuiStates.RegisterPanel) {
            Registration r = new Registration(this);
            body.addView(r.GetBody());
        }
        else if (this.guiState == Types.GuiStates.Body){
            if (il != null)
                body.addView(il.GetBody());
            else {
                this.guiState = Types.GuiStates.LoginPanel;
                buildGui();
            }
        }
        else if (this.guiState == Types.GuiStates.Menu){
            TgimbaMenu m = new TgimbaMenu(this);
            body.addView(m.GetBody());
        }
        else if (this.guiState == Types.GuiStates.SortMenu) {
            SortMenu m = new SortMenu(this);
            body.addView(m.GetBody());
        }
        else if (this.guiState == Types.GuiStates.Add) {
            ItemEdit ie = new ItemEdit(this, item, true, this.userName, this.token);
            body.addView(ie.GetBody());
        }
        else if (this.guiState == Types.GuiStates.Edit) {
            ItemEdit ie = new ItemEdit(this, item, false, this.userName, this.token);
            body.addView(ie.GetBody());
        }
        else if (this.guiState == Types.GuiStates.SearchQuery) {
            ItemSearch sr = new ItemSearch(this, il.GetItemsListed(), srchTerm, this.userName, this.token);
            body.addView(sr.GetBody());
        }

        this.setContentView(body);
    }

    private LinearLayout SetBody(){
        ScreenDim sd = new ScreenDim(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sd.width, sd.height);
        LinearLayout body = new LinearLayout(this);

        return body;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
