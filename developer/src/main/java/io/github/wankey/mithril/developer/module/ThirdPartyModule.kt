package io.github.wankey.mithril.developer.module

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.Switch
import com.jakewharton.madge.MadgeFrameLayout
import com.jakewharton.scalpel.ScalpelFrameLayout
import io.github.wankey.mithril.developer.DeveloperTool
import io.github.wankey.mithril.developer.R
import kotlin.reflect.KClass

/**
 * @author wankey
 */
class ThirdPartyModule : DeveloperModule, OnCheckedChangeListener {

  lateinit var switchLeakCanary: Switch
  lateinit var switchMadge: Switch
  lateinit var containerMadge: View
  lateinit var switchMadgeRatio: Switch
  lateinit var switchScalpel: Switch
  lateinit var containerScalpel: View
  lateinit var switchScalpelInteraction: Switch
  lateinit var switchScalpelDrawView: Switch
  lateinit var switchScalpelDrawViewId: Switch
  lateinit var context: Context
  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
    val view = inflater.inflate(R.layout.partial_thirdparty_tool, parent, true)
    context = view.context
    switchLeakCanary = view.findViewById(R.id.switch_leak_canary)
    switchMadge = view.findViewById(R.id.switch_madge)
    containerMadge = view.findViewById(R.id.container_madge)
    switchMadgeRatio = view.findViewById(R.id.switch_madge_ratio)
    switchScalpel = view.findViewById(R.id.switch_scalpel)
    switchScalpelInteraction = view.findViewById(R.id.switch_scalpel_interaction)
    switchScalpelDrawView = view.findViewById(R.id.switch_scalpel_wireframe)
    switchScalpelDrawViewId = view.findViewById(R.id.switch_scalpel_view_id)
    containerScalpel = view.findViewById(R.id.container_scalpel)

    switchLeakCanary.setOnCheckedChangeListener(this)
    switchMadge.setOnCheckedChangeListener(this)
    switchScalpel.setOnCheckedChangeListener(this)
    switchScalpelInteraction.setOnCheckedChangeListener(this)
    switchScalpelDrawView.setOnCheckedChangeListener(this)
    switchScalpelDrawViewId.setOnCheckedChangeListener(this)
    refresh()
    return view
  }

  override fun onCheckedChanged(p0: CompoundButton?, checked: Boolean) {
    when (p0) {
      switchLeakCanary -> {
        DeveloperTool.toggleLeakCanary(checked)
      }
      switchMadge -> {
        val view = (context as Activity).findViewById<View>(android.R.id.content)
        val madgeView = findView(view, MadgeFrameLayout::class)
        if (madgeView != null) {
          (madgeView as MadgeFrameLayout).isOverlayEnabled = checked
        }
        containerMadge.visibility = if (checked) View.VISIBLE else View.GONE
      }
      switchMadgeRatio -> {
        val view = (context as Activity).findViewById<View>(android.R.id.content)
        val madgeView = findView(view, MadgeFrameLayout::class)
        if (madgeView != null) {
          (madgeView as MadgeFrameLayout).isOverlayRatioEnabled = checked
        }
      }
      switchScalpel -> {
        containerScalpel.visibility = if (checked) View.VISIBLE else View.GONE
        if (!checked) {
          val view = (context as Activity).findViewById<View>(android.R.id.content)
          val scalpelFrameLayout = findView(view, ScalpelFrameLayout::class)
          (scalpelFrameLayout as ScalpelFrameLayout).isLayerInteractionEnabled = false
          switchScalpelDrawView.isEnabled = false
          switchScalpelDrawViewId.isEnabled = false
          switchScalpelInteraction.isChecked = false
        }
      }
      switchScalpelInteraction -> {
        val view = (context as Activity).findViewById<View>(android.R.id.content)
        val scalpelFrameLayout = findView(view, ScalpelFrameLayout::class)
        (scalpelFrameLayout as ScalpelFrameLayout).isLayerInteractionEnabled = checked
        switchScalpelDrawView.isEnabled = checked
        switchScalpelDrawViewId.isEnabled = checked
      }
      switchScalpelDrawView -> {
        val view = (context as Activity).findViewById<View>(android.R.id.content)
        val scalpelFrameLayout = findView(view, ScalpelFrameLayout::class)
        (scalpelFrameLayout as ScalpelFrameLayout).setDrawViews(checked)
      }
      switchScalpelDrawViewId -> {
        val view = (context as Activity).findViewById<View>(android.R.id.content)
        val scalpelFrameLayout = findView(view, ScalpelFrameLayout::class)
        (scalpelFrameLayout as ScalpelFrameLayout).setDrawIds(checked)
      }
    }
  }

  private fun findView(view: View, kClass: KClass<*>): View? {
    if (view::class == kClass) {
      return view
    } else if (view is ViewGroup) {
      val childCount = view.childCount
      for (idx in 0..childCount) {
        return findView(view.getChildAt(idx), kClass)
      }
    }
    return null
  }

  private fun refresh() {

  }

  override fun onOpened() {
  }

  override fun onClosed() {
  }

  override fun onResume() {
  }

  override fun onPause() {
  }

  override fun onStart() {
  }

  override fun onStop() {
  }
}