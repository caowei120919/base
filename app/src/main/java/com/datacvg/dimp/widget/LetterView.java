package com.datacvg.dimp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.datacvg.dimp.R;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-11
 * @Description :
 */
public class LetterView extends LinearLayout {
    private Context mContext;
    private CharacterClickListener mListener;

    public LetterView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        setOrientation(VERTICAL);

        initView();
    }

    private void initView() {
        for (char i = 'A'; i <= 'Z'; i++) {
            final String character = i + "";
            TextView tv = buildTextLayout(character);
            addView(tv);
        }
        addView(buildTextLayout("#"));
    }

    private TextView buildTextLayout(final String character) {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(mContext);
        tv.setLayoutParams(layoutParams);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(12);
        tv.setTextColor(mContext.getResources().getColor(R.color.c_5b7c7c));
        tv.setClickable(true);
        tv.setText(character);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickCharacter(character);
                }
            }
        });
        return tv;
    }

    public void setCharacterListener(CharacterClickListener listener) {
        mListener = listener;
    }

    public interface CharacterClickListener {
        void clickCharacter(String character);
    }
}
