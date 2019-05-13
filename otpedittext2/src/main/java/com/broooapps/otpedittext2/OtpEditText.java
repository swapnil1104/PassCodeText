package com.broooapps.otpedittext2;

/**
 * Created by Swapnil Tiwari on 2019-05-07.
 * swapnil.tiwari@box8.in
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class OtpEditText extends AppCompatEditText {
    public static final String XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";
    private int defStyleAttr = 0;

    private float mSpace = 8; //24 dp by default, space between the lines
    private float mCharSize;
    private float mNumChars = 6;
    private float mLineSpacing = 10; //8dp by default, height of the text from our lines
    private int mMaxLength = 6;

    private OnClickListener mClickListener;

    private float mLineStroke = 1; //1dp by default
    private float mLineStrokeSelected = 2; //2dp by default
    private Paint mLinesPaint;

    private int mMainColor;
    private int mSecondaryColor;
    private int mTextColor;

    private Paint mStrokePaint;

    public OtpEditText(Context context) {
        super(context);
    }

    public OtpEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public OtpEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.defStyleAttr = defStyleAttr;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        getAttrsFromTypedArray(attrs);

        float multi = context.getResources().getDisplayMetrics().density;
        mLineStroke = multi * mLineStroke;
        mLineStrokeSelected = multi * mLineStrokeSelected;
        mLinesPaint = new Paint(getPaint());
        mStrokePaint = new Paint(getPaint());
        mStrokePaint.setStrokeWidth(4);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mLinesPaint.setStrokeWidth(mLineStroke);
        setBackgroundResource(0);
        mSpace = multi * mSpace; //convert to pixels for our density
        mNumChars = mMaxLength;

        //Disable copy paste
        super.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        // When tapped, move cursor to end of text.
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelection(getText().length());
                if (mClickListener != null) {
                    mClickListener.onClick(v);
                }
            }
        });

    }

    private void getAttrsFromTypedArray(AttributeSet attributeSet) {
        final TypedArray a = getContext().obtainStyledAttributes(attributeSet, R.styleable.OtpEditText, defStyleAttr, 0);

        mMaxLength = attributeSet.getAttributeIntValue(XML_NAMESPACE_ANDROID, "maxLength", 4);
        mMainColor = a.getColor(R.styleable.OtpEditText_oev_primary_color, getResources().getColor(android.R.color.holo_red_dark));
        mSecondaryColor = a.getColor(R.styleable.OtpEditText_oev_secondary_color, getResources().getColor(R.color.light_gray));
        mTextColor = a.getColor(R.styleable.OtpEditText_oev_text_color, getResources().getColor(android.R.color.black));

        a.recycle();
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mClickListener = l;
    }

    @Override
    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        throw new RuntimeException("setCustomSelectionActionModeCallback() not supported.");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int availableWidth = getWidth() - getPaddingRight() - getPaddingLeft();
        if (mSpace < 0) {
            mCharSize = (availableWidth / (mNumChars * 2 - 1));
        } else {
            mCharSize = (availableWidth - (mSpace * (mNumChars - 1))) / mNumChars;
        }

        mLineSpacing = (float) (getHeight() * .7);

        int startX = getPaddingLeft();
        int bottom = getHeight() - getPaddingBottom();
        int top = getPaddingTop();

        //Text Width
        Editable text = getText();
        int textLength = text.length();
        float[] textWidths = new float[textLength];
        getPaint().getTextWidths(getText(), 0, textLength, textWidths);

        for (int i = 0; i < mNumChars; i++) {
            updateColorForLines(i <= textLength, i == textLength, getText().length(), (int) mNumChars);
            canvas.drawLine(startX, bottom, startX + mCharSize, bottom, mLinesPaint);

            try {
                canvas.drawRoundRect(startX, top, startX + mCharSize, bottom, 8, 8, mLinesPaint);
                canvas.drawRoundRect(startX, top, startX + mCharSize, bottom, 8, 8, mStrokePaint);
            } catch (NoSuchMethodError err) {
                canvas.drawRect(startX, top, startX + mCharSize, bottom, mLinesPaint);
                canvas.drawRect(startX, top, startX + mCharSize, bottom, mStrokePaint);
            }
            if (getText().length() > i) {
                float middle = startX + mCharSize / 2;
                canvas.drawText(text, i, i + 1, middle - textWidths[0] / 2, mLineSpacing, getPaint());
            }

            if (mSpace < 0) {
                startX += mCharSize * 2;
            } else {
                startX += mCharSize + mSpace;
            }
        }
    }

    /**
     * @param next Is the current char the next character to be input?
     */
    private void updateColorForLines(boolean next, boolean current, int textSize, int totalSize) {
        if (next) {
            mStrokePaint.setColor(mSecondaryColor);
            mLinesPaint.setColor(mSecondaryColor);
        } else {
            mStrokePaint.setColor(mSecondaryColor);
            mLinesPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.white));
        }
        if (current) {
            mLinesPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.white));
            mStrokePaint.setColor(mMainColor);
        }
    }
}
