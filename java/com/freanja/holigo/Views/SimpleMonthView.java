package com.freanja.holigo.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.freanja.holigo.Utils.DateUtil;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

public class SimpleMonthView extends MonthView {
    public Paint mAfterPaint = new Paint();
    public Paint mAfterTextPaint = new Paint();
    public Paint mBeforeTextPaint = new Paint();
    int mRadius;
    public String selectedDate = "";

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
        mSchemePaint.setShadowLayer(15, 1, 3, 0xAA333333);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mSelectedPaint.setStyle(Paint.Style.FILL);

        mAfterPaint.setColor(Color.rgb(255, 246, 243));
        mAfterPaint.setStyle(Paint.Style.FILL);

        mAfterTextPaint.setColor(Color.rgb(242, 97, 60));
        mAfterTextPaint.setTextSize(60);

        mBeforeTextPaint.setColor(Color.rgb(196, 196, 196));
        mBeforeTextPaint.setTextSize(60);
    }

    public SimpleMonthView(Context context) {
        super(context);
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
//        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        canvas.drawText(String.valueOf(calendar.getDay()),
                cx,
                baselineY,
                mSelectTextPaint);
        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;
        DateUtil dateUtil = new DateUtil();

        String date = calendar.toString();

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
            selectedDate = date;
        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else if (dateUtil.rangeDate(date, selectedDate)){
            canvas.drawRoundRect(x, y, x + mItemWidth, y + mItemHeight, 100, 100, mAfterPaint);
            if (calendar.getDay() >= 10){
                canvas.drawText(String.valueOf(calendar.getDay()),
                        cx - 30,
                        baselineY,
                        mAfterTextPaint);
            }
            else {
                canvas.drawText(String.valueOf(calendar.getDay()),
                        cx - 20,
                        baselineY,
                        mAfterTextPaint);
            }
        }
        else if (dateUtil.beforeCur(date)){
            if (calendar.getDay() >= 10){
                canvas.drawText(String.valueOf(calendar.getDay()),
                        cx - 30,
                        baselineY,
                        mBeforeTextPaint);
            }
            else {
                canvas.drawText(String.valueOf(calendar.getDay()),
                        cx - 20,
                        baselineY,
                        mBeforeTextPaint);
            }
        }
        else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }
}
