package io.github.wankey.mithril.developer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.Space
import androidx.fragment.app.Fragment
import io.github.wankey.mithril.developer.Modules
import io.github.wankey.mithril.developer.R
import kotlinx.android.synthetic.main.fragment_developer.dev_module_container

/**
 * @author wankey
 */
class DeveloperFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_developer, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    for (module in Modules.modules) {
      val inflater = LayoutInflater.from(context)
      module.onCreateView(inflater, dev_module_container)
      dev_module_container.addView(generateDividerView())
    }
  }

  override fun onStart() {
    super.onStart()
    for (module in Modules.modules) {
      module.onStart()
    }
  }

  override fun onStop() {
    super.onStop()
    for (module in Modules.modules) {
      module.onStop()
    }
  }

  private fun generateDividerView(): View {
    val view = Space(context)
    val layoutParam = LinearLayout.LayoutParams(MATCH_PARENT, resources.getDimensionPixelSize(R.dimen.activity_vertical_margin))
    view.layoutParams = layoutParam
    return view
  }

}