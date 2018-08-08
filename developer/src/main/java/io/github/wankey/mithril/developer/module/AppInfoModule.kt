package io.github.wankey.mithril.developer.module

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.wankey.mithril.developer.R

/**
 * @author wankey
 */
class AppInfoModule : DeveloperModule {
  private lateinit var tvAppName: TextView

  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
    val view = inflater.inflate(R.layout.partial_app_info, parent, true)
    tvAppName = view.findViewById(R.id.tv_app_name)
    refresh()
    return view
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

  fun refresh() {
    tvAppName.setText(R.string.app_name)
  }
}