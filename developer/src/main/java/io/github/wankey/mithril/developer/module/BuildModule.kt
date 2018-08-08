package io.github.wankey.mithril.developer.module

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.wankey.mithril.developer.BuildConfig
import io.github.wankey.mithril.developer.R

/**
 * @author wankey
 */
class BuildModule(private val gitSha: String, private val buildTime: String) : DeveloperModule {

  lateinit var context: Context
  lateinit var tvVersion: TextView
  lateinit var tvName: TextView
  lateinit var tvPackage: TextView
  lateinit var tvGitSHa: TextView
  lateinit var tvBuildTime: TextView

  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
    val view = inflater.inflate(R.layout.partial_build_info, parent, true)
    context = view.context
    tvVersion = view.findViewById(R.id.tv_version)
    tvName = view.findViewById(R.id.tv_name)
    tvPackage = view.findViewById(R.id.tv_package)
    tvGitSHa = view.findViewById(R.id.tv_git_sha)
    tvBuildTime = view.findViewById(R.id.tv_build_time)
    refresh()
    return view
  }

  private fun refresh() {
    tvVersion.text = BuildConfig.VERSION_CODE.toString()
    tvName.text = BuildConfig.VERSION_NAME
    tvGitSHa.text = gitSha
    tvPackage.text = context.packageName
    tvBuildTime.text = buildTime
  }

  override fun onOpened() {
  }

  override fun onClosed() {
  }

  override fun onResume() {
  }

  override fun onPause() {
  }

  override fun onStart() {
  }

  override fun onStop() {
  }

}