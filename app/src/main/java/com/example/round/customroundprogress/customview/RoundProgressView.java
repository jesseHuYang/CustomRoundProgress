package com.example.round.customroundprogress.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.icu.util.Measure;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.round.customroundprogress.R;

/**
 * Auther:JesseHu
 * Date 2017/4/17 0017
 * Email:15623889032@163.com
 */

public class RoundProgressView extends View {
    //进度条颜色
    private int mTextColor;
    //进度条字体大小
    private float mTextSize;
    //文字
    private String mProgressText;
    //圆半径
    private float mRoundRadius;
    //环宽度
    private float mRoundWidth;
    //圆环默认颜色
    private int mRoundDefaultColor;
    //圆环进度颜色
    private int mProgressColor;
    //当前进度
    private float mProgress;
    //最大进度值
    private float mMaxProgress ;
    //画笔
    private Paint mPaint1;
    private Paint mPaint2;
    private Paint mPaint3;
    public RoundProgressView(Context context) {
        this(context, null);
    }

    public RoundProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.custom_progress, defStyleAttr, 0);
        mTextColor = a.getColor(R.styleable.custom_progress_text_color, ContextCompat.getColor(context, R.color.text_color));
        mTextSize = a.getDimension(R.styleable.custom_progress_text_size, 40.0f);
        mRoundRadius = a.getDimension(R.styleable.custom_progress_round_radius, 80.f);
        mRoundWidth = a.getDimension(R.styleable.custom_progress_round_width, 15.0f);
        mRoundDefaultColor = a.getColor(R.styleable.custom_progress_round_default_color, ContextCompat.getColor(context, R.color.color_default));
        mProgressColor = a.getColor(R.styleable.custom_progress_round_color, ContextCompat.getColor(context, R.color.color_progress));
        mProgress = a.getDimension(R.styleable.custom_progress_progress, 0.0f);
        mMaxProgress = a.getDimension(R.styleable.custom_progress_max_progress, 100.0f);
        mProgressText = mProgress + "%";
        a.recycle();
        mPaint1 = new Paint();
        mPaint2 = new Paint();
        mPaint3 = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = (int) ((mRoundRadius * 2) + mRoundWidth);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = (int) ((mRoundRadius * 2) + mRoundWidth);
        }
        if (width > height) {
            width = height;
        }
        mRoundWidth = width/10;
        mRoundRadius = (width - mRoundWidth)/2;
        mTextSize = mRoundRadius / 3;
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画外层圆
        int circleAngle = getWidth()/2;//圆中心坐标
        canvas.save();
        mPaint1.setColor(mRoundDefaultColor);//设置颜色
        mPaint1.setStyle(Paint.Style.STROKE);//设置空心圆
        mPaint1.setStrokeWidth(mRoundWidth);//设置圆宽度
        mPaint1.setAntiAlias(true);//消除锯齿
        canvas.drawCircle(circleAngle, circleAngle, mRoundRadius, mPaint1);
        canvas.restore();
        //画进度圆弧
        canvas.save();

       /** drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)//画弧，
        参数一是RectF对象，一个矩形区域椭圆形的界限用于定义在形状、大小、电弧，
        参数二是起始角(度)在电弧的开始，(起始角度0.0f从右边开始)
        参数三扫描角(度)开始顺时针测量的，
        参数四是如果这是真的话,包括椭圆中心的电弧,并关闭它,如果它是假这将是一个弧线,
        参数五是Paint对象
        */
        mPaint2.setColor(mProgressColor);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeWidth(mRoundWidth);
        mPaint2.setAntiAlias(true);
        float sweepAngle = ((float) (mProgress / mMaxProgress)) * 360;
        Log.i("sweep", sweepAngle +"");
        RectF oval = new RectF(circleAngle - mRoundRadius, circleAngle - mRoundRadius, circleAngle + mRoundRadius, circleAngle + mRoundRadius);
        canvas.drawArc(oval, 0.0f, sweepAngle, false, mPaint2);
        canvas.restore();
        //画文字
        /**
         *  //渲染文本，Canvas类除了上面的还可以描绘文字，
         *  参数一是String类型的文本，
         *  参数二x轴，
         *  参数三y轴，
         *  参数四是Paint对象。
         */
        mPaint3.setColor(mTextColor);
        mPaint3.setTextSize(mTextSize);
        mPaint3.setStrokeWidth(0);
        Rect rect = new Rect();
        mPaint3.getTextBounds(mProgressText, 0, mProgressText.length(), rect);
        int scaleX = circleAngle - rect.width()/2;
        Paint.FontMetricsInt fontMetrics = mPaint3.getFontMetricsInt();
        Log.i("top", fontMetrics.top+"");
        Log.i("bottom", fontMetrics.bottom+"");
        int scaleY = circleAngle - (fontMetrics.bottom + fontMetrics.top)/2;  //y轴
        canvas.drawText(mProgressText, scaleX, scaleY, mPaint3);
    }
    public void setProgress(float progress) {
        if (progress < 0) {
            mProgress = 0;
        } else if (progress > 100){
            mProgress = mMaxProgress;
        } else {
            mProgress = progress;
        }
        mProgressText = (int)mProgress + "%";
        postInvalidate();
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
