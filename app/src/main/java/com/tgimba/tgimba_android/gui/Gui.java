package com.tgimba.tgimba_android.gui;

import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.tgimba.tgimba_android.MainActivity;
import com.tgimba.tgimba_android.data.EnumRanking;
import com.tgimba.tgimba_android.misc.Constants;

public class Gui
{
    protected MainActivity ma = null;
    protected ScreenDim sc = null;
    protected int TotalWidth = 0;

    protected Gui(MainActivity pMa){
        ma = pMa;
        sc = new ScreenDim(ma);
        TotalWidth = sc.width - Constants.SCREEN_SUBTRACT_VALUE;
    }

    public LinearLayout GetBody() {
        LinearLayout backGround = GetBackground();

        backGround.addView(BuildBodyPanel());

        return backGround;
    }

    protected LinearLayout BuildBodyPanel()
    {
        return null;
    }

    //Gui sections -----------------------------------------
    protected TextView GetLabelSpacer(){
        TextView spacerLbl = GetLabel(Constants.BLACK, Constants.LABEL_FONTSIZE, " ", 10, 25);

        return spacerLbl;
    }

    protected ScrollView GetScrollBasicPanel(){
        ScrollView sv = new ScrollView(ma);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(this.sc.width-40, this.sc.height-20);
        LinearLayout panel = new LinearLayout(ma);
        panel.setLayoutParams(params);

        panel.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

        sv.addView(panel);

        return sv;
    }

    protected LinearLayout GetBasicPanel(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(this.sc.width-40, this.sc.height-20);
        LinearLayout panel = new LinearLayout(ma);
        panel.setLayoutParams(params);

        panel.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

        return panel;
    }

    protected LinearLayout GetBackground() {
        LinearLayout backGround = new LinearLayout(ma);

        backGround.setBackgroundColor(android.graphics.Color.parseColor(Constants.WHITE));
        backGround.setOrientation(LinearLayout.VERTICAL);
        backGround.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        backGround.setMinimumHeight(sc.height - 20);
        backGround.setMinimumWidth(sc.width - 20);

        return backGround;
    }

    protected Button GetButton(String bgColor,
                               String fgColor,
                               int width,
                               int height,
                               String txt,
                               int fontSize){
        Button btn = new Button(ma);
        btn.setBackgroundColor(android.graphics.Color.parseColor(bgColor));
        btn.setTextColor(android.graphics.Color.parseColor(fgColor));
        btn.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        btn.setText(txt);
        btn.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, fontSize);

        return btn;
    }

    protected ImageButton GetImageButton(String bgColor,
                                                String fgColor,
                                                int width,
                                                int height,
                                                String txt,
                                                int fontSize){
        ImageButton btn = new ImageButton(ma);
        btn.setBackgroundColor(android.graphics.Color.parseColor(bgColor));

        if (txt.equals(Constants.E))
            btn.setImageResource(this.ma.GetR(Constants.IMAGE_PENCIL_32));
        else if (txt.equals(Constants.D))
            btn.setImageResource(this.ma.GetR(Constants.IMAGE_DELETE_32));
        else if (txt.equals(Constants.SEARCH))
            btn.setImageResource(this.ma.GetR(Constants.IMAGE_SEARCH_V2));

        btn.setLayoutParams(new LinearLayout.LayoutParams(width, height));

        return btn;
    }

    protected TextView GetLabelMultipleLines(String fgColor,
                                              String bgColor,
                                              int fontSize,
                                              String txt,
                                              int width,
                                              int height,
                                              boolean leftAlign)
    {
        TextView tv = new TextView(ma);

        tv.setLines(2);
        tv.setMaxLines(2);
        tv.setWidth(width);
        if (leftAlign)
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        else
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        tv.setTextColor(android.graphics.Color.parseColor(fgColor));
        tv.setBackgroundColor(android.graphics.Color.parseColor(bgColor));
        tv.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, fontSize);
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        tv.setText(txt);

        return tv;
    }
    protected TextView GetLabelRanking(String fgColor,
                                        int fontSize,
                                        String txt,
                                        int width,
                                        int height)
    {
        TextView tv = new TextView(ma);

        tv.setLayoutParams(new LinearLayout.LayoutParams(width, height));

        if (txt.equals(EnumRanking.Hot.toString()))
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, ma.GetR(Constants.IMAGE_HOT_MOBILE));
        else if (txt.equals(EnumRanking.Warm.toString()))
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, ma.GetR(Constants.IMAGE_WARM_MOBILE));
        else if (txt.equals(EnumRanking.Cool.toString()))
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, ma.GetR(Constants.IMAGE_COLD_MOBILE));

        return tv;
    }

    protected TextView GetLabel(String fgColor,
                                int fontSize,
                                String txt,
                                int width,
                                int height)
    {
        TextView tv = new TextView(ma);

        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tv.setTextColor(android.graphics.Color.parseColor(fgColor));
        tv.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, fontSize);
        tv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        tv.setText(txt);

        return tv;
    }
    protected EditText GetTextBoxMultipleLines(int id,
                                                  String fgColor,
                                                  String bgColor,
                                                  int width,
                                                  int height,
                                                  int fontSize,
                                                  String txtValue,
                                                  boolean editable)
    {
        EditText et = new EditText(ma);

        et.setId(id);
        et.setLines(4);
        et.setMaxLines(4);
        et.setGravity(Gravity.LEFT);
        et.setBackgroundColor(android.graphics.Color.parseColor(bgColor));
        et.setTextColor(android.graphics.Color.parseColor(fgColor));
        et.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        et.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, fontSize);
        et.setText(txtValue);
        et.setEnabled(editable);

        return et;
    }

    protected EditText GetTextBox(int id,
                                  String fgColor,
                                  String bgColor,
                                  int width,
                                  int height,
                                  int fontSize,
                                  boolean password,
                                  String txtValue,
                                  boolean editable)
    {
        EditText et = new EditText(ma);

        et.setId(id);
        et.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        et.setBackgroundColor(android.graphics.Color.parseColor(bgColor));
        et.setTextColor(android.graphics.Color.parseColor(fgColor));
        et.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        et.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, fontSize);
        et.setText(txtValue);
        et.setEnabled(editable);

        if (password)
            et.setTransformationMethod(new PasswordTransformationMethod());

        return et;
    }
}
