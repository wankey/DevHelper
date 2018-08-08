package io.github.wankey.mithril.developer.module

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.annotations.NotNull

/**
 * @author wankey
 */
interface DeveloperModule {
  @NotNull
  fun onCreateView(@NotNull inflater: LayoutInflater, @NotNull parent: ViewGroup): View

  fun onOpened()

  fun onClosed()

  fun onResume()

  fun onPause()

  fun onStart()

  fun onStop()
}