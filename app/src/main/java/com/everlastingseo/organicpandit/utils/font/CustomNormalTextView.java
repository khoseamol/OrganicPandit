package com.everlastingseo.organicpandit.utils.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class CustomNormalTextView extends AppCompatTextView {
    public CustomNormalTextView(Context context) {
        super(context);
        applaycustomFont(context);
    }

    public CustomNormalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applaycustomFont(context);
    }

    public CustomNormalTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applaycustomFont(context);
    }

    private void applaycustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Sansation-Regular.ttf", context);
        setTypeface(customFont);
    }

}
