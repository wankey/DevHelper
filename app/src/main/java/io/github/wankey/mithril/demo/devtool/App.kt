package io.github.wankey.mithril.demo.devtool

import android.app.Application
import hu.supercluster.paperwork.Paperwork
import io.github.wankey.mithril.developer.DeveloperTool
import io.github.wankey.mithril.developer.Modules
import io.github.wankey.mithril.developer.module.BuildModule
import io.github.wankey.mithril.developer.module.NetworkModule
import io.github.wankey.mithril.developer.module.api.ApiModule
import retrofit2.mock.NetworkBehavior
import timber.log.Timber

/**
 * @author wankey
 */
class App : Application() {
  override fun onCreate() {
    super.onCreate()
    DeveloperTool.init(this)
    Timber.plant(Timber.DebugTree())
    val paperwork = Paperwork(this)
    Modules.addModule(ApiModule(NetworkBehavior.create()), 1)
    Modules.addModule(BuildModule(paperwork.get("gitSha"), paperwork.get("buildDate")))
    Modules.addModule(NetworkModule())
  }
}