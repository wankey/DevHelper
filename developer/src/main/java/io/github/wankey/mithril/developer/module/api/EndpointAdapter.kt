package io.github.wankey.mithril.developer.module.api

import android.content.Context
import android.view.View
import android.widget.TextView
import io.github.wankey.mithril.developer.ui.BindableAdapter

/**
 * @author wankey
 */
class EndpointAdapter(context: Context) : BindableAdapter<String>(context) {
  companion object {
    const val DEFAULT_MOCK_MODE = "Mock Mode"
  }

  private val endpoints = with(context) {
    val tmp = resources.getStringArray(resources.getIdentifier("$packageName:array/endpoint_array", null, null))
    tmp.plus(DEFAULT_MOCK_MODE)
  }

  fun getPositionForValue(value: String?): Int {
    if (value == null) {
      return 0
    }
    return (0 until endpoints.size).firstOrNull { endpoints[it] == value } ?: 0
  }

  override fun onBindView(item: String, position: Int, view: View) {
    val tv = view.findViewById(android.R.id.text1) as TextView
    tv.text = item
  }

  override fun getItem(position: Int): String = endpoints[position]

  override fun getCount(): Int = endpoints.size
}