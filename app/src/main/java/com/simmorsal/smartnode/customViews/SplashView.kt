package com.simmorsal.smartnode.customViews

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.simmorsal.smartnode.activities.ActivityMain
import com.simmorsal.smartnode.interfaces.OnSplashAnimFinish
import kotlin.math.ln
import kotlin.math.pow


class SplashView : View {

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize()
    }


//////////////////////////////////////////////////////////////////////

    private val LOGO_FADE_SPEED = 300
    private val CLICK_SPEED = 100
    private val LIGHT_EXPAND = 600

    private var INVALIDATE_DELAY = 16L

    private var pcWidth = 0 // pc = percent
    private var pcHeight = 0
    private var smallSide = 0
    private var isHorizontal = false
    private var scene = 0f
    private var drawSpeed = 16
    @get:JvmName("getHandler_")
    private val handler = Handler()
    private var runnableInvalidate = Runnable { invalidate() }

    private lateinit var logo: Bitmap

    private val rectFBackground = RectF()
    private val rectFButtonRing = RectF()
    private val rectFLogo = RectF()
    private val rectFLight = RectF()

    private val paintBackground = Paint()
    private val paintButtonRing = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintLogo = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintLight = Paint(Paint.ANTI_ALIAS_FLAG)

//    private lateinit var gradient: IntArray

    private val blurMaskFilter = BlurMaskFilter(1f, BlurMaskFilter.Blur.NORMAL)
    private val porterDuffBlack = PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)


    private fun initialize() {


        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (windowManager != null) {
            drawSpeed = (1000 / windowManager.defaultDisplay.refreshRate).toInt()
            INVALIDATE_DELAY = drawSpeed.toLong()
        }

        paintBackground.color = Color.BLACK
        paintLogo.colorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        paintLogo.alpha = 0
        paintButtonRing.color = Color.WHITE
        paintButtonRing.style = Paint.Style.STROKE
        paintButtonRing.strokeWidth = 0f
        paintLight.color = Color.WHITE

//        gradient = ReColor(context).getColorIntArray("000000", "ffffff", 100)

        getLogo()
    }

    private fun getLogo() {
        val drawable =
            ContextCompat.getDrawable(context, com.simmorsal.smartnode.R.drawable.logo)

        if (drawable != null) {
            logo = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(logo)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        pcWidth = measuredWidth / 100
        pcHeight = measuredHeight / 100
        isHorizontal = measuredWidth < measuredHeight
        smallSide = if (isHorizontal) pcWidth else pcHeight

        /////////////////
        /////////////////

        rectFBackground.set(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())

        val sizeImage = 20 * smallSide
        rectFLogo.set(
            ((measuredWidth / 2) - (sizeImage / 2)).toFloat(),
            ((measuredHeight / 2) - (sizeImage / 2)).toFloat(),
            ((measuredWidth / 2) + (sizeImage / 2)).toFloat(),
            ((measuredHeight / 2) + (sizeImage / 2)).toFloat()
        )

        val sizeRing = 30 * smallSide
        rectFButtonRing.set(
            ((measuredWidth / 2) - (sizeRing / 2)).toFloat(),
            ((measuredHeight / 2) - (sizeRing / 2)).toFloat(),
            ((measuredWidth / 2) + (sizeRing / 2)).toFloat(),
            ((measuredHeight / 2) + (sizeRing / 2)).toFloat()
        )

        rectFLight.set(rectFButtonRing)
    }

    private var step = 0
    private var stepExpected = 0f
    private lateinit var stepsArrayForButtonClick: FloatArray
    private val rectFLogoB = RectF()
    private var powerToGrowLight = 0f
    var onSplashAnimFinish: OnSplashAnimFinish? = null

    override fun onDraw(canvas: Canvas) {

        canvas.drawRect(rectFBackground, paintBackground)
//        canvas.drawArc(rectFButtonRing, 0f, 360f, false, paintButtonRing)

        when (scene) {
            0f -> { // logo fades in
                step = 0
                stepExpected = getStepCount(LOGO_FADE_SPEED)

                scene = 0.5f
                INVALIDATE_DELAY = 400
                resetInvalidateDelay()
            }

            0.5f -> {
                paintLogo.alpha = ((step / stepExpected) * 255).toInt()

                step++
                if (step == stepExpected.toInt())
                    scene = 1f
            }

            1f -> { // button clicks
                step = 0
                stepExpected = getStepCount(CLICK_SPEED)

                stepsArrayForButtonClick = FloatArray(stepExpected.toInt())
                for (i in 0 until stepExpected.toInt() / 2) {
                    val r = (i * 2) / stepExpected
                    stepsArrayForButtonClick[i] = r
                    stepsArrayForButtonClick[stepExpected.toInt() - 1 - i] = r
                }

                rectFLogoB.set(rectFLogo)

                scene = 1.5f
                INVALIDATE_DELAY = 500
                resetInvalidateDelay()
            }

            1.5f -> {
                paintButtonRing.strokeWidth = (smallSide / 2) * stepsArrayForButtonClick[step]
                canvas.drawArc(rectFButtonRing, 0f, 360f, false, paintButtonRing)
                rectFLogo.set(
                    rectFLogoB.left + ((pcWidth * .6f) * stepsArrayForButtonClick[step]),
                    rectFLogoB.top + ((pcWidth * .6f) * stepsArrayForButtonClick[step]),
                    rectFLogoB.right - ((pcWidth * .6f) * stepsArrayForButtonClick[step]),
                    rectFLogoB.bottom - ((pcWidth * .6f) * stepsArrayForButtonClick[step])
                )

                Log.i("11111", "SplashView => onDraw:  " + pcWidth * stepsArrayForButtonClick[step])

                step++
                if (step == stepExpected.toInt())
                    scene = 2f
            }

            2f -> { // big circle shows and icon recolors
                rectFLogo.set(rectFLogoB)

                scene = 2.5f
                INVALIDATE_DELAY = 200
                resetInvalidateDelay()
            }

            2.5f -> {
                paintLogo.colorFilter = porterDuffBlack

                canvas.drawOval(rectFLight, paintLight)
                scene = 3f
            }

            3f -> { // big circle grows
                step = 1
                stepExpected = getStepCount(LOGO_FADE_SPEED)

                canvas.drawOval(rectFLight, paintLight)

                val theSizeToGrow =
                    if (isHorizontal)
                        pcWidth * 120
                    else
                        pcHeight * 120

                powerToGrowLight = (ln(theSizeToGrow.toDouble())
                        / ln(stepExpected)).toFloat()

                scene = 3.5f
                INVALIDATE_DELAY = 200
                resetInvalidateDelay()
            }

            3.5f -> {
                val r = step.toDouble().pow(powerToGrowLight.toDouble())

                rectFLight.set(
                    (rectFButtonRing.left - r).toFloat(),
                    (rectFButtonRing.top - r).toFloat(),
                    (rectFButtonRing.right + r).toFloat(),
                    (rectFButtonRing.bottom + r).toFloat()
                )

//                Log.i("11111", "SplashView => onDraw: rrr " + r)
                paintLight.maskFilter = BlurMaskFilter(r.toFloat() / 10, BlurMaskFilter.Blur.NORMAL)

                canvas.drawOval(rectFLight, paintLight)

                step++
                if (step == stepExpected.toInt())
                    scene = 4f
            }

            4f -> { // icon fades and next activity loads
                canvas.drawOval(rectFLight, paintLight)
                paintBackground.color = Color.WHITE

                step = 0
                stepExpected = getStepCount(LOGO_FADE_SPEED)

                scene = 4.5f
                INVALIDATE_DELAY = 400
                resetInvalidateDelay()
            }

            4.5f -> {
                paintLogo.alpha = (255 - ((step / stepExpected) * 255)).toInt()

                step++
                if (step == stepExpected.toInt())
                    scene = 5f
            }

            5f -> {
                paintLogo.alpha = 0

                runnableInvalidate = Runnable {}

                handler.postDelayed({
                    context.startActivity(Intent(context, ActivityMain::class.java))
                    onSplashAnimFinish?.onFinish()
                }, 200)
            }
        }


        canvas.drawBitmap(logo, null, rectFLogo, paintLogo)

        super.onDraw(canvas)
        inv()
    }


    private fun inv() {
        handler.postDelayed(runnableInvalidate, INVALIDATE_DELAY)
    }

    private fun resetInvalidateDelay() {
        handler.postDelayed({ INVALIDATE_DELAY = drawSpeed.toLong() }, 10)
    }

    private fun getStepCount(duration: Int): Float {
        return (duration / drawSpeed).toFloat()
    }
}
