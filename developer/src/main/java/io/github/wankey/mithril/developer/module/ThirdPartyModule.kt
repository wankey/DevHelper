package io.github.wankey.mithril.developer.module

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.Switch
import io.github.wankey.mithril.developer.R

/**
 * @author wankey
 */
class ThirdPartyModule : DeveloperModule, OnCheckedChangeListener {

  lateinit var switchLeakCanary: Switch
  lateinit var switchMadge: Switch
  lateinit var switchScalpel: Switch
  lateinit var containerScalpel: View
  lateinit var switchScalpelInteraction: Switch
  lateinit var switchScalpelDrawView: Switch
  lateinit var switchScalpelDrawViewId: Switch

  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
    val view = inflater.inflate(R.layout.partial_thirdparty_tool, parent, true)
    switchLeakCanary = view.findViewById(R.id.switch_leak_canary)
    switchMadge = view.findViewById(R.id.switch_madge)
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

  override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
    when (p0) {
      switchLeakCanary -> {
      }
      switchMadge -> {
      }
      switchScalpel -> {
        containerScalpel.visibility = if (p1) View.VISIBLE else View.GONE
      }
      switchScalpelInteraction -> {
      }
      switchScalpelDrawView -> {
      }
      switchScalpelDrawViewId -> {
      }
    }
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