package io.github.wankey.mithril.developer

import android.app.Application
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.AndroidHeapDumper
import com.squareup.leakcanary.HeapDumper
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.squareup.leakcanary.internal.LeakCanaryInternals
import hu.supercluster.paperwork.Paperwork
import io.github.wankey.mithril.developer.module.BuildModule
import io.github.wankey.mithril.developer.module.NetworkModule
import io.github.wankey.mithril.developer.module.api.ApiModule
import retrofit2.mock.NetworkBehavior
import java.io.File


/**
 * @author wankey
 */
object DeveloperTool {
  var refWatcher: RefWatcher? = null
  private var heapDumper: TogglableHeapDumper? = null

  fun init(app: Application) {
    initLeakCanary(app)
    Stetho.initializeWithDefaults(app)
    val paperwork = Paperwork(app)
    Modules.addModule(ApiModule(NetworkBehavior.create()), 1)
    Modules.addModule(BuildModule(paperwork.get("gitSha"), paperwork.get("buildDate")))
    Modules.addModule(NetworkModule())
  }


  private fun initLeakCanary(app: Application) {
    if (LeakCanary.isInAnalyzerProcess(app)) {
      return
    }
    val leakDirectoryProvider = LeakCanaryInternals.getLeakDirectoryProvider(app)
    val defaultDumper = AndroidHeapDumper(app, leakDirectoryProvider)
    heapDumper = TogglableHeapDumper(defaultDumper)
    refWatcher = LeakCanary.refWatcher(app)
        .heapDumper(heapDumper)
        .buildAndInstall()
  }

  fun toggleLeakCanary(enable: Boolean) {
    heapDumper?.setEnable(enable)
  }

  class TogglableHeapDumper(private val defaultDumper: HeapDumper) : HeapDumper {
    private var enabled = true

    fun toggle() {
      enabled = !enabled
    }

    override fun dumpHeap(): File {
      return if (enabled) defaultDumper.dumpHeap() else HeapDumper.RETRY_LATER
    }

    fun setEnable(enable: Boolean) {
      this.enabled = enable
    }
  }

  fun watch(obj: Any) {
    refWatcher?.watch(obj)
  }
}