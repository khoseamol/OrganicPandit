package com.everlastingseo.organicpandit.utils.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class CustomNormalButton extends AppCompatButton {
    public CustomNormalButton(Context context) {
        super(context);
        applaycustomFont(context);
    }

    public CustomNormalButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applaycustomFont(context);
    }

    public CustomNormalButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applaycustomFont(context);
    }
    private void applaycustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Sansation-Regular.ttf", context);
        setTypeface(customFont);
    }
}
