package dem.vac.taptargetviewdemo

import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.getkeepsafe.taptargetview.TapTargetView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var drawable:Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawable = resources.getDrawable(R.drawable.downbtn)

        tvshow.setOnClickListener {
            ViewTap(tvshow,"我是左上角的TextView","TapTarget这个控件挺好用")
        }

        btn1.setOnClickListener {
            ViewTap(btn1,btn1.text.toString(),"btn1的按钮")
        }

        btn2.setOnClickListener {
            ViewTap(btn2,btn2.text.toString(),"孔径的XXX")
        }

        edttext.setOnClickListener {
            ViewTap(edttext,edttext.text.toString(),"EditText")
        }

        btncenter.setOnClickListener {
            SequencesTap()
        }

        tvcenter.setOnClickListener {
            tvshowTapTarget()
        }

    }

    private fun SequencesTap(){
        val sequence= TapTargetSequence(this)
            .targets(TapTarget.forView(tvshow,"左上角标题"),
                TapTarget.forView(btn1,"btn1的说明","我来做说明")
                    .drawShadow(true).tintTarget(false)
                    .icon(drawable),
            TapTarget.forView(edttext,"我是文本框","我来做文本框说明"),
                TapTarget.forBounds(Rect(100,100,100,200),"我是一个矩 形")
                    .cancelable(false))
            .listener(object : TapTargetSequence.Listener{
                override fun onSequenceCanceled(lastTarget: TapTarget?) {
                    Toast.makeText(this@MainActivity,"canceled",Toast.LENGTH_SHORT).show()
                }

                override fun onSequenceFinish() {
                    Toast.makeText(this@MainActivity,"finish",Toast.LENGTH_SHORT).show()
                }

                override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {
                    Toast.makeText(this@MainActivity,"targetclicked:"+targetClicked.toString()+" lasttarget:"+ lastTarget?.let{ it.id()},Toast.LENGTH_SHORT).show()
                }
            })
        sequence.start()
    }


    private fun ViewTap(view: View, title: String, desc: String) {
        TapTargetView.showFor(this,
            TapTarget.forView(view,title,desc)
                .drawShadow(true))
    }

    private fun tvshowTapTarget() {
        TapTargetView.showFor(
            this, //当前的activity
            TapTarget.forView(tvshow, "这里输入标题", "这里输入说明")
                //外圈圆的颜色
                .outerCircleColor(R.color.colorDefBlue)
                    //外圈圆的透明度
                .outerCircleAlpha(0.9f)
                    //当前设置控件的圆的颜色
                .targetCircleColor(R.color.colorTransBlue)
                    //标题字体大小
                .titleTextSize(20)
                    //说明字体大小
                .descriptionTextSize(12)
                    //字体颜色
                .textColor(R.color.colorBlack)
                .textTypeface(Typeface.SANS_SERIF)
                    //阴影色
                .dimColor(R.color.colorWhite)
                    //选中目标的半径范围
                .targetRadius(70),
                    //点击目标的事件
            object : TapTargetView.Listener() {
                override fun onTargetClick(view: TapTargetView?) {
                    super.onTargetClick(view)
                    Toast.makeText(this@MainActivity, "我是弹窗", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}
