package io.github.wankey.mithril

import android.view.View

/**
 * @author wankey
 */
interface ViewModifier {
  fun modify(view: View): View

  companion object {
    val defaultViewModifier: ViewModifier  by lazy {
      DefaultViewModifier()
    }

    class DefaultViewModifier : ViewModifier {
      override fun modify(view: View): View = view
    }
  }
}