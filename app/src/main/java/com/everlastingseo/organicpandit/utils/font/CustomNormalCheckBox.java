package com.everlastingseo.organicpandit.utils.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

public class CustomNormalCheckBox extends AppCompatCheckBox {
    public CustomNormalCheckBox(Context context) {
        super(context);
        applaycustomFont(context);
    }

    public CustomNormalCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);

        applaycustomFont(context);
    }

    public CustomNormalCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applaycustomFont(context);
    }

    private void applaycustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Sansation-Regular.ttf", context);
        setTypeface(customFont);
    }

}
