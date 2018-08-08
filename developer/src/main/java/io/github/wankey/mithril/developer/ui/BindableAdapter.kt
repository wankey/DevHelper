package io.github.wankey.mithril.developer.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

/**
 * @author wankey
 */
abstract class BindableAdapter<T>(context: Context) : BaseAdapter() {
  var inflater: LayoutInflater = LayoutInflater.from(context)

  override fun getView(position: Int, convertView: View?, container: ViewGroup?): View {
    val resultView: View = convertView ?: onCreateView(inflater, container)
    onBindView(getItem(position), position, resultView)
    return resultView
  }

  override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
    val resultView: View = convertView ?: newDropDownView(inflater, parent)
    onBindView(getItem(position), position, resultView)
    return resultView
  }

  abstract override fun getItem(position: Int): T

  override fun getItemId(position: Int): Long = position.toLong()

  abstract fun onBindView(item: T, position: Int, view: View)

  fun onBindDropDownView(item: T, position: Int, view: View) {
    onBindView(item, position, view)
  }

  open fun newDropDownView(inflater: LayoutInflater, container: ViewGroup?): View {
    return inflater.inflate(android.R.layout.simple_spinner_dropdown_item, container, false)
  }

  open fun onCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
    return inflater.inflate(android.R.layout.simple_spinner_item, container, false)
  }
}