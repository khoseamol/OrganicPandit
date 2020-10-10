package com.everlastingseo.organicpandit.utils.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class CustomBoldButton extends AppCompatButton {
    public CustomBoldButton(Context context) {
        super(context);
        applaycustomFont(context);
    }

    public CustomBoldButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applaycustomFont(context);
    }

    public CustomBoldButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applaycustomFont(context);
    }
    private void applaycustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Sansation-Bold.ttf", context);
        setTypeface(customFont);
    }
}
