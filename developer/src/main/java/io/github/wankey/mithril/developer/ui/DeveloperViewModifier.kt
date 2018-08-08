package io.github.wankey.mithril.developer.ui

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.WindowManager
import androidx.drawerlayout.widget.DrawerLayout
import com.jakewharton.madge.MadgeFrameLayout
import com.jakewharton.scalpel.ScalpelFrameLayout
import io.github.wankey.mithril.developer.R
import io.github.wankey.mithril.ui.ViewModifier


/**
 * @author wankey
 */
class DeveloperViewModifier : ViewModifier {

  override fun modify(view: View): View {
    val madgeView = MadgeFrameLayout(view.context)
    val layoutParams = DrawerLayout.LayoutParams((getScreenWidth(view.context) * 0.7).toInt(), MATCH_PARENT)
    layoutParams.gravity = Gravity.END
    val tmpView = if (view is DrawerLayout) {
      view.addView(LayoutInflater.from(view.context).inflate(R.layout.view_developer_setting, view, false), layoutParams)
      view
    } else {
      val drawerLayout = DrawerLayout(view.context)
      drawerLayout.addView(view)
      drawerLayout.addView(LayoutInflater.from(view.context).inflate(R.layout.view_developer_setting, drawerLayout, false), layoutParams)
      drawerLayout
    }

    val scalpelView = ScalpelFrameLayout(view.context)
    scalpelView.addView(tmpView)
    madgeView.addView(scalpelView)
    return madgeView
  }

  private fun getScreenWidth(context: Context): Int {
    val wm: WindowManager? = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (wm == null) {
      context.resources.displayMetrics.widthPixels
    } else {
      val point = Point()
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
      } else {
        wm.defaultDisplay.getSize(point)
      }
      point.x
    }
  }
}