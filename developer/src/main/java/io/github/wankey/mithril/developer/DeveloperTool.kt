package io.github.wankey.mithril.developer

import android.app.Application
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary

/**
 * @author wankey
 */
object DeveloperTool {
  fun init(application: Application) {
    LeakCanary.install(application)
    Stetho.initializeWithDefaults(application)
  }
}