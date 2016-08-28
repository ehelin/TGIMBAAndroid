package com.tgimba.tgimba_android.misc;

import android.app.AlertDialog;
import android.util.Base64;
import android.view.Gravity;
import android.widget.LinearLayout;
import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.data.BucketItemList;
import com.tgimba.tgimba_android.data.EnumRanking;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

public class Utilities {

    public static String EncodeStringBase64(String value, MainActivity ma)
    {
        String base64Value = null;

        try
        {
            //TODO - figure out why this version won't work
            //byte[] data = Base64.encode(value.getBytes(), Base64.DEFAULT);
            //base64Value = new String(data);

            byte[] bytesEncoded = org.apache.commons.codec.binary.Base64.encodeBase64(value.getBytes());
            base64Value = new String(bytesEncoded);
        }
        catch(Exception e)
        {
            Utilities.DisplayMsg(ma, e.getMessage());
        }

        return base64Value;
    }
    public static String DecodeStringBase64(String base64Value, MainActivity ma)
    {
        String value = null;

        try
        {
            byte[] data = Base64.decode(base64Value, Base64.DEFAULT);
            value = new String(data, "UTF-8");
        }
        catch(UnsupportedEncodingException e)
        {
            Utilities.DisplayMsg(ma, e.getMessage());
        }

        return value;
    }

    public static ArrayList<BucketItemList> getTestingBucketList(){
        ArrayList<BucketItemList> items = new ArrayList<BucketItemList>();
        int ctr = 0;

        while(ctr < 5)
        {
            BucketItemList bil = new BucketItemList("Item " + Integer.toString(ctr),
                    new Date(),
                    EnumRanking.Warm,
                    false, "1");
            items.add(bil);

            ctr++;
        }

        return items;
    }

    public static void DisplayMsg(MainActivity ma, String msg)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(ma);

        dlgAlert.setMessage(msg);
        dlgAlert.setTitle("TGIMBA");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public static LinearLayout GetBasicItemLayout(int screenWidth, MainActivity ma)
    {
        LinearLayout itemList = new LinearLayout(ma);

        itemList.setOrientation(LinearLayout.HORIZONTAL);
        itemList.setHorizontalGravity(Gravity.LEFT);
        itemList.setMinimumWidth(screenWidth);

        return itemList;
    }
}
