package com.everlastingseo.organicpandit.utils.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class CustomNormalEditText  extends AppCompatEditText {
    public CustomNormalEditText(Context context) {
        super(context);
        applaycustomFont(context);
    }

    public CustomNormalEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        applaycustomFont(context);
    }

    public CustomNormalEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applaycustomFont(context);
    }

    private void applaycustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Sansation-Regular.ttf", context);
        setTypeface(customFont);
    }
}
