package com.freanja.holigo.Views;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.RangeMonthView;

public class CusMonthView extends RangeMonthView {

    public Paint mAfterPaint = new Paint();
    public Paint mAfterTextPaint = new Paint();

    int mRadius;

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
        mSchemePaint.setShadowLayer(15, 1, 3, 0xAA333333);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }


    public CusMonthView(Context context) {
        super(context);
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 绘制选中的日子
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return true 则绘制onDrawScheme，因为这里背景色不是是互斥的
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelectedPre, boolean isSelectedNext) {
        mSelectedPaint.setStyle(Paint.Style.FILL);

        mAfterPaint.setColor(Color.rgb(255, 246, 243));
        mAfterPaint.setStyle(Paint.Style.FILL);

        mAfterTextPaint.setColor(Color.rgb(242, 97, 60));
        mAfterTextPaint.setTextSize(60);

        float baselineY = mTextBaseLine + y;

        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        if (isSelectedPre) {
            // 后几天
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
        else {
            // 第一天
            canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
        }

        return true;
    }

    /**
     * 绘制标记的事件日子
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     * @param y        日历Card y起点坐标
     */
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y, boolean isSelected) {

    }

    /**
     * 绘制文本
     *
     * @param canvas     canvas
     * @param calendar   日历calendar
     * @param x          日历Card x起点坐标
     * @param y          日历Card y起点坐标
     * @param hasScheme  是否是标记的日期
     * @param isSelected 是否选中
     */
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;

        if (isSelected) {

        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }
}
