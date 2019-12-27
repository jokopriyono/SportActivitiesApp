package com.rizzkijo.sportactivitiesapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.abs

/**
 * This custom view created by Pawel
 * Edited by myself
 * Check his awesome work on https://github.com/pkleczko
 * and this custom view on https://github.com/pkleczko/CustomGauge
 */
class WeightGaugeMeter : View {
    companion object {
        private const val DEFAULT_LONG_POINTER_SIZE = 1f
    }

    private lateinit var mPaint: Paint
    private lateinit var mPaint2: Paint
    private lateinit var mRect: RectF
    private lateinit var mRect2: RectF

    private var mStrokeWidth = 10f
    private var mStrokeColor = 0
    private var mStrokeColor2 = 0
    private var mStrokeCap: String? = null
    private var mStartAngle = 0
    private var mSweepAngle = 360
    private var mStartValue = 360
    private var mEndValue = 1000f
    private var mPointSize = 0
    private var mPointStartColor = 0
    private var mPointEndColor = 0
    private var mDividerColor = 0
    private var mDividerSize = 0f
    private var mDividerStepAngle = 0
    private var mDividersCount = 0
    private var mDividerDrawFirst = true
    private var mDividerDrawLast = true
    private var mPointAngle = 0.0
    private var mValue = 0f
    private var mPoint = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        val a = context.obtainStyledAttributes(attributeSet, R.styleable.WeightGaugeMeter, 0, 0)

        // stroke style
        mStrokeWidth = a.getDimension(R.styleable.WeightGaugeMeter_gaugeStrokeWidth, 10f)
        mStrokeColor = a.getColor(
            R.styleable.WeightGaugeMeter_gaugeStrokeColor,
            ContextCompat.getColor(context, android.R.color.darker_gray)
        )
        mStrokeColor2 = ContextCompat.getColor(context, android.R.color.white)
        mStrokeCap = a.getString(R.styleable.WeightGaugeMeter_gaugeStrokeCap)

        // angle start and sweep (opposite direction 0, 270, 180, 90)
        mStartAngle = a.getInt(R.styleable.WeightGaugeMeter_gaugeStartAngle, 0)
        mSweepAngle = a.getInt(R.styleable.WeightGaugeMeter_gaugeSweepAngle, 360)

        // scale (from mStartValue to mEndValue)
        mStartValue = a.getInt(R.styleable.WeightGaugeMeter_gaugeStartValue, 0)
        mEndValue = a.getFloat(R.styleable.WeightGaugeMeter_gaugeEndValue, 1000f)

        // pointer size and color
        mPointSize = a.getInt(R.styleable.WeightGaugeMeter_gaugePointSize, 0)
        mPointStartColor = a.getColor(
            R.styleable.WeightGaugeMeter_gaugePointStartColor,
            ContextCompat.getColor(context, android.R.color.white)
        )
        mPointEndColor = a.getColor(
            R.styleable.WeightGaugeMeter_gaugePointEndColor,
            ContextCompat.getColor(context, android.R.color.white)
        )

        // divider options
        // divider options
        val dividerSize = a.getFloat(R.styleable.WeightGaugeMeter_gaugeDividerSize, 0f)
        mDividerColor = a.getColor(
            R.styleable.WeightGaugeMeter_gaugeDividerColor,
            ContextCompat.getColor(context, android.R.color.white)
        )
        val dividerStep = a.getInt(R.styleable.WeightGaugeMeter_gaugeDividerStep, 0)
        mDividerDrawFirst = a.getBoolean(R.styleable.WeightGaugeMeter_gaugeDividerDrawFirst, true)
        mDividerDrawLast = a.getBoolean(R.styleable.WeightGaugeMeter_gaugeDividerDrawLast, true)

        // calculating one point sweep
        mPointAngle = abs(mSweepAngle).toDouble() / (mEndValue - mStartValue)

        // calculating divider step
        if (dividerSize > 0) {
            mDividerSize = mSweepAngle / (abs(mEndValue - mStartValue) / dividerSize)
            mDividersCount = 100 / dividerStep
            mDividerStepAngle = mSweepAngle / mDividersCount
        }

        a.recycle()
        init()
    }

    private fun init() {
        // main Paint
        mPaint = Paint()
        mPaint.color = mStrokeColor
        mPaint.strokeWidth = mStrokeWidth
        mPaint.isAntiAlias = true
        if (!TextUtils.isEmpty(mStrokeCap)) {
            if (mStrokeCap == "BUTT") mPaint.strokeCap =
                Paint.Cap.BUTT else if (mStrokeCap == "ROUND") mPaint.strokeCap =
                Paint.Cap.ROUND
        } else mPaint.strokeCap = Paint.Cap.BUTT
        mPaint.style = Paint.Style.STROKE
        mRect = RectF()

        // rings paint
        mPaint2 = Paint()
        mPaint2.color = mStrokeColor2
        mPaint2.strokeWidth = 10f
        mPaint2.isAntiAlias = true
        mPaint2.style = Paint.Style.STROKE
        mRect2 = RectF()

        mValue = mStartValue.toFloat()
        mPoint = mStartAngle
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val padding: Float = mStrokeWidth
        val size = if (width < height) width.toFloat() else height.toFloat()
        val width = size - 2 * padding
        val height = size - 2 * padding
        val radius = if (width < height) width / 2 else height / 2
        val rectLeft = (getWidth() - 2 * padding) / 2 - radius + padding
        val rectTop = (getHeight() - 2 * padding) / 2 - radius + padding
        val rectRight = (getWidth() - 2 * padding) / 2 - radius + padding + width
        val rectBottom = (getHeight() - 2 * padding) / 2 - radius + padding + height
        mRect[rectLeft, rectTop, rectRight] = rectBottom
        mPaint.color = mStrokeColor
        mPaint.shader = null
        mPaint.setShadowLayer(30f, 0f, 0f, Color.LTGRAY)
        canvas.drawArc(mRect, mStartAngle.toFloat(), mSweepAngle.toFloat(), false, mPaint)
        mPaint.clearShadowLayer()

        mPaint.color = mPointStartColor
        mPaint.shader = LinearGradient(
            getWidth().toFloat(),
            getHeight().toFloat(),
            0f,
            0f,
            mPointEndColor,
            mPointStartColor,
            Shader.TileMode.CLAMP
        )
        if (mPointSize > 0) { //if size of pointer is defined
            if (mPoint > mStartAngle + mPointSize / 2) {
                canvas.drawArc(
                    mRect,
                    mPoint - mPointSize / 2.toFloat(),
                    mPointSize.toFloat(),
                    false,
                    mPaint
                )
            } else { //to avoid excedding start/zero point
                canvas.drawArc(mRect, mPoint.toFloat(), mPointSize.toFloat(), false, mPaint)
            }
        } else { //draw from start point to value point (long pointer)
            if (mValue == mStartValue.toFloat()) //use non-zero default value for start point (to avoid lack of pointer for start/zero value)
                canvas.drawArc(
                    mRect,
                    mStartAngle.toFloat(),
                    DEFAULT_LONG_POINTER_SIZE,
                    false,
                    mPaint
                ) else canvas.drawArc(
                mRect,
                mStartAngle.toFloat(),
                mPoint - mStartAngle.toFloat(),
                false,
                mPaint
            )
        }

        if (mDividerSize > 0) {
            mPaint.color = mDividerColor
            mPaint.shader = null
            var i = if (mDividerDrawFirst) 0 else 1
            val max = if (mDividerDrawLast) mDividersCount + 1 else mDividersCount
            while (i < max) {
                canvas.drawArc(
                    mRect,
                    mStartAngle + i * mDividerStepAngle.toFloat(),
                    mDividerSize,
                    false,
                    mPaint
                )
                i++
            }
        }

        mRect2[rectLeft - padding / 2, rectTop - padding / 2, rectRight + padding / 2] =
            rectBottom + padding / 2
        mPaint2.color = mStrokeColor2
        mPaint2.shader = null

        canvas.drawArc(mRect2, mStartAngle.toFloat(), mSweepAngle.toFloat(), false, mPaint2)

        mRect2[rectLeft + padding / 2, rectTop + padding / 2, rectRight - padding / 2] =
            rectBottom - padding / 2
        mPaint2.color = mStrokeColor2
        mPaint2.shader = null
        canvas.drawArc(mRect2, mStartAngle.toFloat(), mSweepAngle.toFloat(), false, mPaint2)
    }

    fun setValue(value: Float) {
        mValue = value
        mPoint = (mStartAngle + (mValue - mStartValue) * mPointAngle).toInt()
        invalidate()
    }

    fun getValue() = mValue

    fun setStrokeCap(strokeCap: String?) {
        mStrokeCap = strokeCap
        if (mStrokeCap == "BUTT") {
            mPaint.strokeCap = Paint.Cap.BUTT
        } else {
            mPaint.strokeCap = Paint.Cap.ROUND
        }
    }

    fun getEndValue() = mEndValue

    fun setEndValue(endValue: Float) {
        mEndValue = endValue
        mPointAngle = abs(mSweepAngle).toDouble() / (mEndValue - mStartValue)
        invalidate()
    }

    fun setDividerStep(dividerStep: Int) {
        if (dividerStep > 0) {
            mDividersCount = 100 / dividerStep
            mDividerStepAngle = mSweepAngle / mDividersCount
        }
    }

    fun setDividerSize(dividerSize: Int) {
        if (dividerSize > 0) {
            mDividerSize = mSweepAngle / (Math.abs(mEndValue - mStartValue) / dividerSize)
        }
    }
}