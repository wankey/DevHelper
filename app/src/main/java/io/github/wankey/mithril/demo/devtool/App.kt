package io.github.wankey.mithril.demo.devtool

import android.app.Application
import io.github.wankey.mithril.developer.DeveloperTool
import timber.log.Timber

/**
 * @author wankey
 */
class App : Application() {
  override fun onCreate() {
    super.onCreate()
    DeveloperTool.init(this)
    Timber.plant(Timber.DebugTree())
  }
}