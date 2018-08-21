package io.github.wankey.mithril.developer.ui

import android.view.View
import android.widget.AdapterView

/**
 * @author wankey
 */
interface OnItemSelectedListener {
  fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
}