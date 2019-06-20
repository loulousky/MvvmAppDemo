package com.example.liuhai.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import java.lang.ref.WeakReference
import kotlin.math.max

/**
 * 作者：liuhai
 * 时间：2019/6/18:16:12
 * 邮箱：185587041@qq.com
 * 说明：自定义导航栏
 */
class JumpGuideViewGroup(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    constructor(context: Context?) : this(context, null)

    private var totalwidth: Float = 0f;
    //默认50
    private var childwidth: Float=50f;

    //选中的ITEM默认为0
    private var selectPosition:Int=0;

    //抛物线的最大凹度
    private var maxBSRH:Float=40f;//默认40

    //抛物线的最小凹度
    private var minBSRH:Float=10f;//默认40


    //贝塞尔曲线画圆的因子
    private val circleF:Float=0.551915024494f;


    init {
        //拿到当前屏幕的宽度用于计算子VIEW的宽度
        //让这个View画背景
        setWillNotDraw(false)
        totalwidth = context!!.resources!!.displayMetrics!!.widthPixels.toFloat()
    }
    //存错子VIEW的位置和宽高
    private var maxHeight: Float = 50f;
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        childwidth = totalwidth / childCount.toFloat()

        for (i in 0 until childCount) {
            val childview = getChildAt(i)
            childview.visibility= View.VISIBLE
            childview.setOnClickListener {
                //控制贝塞尔曲线的绘制位置
                //让所有的VIEW显示
                for (i in 0 until childCount) {
                    val childview = getChildAt(i)
                    childview.visibility= View.VISIBLE
                }
                selectPosition=i
                invalidate()
          //      Toast.makeText(context,"当前的位置是"+i,0).show()
            }
            //测量子View
            measureChild(childview, widthMeasureSpec, heightMeasureSpec)
            //设置高度为子VIEW最高的一个
            maxHeight = Math.max(maxHeight, childview.measuredHeight.toFloat())
        }
        val mywidthMeasureSpec = MeasureSpec.makeMeasureSpec(totalwidth.toInt(), widthMeasureSpec)
        val myheightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight.toInt(), heightMeasureSpec)

        maxBSRH=childwidth/2;
        minBSRH=childwidth/2;
        setMeasuredDimension(mywidthMeasureSpec, myheightMeasureSpec)
    }
    //摆放
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //摆成一排
        for (i in 0 until childCount){
            val widthper=i*childwidth
            //布局
            getChildAt(i).layout(widthper.toInt(),0,(widthper+childwidth).toInt(),maxHeight.toInt())
        }
    }
    override fun onDraw(canvas: Canvas?) {
        //画背景
        val paint:Paint= Paint()
        paint.isAntiAlias=true;
        paint.color=Color.GREEN;
        //画背景
        val path:Path=Path();
        //起点开始 可能移动到第
        path.moveTo(0f,0f)
        //移动到要绘制贝塞尔曲线的点
        path.lineTo(selectPosition*childwidth,0f);
        path.cubicTo(selectPosition*childwidth,0f+circleF*(maxBSRH),
            (selectPosition*childwidth+maxBSRH*circleF),childwidth/2,
            selectPosition*childwidth+childwidth/2,childwidth/2)
        path.cubicTo(selectPosition*childwidth+childwidth/2+maxBSRH*circleF,childwidth/2,
            selectPosition*childwidth+childwidth,maxBSRH*circleF,
            selectPosition*childwidth+childwidth,0f)
        path.lineTo(totalwidth,0f)
        path.lineTo(totalwidth,maxHeight.toFloat())
        path.lineTo(0f,maxHeight.toFloat())
        path.lineTo(0f,0f)
        getChildAt(selectPosition).visibility= View.INVISIBLE
        canvas?.drawPath(path,paint)
        super.onDraw(canvas)
    }
}