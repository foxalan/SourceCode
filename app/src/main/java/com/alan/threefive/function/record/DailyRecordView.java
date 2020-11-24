package com.alan.threefive.function.record;

import android.animation.ValueAnimator;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author alan
 * function: 日常事件View
 * todo:
 * 1.动画未处理
 * 2.刻度未处理
 * 3.字没对齐
 * 4.点击响应慢
 * 5.属性没拉出来
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
    private int mTextDateTopWidth = 250;

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
    private int mTextLeftWidth = 50;

    //数据
    private List<DailyRecord> dailyRecords;
    //保存数据
    private List<Rect> rects = new ArrayList<>();
    //圆柱体
    private Paint mPaintRound;
    //日期
    private String currentDate = "2020-11-17";

    private Rect currentRect;
    //中心点
    private int startCenterY;
    //选中时的线
    private Paint mPaintChooseLine;

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
        mPaintDateLeft.setTextSize(50);
        mPaintDateLeft.setStrokeWidth(2);
        mPaintDateLeft.setStyle(Paint.Style.FILL);

        mPaintRound = new Paint();
        mPaintRound.setColor(Color.RED);
        mPaintRound.setAntiAlias(true);
        mPaintRound.setStrokeWidth(2);
        mPaintRound.setStyle(Paint.Style.FILL);

        mPaintChooseLine = new Paint();
        mPaintChooseLine.setColor(Color.BLUE);
        mPaintChooseLine.setAntiAlias(true);
        mPaintChooseLine.setStrokeWidth(2);
        mPaintChooseLine.setStyle(Paint.Style.FILL);

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
        mRectDateTop.left = (mViewWidth - mTextDateTopWidth-50) / 2;
        mRectDateTop.right = (mViewWidth + mTextDateTopWidth+50) / 2;
    }

    /**
     * 设置数据
     *
     * @param dailyList
     * @param type
     */
    public void setDailyList(List<DailyRecord> dailyList, int type) {
        this.dailyRecords = dailyList;
        currentType = type;

        if (dailyList.size()==0){
            return;
        }
        int size = dailyList.size()-1;
        currentDate = dailyList.get(size).date;
        invalidate();
        onCallBack(dailyList.get(0));
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
        //
        drawTopDate(canvas);
        drawLine(canvas);

        drawChooseLine(canvas);
    }

    /**
     * 画上面的日期
     *
     * @param canvas
     */
    private void drawTopDate(Canvas canvas) {
        canvas.drawRect(mRectDateTop, mPaintDateTop);

        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.BLUE);
        rectPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(mRectDateTop, rectPaint);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);
        textPaint.setStyle(Paint.Style.FILL);
        //该方法即为设置基线上那个点到底是left,center,还是right  这里我设置为center
        textPaint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        int baseLineY = (int) (mRectDateTop.centerY() - top/2 - bottom/2);//基线中间点的y轴计算公式

        canvas.drawText(currentDate,mRectDateTop.centerX(),baseLineY,textPaint);
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
        startCenterY = startLinePositionY + 2 * lineDivider;
        rects.clear();
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
    private void drawLineDay(final Canvas canvas, int lineWidth, int startY) {
        //第一步，找到天数
        if (dailyRecords == null || dailyRecords.size() == 0) {
            return;
        }

        int day = dailyRecords.size();
        Log.e("DailyRecord", "day" + day);
        int width = lineWidth / (day * 2 - 1);

        for (int i = 0; i < day; i++) {
            final DailyRecord record = dailyRecords.get(i);
           final Rect rect = new Rect();
            if (record.totalPoint > 0) {
                mPaintRound.setColor(Color.GREEN);
                rect.left = startLinePositionX + i * width * 2;
                rect.right = rect.left + width;
                rect.bottom = startY;
                rect.top = (int) (rect.bottom - getHeightByPoint(record.totalPoint));
                ValueAnimator valueAnimator = ValueAnimator.ofInt(rect.top);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                    }
                });
                valueAnimator.setRepeatCount(1);
                valueAnimator.setDuration(2000);
                valueAnimator.start();
            } else {
                mPaintRound.setColor(Color.RED);
                rect.left = startLinePositionX + i * width * 2;
                rect.right = rect.left + width;
                rect.top = startY;
                rect.bottom = (int) (rect.top - getHeightByPoint(record.totalPoint));
            }
            rects.add(rect);
            canvas.drawRect(rect, mPaintRound);

        }
    }

    /**
     * 通过分数得到高度
     *
     * @param totalPoint
     * @return
     */
    private double getHeightByPoint(Integer totalPoint) {
        return lineDivider * (totalPoint * 1.0 / degree * 1.0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            //2.显示日期
            //1,画线
            //3.回调日志数据
            int x = (int) event.getX();
            int y = (int) event.getY();

            if (rects.size() != 0) {
                int size = rects.size();
                for (int i = 0; i < size; i++) {
                    if (rects.get(i).contains(x, y)) {
                        onChooseDaily(i, rects.get(i));
                        break;
                    }
                }
            }

            return true;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 被选中时
     */
    private void onChooseDaily(int position, Rect rect) {

        if (dailyRecords.size() == 0) {
            return;
        }

        if (position >= dailyRecords.size()) {
            return;
        }

        DailyRecord dailyRecord = dailyRecords.get(position);
        currentDate = dailyRecord.date;
        currentRect = rect;
        onCallBack(dailyRecord);
        invalidate();

    }

    /**
     * 画被选中的
     *
     * @param
     */
    private void drawChooseLine(Canvas canvas) {

        if (currentRect == null){
            return;
        }

        int centerX;
        int centerY;

        int top = currentRect.top;
        int bottom = currentRect.bottom;
        int left = currentRect.left;
        int right = currentRect.right;

        Log.e("DailyRecord",top+"===="+startCenterY);

        if (top<startCenterY){
            centerX = (left+right)/2;
            centerY = top;
        }else {
            centerX = (left+right)/2;
            centerY = bottom;
        }
        canvas.drawCircle(centerX,centerY,15,mPaintChooseLine);
        canvas.drawLine(centerX,centerY-500,centerX,centerY+500,mPaintChooseLine);
    }

    /**
     * 值回调
     *
     * @param dailyRecord
     */
    private void onCallBack(DailyRecord dailyRecord) {
        if (callBack!=null){
            callBack.onDateCallBack(dailyRecord);
        }
    }


    private onChooseCallBack callBack;

    public void setOnChooseCallBack(onChooseCallBack chooseCallBack){
        this.callBack = chooseCallBack;
    }

    /**
     * 选择后回调
     */
    public interface onChooseCallBack {

        /**
         * 日期返回事件
         * @param dailyRecord
         */
        void onDateCallBack(DailyRecord dailyRecord);

    }
}
