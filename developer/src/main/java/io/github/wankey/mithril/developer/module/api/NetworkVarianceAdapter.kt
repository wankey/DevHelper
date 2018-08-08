package io.github.wankey.mithril.developer.module.api

import android.content.Context
import android.view.View
import android.widget.TextView
import io.github.wankey.mithril.developer.ui.BindableAdapter


/**
 * @author wankey
 */
class NetworkVarianceAdapter(context: Context) : BindableAdapter<Int>(context) {
  private val VALUES = intArrayOf(20, 40, 60)

  fun getPositionForValue(value: Int): Int {
    for (i in 0 until VALUES.size) {
      if (VALUES[i] == value) {
        return i
      }
    }
    return 0
  }

  override fun getItem(position: Int): Int = VALUES[position]

  override fun getCount(): Int = VALUES.size

  override fun onBindView(item: Int, position: Int, view: View) {
    val tv = view.findViewById(android.R.id.text1) as TextView
    tv.text = "±$item%"
  }

}