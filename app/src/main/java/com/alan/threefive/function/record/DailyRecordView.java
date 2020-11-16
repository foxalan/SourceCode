package com.alan.threefive.function.record;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.alan.tfive_function.database.table.DailyRecord;

import java.util.List;

/**
 * @author alan
 * function: 日常事件View
 */
public class DailyRecordView extends View {

    private static final String TAG = "dailyRecord";

    public static final int RECORD_TYPE_YEAR = 0;
    public static final int RECORD_TYPE_MONTH = 1;
    public static final int RECORD_TYPE_DAY = 2;

    private int currentType = 2;

    //View的宽度
    private int mViewWidth;
    //View的长度
    private int mViewHeight;

    //开始绘制的X,Y
    private int startPositionX;
    private int startPositionY;

    //上面的日期
    private Paint mPaintDateTop;
    private Rect mRectDateTop;
    //最上面日期高度
    private int mTextDateTopHeight = 100;
    private int mTextDateTopWidth = 200;

    private Paint mPaintLine;
    //绘制线的时候的X,Y
    private int startLinePositionX;
    private int startLinePositionY;
    //间隔
    private int lineDivider;
    //线的个数
    private int lineCount = 6;
    //线距离日期文字的高度
    private int mLineMarginTextHeight = 50;
    //最下面的日期高度
    private int mTextDateBottomHeight = 50;

    private int degree = 5;
    private int startDegree = 10;

    //左边的文字
    private Paint mPaintDateLeft;
    //左边日期的宽度
    private int mTextLeftWidth = 100;

    //数据
    private List<DailyRecord> dailyRecords;
    //保
    private List<Rect> rects;

    public DailyRecordView(Context context) {
        super(context);
        initPaint();
    }

    public DailyRecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public DailyRecordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaintLine = new Paint();
        mPaintLine.setColor(Color.BLACK);
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStrokeWidth(2);
        mPaintLine.setStyle(Paint.Style.FILL);

        mPaintDateTop = new Paint();
        mPaintDateTop.setColor(Color.GREEN);
        mPaintDateTop.setAntiAlias(true);
        mPaintDateTop.setStrokeWidth(2);
        mPaintDateTop.setStyle(Paint.Style.FILL);

        mPaintDateLeft = new Paint();
        mPaintDateLeft.setColor(Color.YELLOW);
        mPaintDateLeft.setAntiAlias(true);
        mPaintDateLeft.setTextSize(18);
        mPaintDateLeft.setStrokeWidth(2);
        mPaintDateLeft.setStyle(Paint.Style.FILL);
    }

    /**
     * 初始化位置
     */
    private void initPosition() {

        //起始位置
        startPositionX = getPaddingLeft() + 50;
        startPositionY = getPaddingTop() + 50;

        //线的位置
        startLinePositionY = startPositionY
                + mTextDateTopHeight + mLineMarginTextHeight;

        startLinePositionX = startPositionX + mTextLeftWidth + mLineMarginTextHeight;
        lineDivider = (mViewHeight - startPositionY * 2 - mTextDateTopHeight - mTextDateBottomHeight - mLineMarginTextHeight) / 5;
        Log.e(TAG, "DIVIDER:" + lineDivider);

        //上面日期
        mRectDateTop = new Rect();
        mRectDateTop.top = startPositionY;
        mRectDateTop.bottom = startPositionY + mTextDateTopHeight;
        mRectDateTop.left = (mViewWidth - mTextDateTopWidth) / 2;
        mRectDateTop.right = (mViewWidth + mTextDateTopWidth) / 2;

    }


    public void setDailyList(List<DailyRecord> dailyList, int type) {
        this.dailyRecords = dailyList;
        currentType = type;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //
        switch (widthMode) {
            case MeasureSpec.AT_MOST:

                break;
            case MeasureSpec.EXACTLY:
                mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
                break;
            default:
                break;
        }

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
                break;
            default:
                break;
        }
        initPosition();
        Log.e(TAG, mViewWidth + "====" + mViewHeight);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTopDate(canvas);
        drawLine(canvas);
    }

    /**
     * 画上面的日期
     *
     * @param canvas
     */
    private void drawTopDate(Canvas canvas) {
        canvas.drawRect(mRectDateTop, mPaintDateTop);
    }

    /**
     * 画X,Y
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        int lineWidth = mViewWidth - startPositionX * 2 - startLinePositionX;
        //画柱形的开始高度
        for (int i = 0; i < lineCount; i++) {
            int startX = startLinePositionX;
            int startY = startLinePositionY + i * lineDivider;
            int endX = startPositionX + lineWidth;
            canvas.drawLine(startX, startY, startX + endX, startY, mPaintLine);
            int mDegree = startDegree - i * degree;
            canvas.drawText(mDegree + "", startPositionX, startY, mPaintDateLeft);
        }
        //画柱状图
        switch (currentType) {
            case RECORD_TYPE_YEAR:
                break;
            case RECORD_TYPE_MONTH:
                break;
            case RECORD_TYPE_DAY:
                drawLineDay(canvas, lineWidth, startLinePositionY + 2 * lineDivider);
                break;
            default:
                break;
        }

    }

    /**
     * 画柱形
     *
     * @param canvas
     * @param lineWidth 横线的长度
     * @param startY    开始的位置
     */
    private void drawLineDay(Canvas canvas, int lineWidth, int startY) {
        //第一步，找到天数
        if (dailyRecords == null || dailyRecords.size() == 0) {

        }

        int day = dailyRecords.size();
        int width = lineWidth / (day * 2 - 1);

        for (int i = 0; i < day; i++) {
            DailyRecord record = dailyRecords.get(i);
            Rect rect = new Rect();
            if (record.totalPoint < 0) {
                rect.left = startLinePositionX + i * width * 2;
                rect.right = rect.left + width;
                rect.bottom = startY;
                rect.top = rect.bottom + getHeightByPoint(record.totalPoint);
            } else {
                rect.left = startLinePositionX + i * width * 2;
                rect.right = rect.left + width;
                rect.bottom = startY;
                rect.top = rect.bottom + getHeightByPoint(record.totalPoint);
            }
//            canvas.drawRect(rect);
        }

    }

    /**
     * 通过分数得到高度
     *
     * @param totalPoint
     * @return
     */
    private int getHeightByPoint(Integer totalPoint) {
        return lineDivider * (totalPoint / degree);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }
}
