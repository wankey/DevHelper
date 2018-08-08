package io.github.wankey.mithril.developer.module.api

import android.content.Context
import android.view.View
import android.widget.TextView
import io.github.wankey.mithril.developer.ui.BindableAdapter


/**
 * @author wankey
 */
class ProxyAdapter(context: Context, private val currentProxy: String?) : BindableAdapter<String>(context) {

  fun getPosition(): Int {
    return if (currentProxy == null) 0 else 1
  }

  override fun getItem(position: Int): String {
    if (position == 0) {
      return "None"
    }

    if (position == count - 1) {
      return "Set…"
    }

    return currentProxy.toString()
  }

  override fun onBindView(item: String, position: Int, view: View) {
    val tv = view.findViewById(android.R.id.text1) as TextView
    tv.text = item
  }

  override fun getCount(): Int {
    return if (currentProxy == null) 2 else 3
  }

}