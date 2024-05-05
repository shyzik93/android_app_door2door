package ru.syeysk.election.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;

import ru.syeysk.election.R;

public class IFTools {

    static public void setBtnCondition(Context context, View btn, String condition) {
        Resources.Theme theme = context.getTheme();
        if (condition == null) {
            btn.setBackgroundColor(context.getResources().getColor(R.color.colorNull, theme));
        } else if (condition.equals("1")) {
            btn.setBackgroundColor(context.getResources().getColor(R.color.colorPositive, theme));
        } else if (condition.equals("0")) {
            btn.setBackgroundColor(context.getResources().getColor(R.color.colorNegative, theme));
        }
    }

}
