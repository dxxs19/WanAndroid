package com.wei.wanandroid.widgets

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView

/**
 * 圆角矩形（后期还可扩展成圆形）
 * @author XiangWei
 * @since 2018/12/3
 */
class RoundedCornersImageView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0)
    : ImageView(context, attributeSet, defStyle) {

    private var bitmapPaint: Paint? = null
    private var radius = 20f

    init {
        bitmapPaint = Paint()
        bitmapPaint?.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)  // 此行代码要注释，否则没有圆角效果

        val drawable = drawable
        drawable?.let {
            var bitmap = drawableToBitmap(it)
            bitmap?.let { it ->
                // 通过BitmapShader对Bitmap进行渲染，
                val bitmapShader = BitmapShader(it, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                bitmapPaint?.shader = bitmapShader
                val rectF = RectF(0f, 0f, it.width.toFloat(), it.height.toFloat())
                canvas?.drawRoundRect(rectF, radius, radius, bitmapPaint)
            }
        }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(drawableWidth, drawableHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, drawableWidth, drawableHeight)
        drawable.draw(canvas)
        return bitmap
    }

}