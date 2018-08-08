package io.github.wankey.mithril.developer.module.api

import android.content.Context
import android.view.View
import android.widget.TextView
import io.github.wankey.mithril.developer.ui.BindableAdapter


/**
 * @author wankey
 */
class NetworkDelayAdapter(context: Context) : BindableAdapter<Long>(context) {
  private val VALUES = longArrayOf(250, 500, 1000, 2000, 3000, 5000)

  fun getPositionForValue(value: Long): Int {
    for (i in 0 until VALUES.size) {
      if (VALUES[i] == value) {
        return i
      }
    }
    return 3
  }

  override fun getItem(position: Int): Long = VALUES[position]

  override fun getCount(): Int = VALUES.size

  override fun onBindView(item: Long, position: Int, view: View) {
    val tv = view.findViewById(android.R.id.text1) as TextView
    tv.text = "${item}ms"
  }

}