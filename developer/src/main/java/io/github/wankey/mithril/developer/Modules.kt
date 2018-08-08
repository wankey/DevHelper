package io.github.wankey.mithril.developer

import io.github.wankey.mithril.developer.module.AppInfoModule
import io.github.wankey.mithril.developer.module.DeveloperModule
import io.github.wankey.mithril.developer.module.DeviceModule
import io.github.wankey.mithril.developer.module.SettingModule
import io.github.wankey.mithril.developer.module.ThirdPartyModule

/**
 * @author wankey
 */
object Modules {
  var modules: ArrayList<DeveloperModule> = arrayListOf(AppInfoModule(), DeviceModule(), ThirdPartyModule(), SettingModule())

  fun <T : DeveloperModule> addModule(module: T, index: Int = modules.size) {
    modules.add(index, module)
  }

}