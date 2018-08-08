package io.github.wankey.mithril.developer.module.api

import android.content.Context
import android.view.View
import android.widget.TextView
import io.github.wankey.mithril.developer.ui.BindableAdapter


/**
 * @author wankey
 */
class NetworkErrorAdapter(context: Context) : BindableAdapter<Int>(context) {
  private val VALUES = intArrayOf(0, 3, 10, 25, 50, 75, 100)

  fun getPositionForValue(value: Int): Int {
    for (i in 0 until VALUES.size) {
      if (VALUES[i] == value) {
        return i
      }
    }
    return 1
  }

  override fun getItem(position: Int): Int = VALUES[position]

  override fun getCount(): Int = VALUES.size

  override fun onBindView(item: Int, position: Int, view: View) {
    val tv = view.findViewById(android.R.id.text1) as TextView
    tv.text = "$item%"
  }

}