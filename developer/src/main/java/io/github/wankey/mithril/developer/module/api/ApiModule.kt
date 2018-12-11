package io.github.wankey.mithril.developer.module.api

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import com.jakewharton.processphoenix.ProcessPhoenix
import io.github.wankey.mithril.developer.R
import io.github.wankey.mithril.developer.module.DeveloperModule
import io.github.wankey.mithril.developer.ui.OnItemSelectedListener
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit.MILLISECONDS


/**
 * @author wankey
 */
class ApiModule(private val networkBehavior: NetworkBehavior) : DeveloperModule, OnItemSelectedListener {

  companion object {
    const val KEY_ENDPOINT = "dev_endpoint"
    const val KEY_DELAY = "dev_delay"
    const val KEY_VARIANCE = "dev_variance"
    const val KEY_ERROR = "dev_error"
    const val KEY_PROXY_IP = "dev_proxy_ip"
    const val KEY_PROXY_PORT = "dev_proxy_port"
  }

  lateinit var context: Context
  lateinit var spinnerEndpoint: Spinner
  lateinit var spinnerDelay: Spinner
  lateinit var spinnerVariance: Spinner
  lateinit var spinnerError: Spinner
  lateinit var spinnerProxy: Spinner

  private lateinit var endpointAdapter: EndpointAdapter
  private lateinit var delayAdapter: NetworkDelayAdapter
  private lateinit var varianceAdapter: NetworkVarianceAdapter
  private lateinit var errorAdapter: NetworkErrorAdapter
  private lateinit var proxyAdapter: ProxyAdapter

  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
    val view = inflater.inflate(R.layout.partial_api, parent, true)

    context = view.context
    spinnerEndpoint = view.findViewById(R.id.spinner_endpoint) as Spinner
    spinnerDelay = view.findViewById(R.id.spinner_delay) as Spinner
    spinnerVariance = view.findViewById(R.id.spinner_variance) as Spinner
    spinnerError = view.findViewById(R.id.spinner_error) as Spinner
    spinnerProxy = view.findViewById(R.id.spinner_proxy) as Spinner

    val currentEndpoint: String? = context.getStringPref(KEY_ENDPOINT)
    endpointAdapter = EndpointAdapter(context)
    spinnerEndpoint.adapter = endpointAdapter
    spinnerEndpoint.setSelection(endpointAdapter.getPositionForValue(currentEndpoint), false)

    val currentDelay: Long = context.getLongPref(KEY_DELAY)
    delayAdapter = NetworkDelayAdapter(context)
    spinnerDelay.adapter = delayAdapter
    spinnerDelay.setSelection(delayAdapter.getPositionForValue(currentDelay), false)

    val currentVariance: Int = context.getIntPref(KEY_VARIANCE)
    varianceAdapter = NetworkVarianceAdapter(context)
    spinnerVariance.adapter = varianceAdapter
    spinnerVariance.setSelection(varianceAdapter.getPositionForValue(currentVariance), false)

    val currentError: Int = context.getIntPref(KEY_ERROR)
    errorAdapter = NetworkErrorAdapter(context)
    spinnerError.adapter = errorAdapter
    spinnerError.setSelection(errorAdapter.getPositionForValue(currentError), false)

    val currentProxyIp: String? = context.getStringPref(KEY_PROXY_IP, null)
    val currentProxyPort: String? = context.getStringPref(KEY_PROXY_PORT, null)
    proxyAdapter = ProxyAdapter(context, if (currentProxyIp == null) null else "$currentProxyIp:$currentProxyPort")
    spinnerProxy.adapter = proxyAdapter
    spinnerProxy.setSelection(proxyAdapter.getPosition(), false)

    spinnerEndpoint.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      var fromUser = false
      override fun onNothingSelected(parent: AdapterView<*>?) {
      }

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when {
          !fromUser -> fromUser = true
          else -> this@ApiModule.onItemSelected(parent, view, position, id)
        }
      }
    }
    spinnerDelay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      var fromUser = false

      override fun onNothingSelected(parent: AdapterView<*>?) {
      }

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when {
          !fromUser -> fromUser = true
          else -> this@ApiModule.onItemSelected(parent, view, position, id)
        }
      }
    }
    spinnerVariance.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      var fromUser = false

      override fun onNothingSelected(parent: AdapterView<*>?) {
      }

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when {
          !fromUser -> fromUser = true
          else -> this@ApiModule.onItemSelected(parent, view, position, id)
        }
      }
    }
    spinnerError.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      var fromUser = false

      override fun onNothingSelected(parent: AdapterView<*>?) {
      }

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when {
          !fromUser -> fromUser = true
          else -> this@ApiModule.onItemSelected(parent, view, position, id)
        }
      }
    }
    spinnerProxy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      var fromUser = false
      override fun onNothingSelected(parent: AdapterView<*>?) {
      }

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when {
          !fromUser -> fromUser = true
          else -> this@ApiModule.onItemSelected(parent, view, position, id)
        }
      }
    }

    when (currentEndpoint) {
      EndpointAdapter.DEFAULT_MOCK_MODE -> {
        spinnerDelay.isEnabled = true
        spinnerVariance.isEnabled = true
        spinnerError.isEnabled = true
        spinnerProxy.isEnabled = false
      }
      else -> {
        spinnerDelay.isEnabled = false
        spinnerVariance.isEnabled = false
        spinnerError.isEnabled = false
        spinnerProxy.isEnabled = true
      }
    }

    return view
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

  override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
    when (adapterView) {
      spinnerEndpoint -> {
        context.putStringPref(KEY_ENDPOINT, endpointAdapter.getItem(position))
        spinnerEndpoint.postDelayed(fun() {
          ProcessPhoenix.triggerRebirth(context)
        }, 200)
      }
      spinnerDelay -> {
        val value = delayAdapter.getItem(position)
        context.putLongPref(KEY_DELAY, value)
        networkBehavior.setDelay(value, MILLISECONDS)
      }
      spinnerVariance -> {
        val value = varianceAdapter.getItem(position)
        context.putIntPref(KEY_VARIANCE, value)
        networkBehavior.setVariancePercent(value)
      }
      spinnerError -> {
        val value = errorAdapter.getItem(position)
        context.putIntPref(KEY_ERROR, value)
        networkBehavior.setErrorPercent(value)
      }
      spinnerProxy -> {
        when (position) {
          0 -> {
            context.deletePref(KEY_PROXY_IP)
            context.deletePref(KEY_PROXY_PORT)
            spinnerProxy.postDelayed({ ProcessPhoenix.triggerRebirth(context) }, 200)
          }
          else -> {
            showProxyInputDialog()
          }
        }
      }
      else -> {
      }
    }

  }

  private fun showProxyInputDialog() {
    val customizeDialog = AlertDialog.Builder(context)
    val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_proxy_input, null)
    customizeDialog.setTitle("Set Proxy")
    customizeDialog.setView(dialogView)
    customizeDialog.setPositiveButton(android.R.string.ok) { dialogInterface, _ ->
      run {
        val etIp: EditText = dialogView.findViewById(R.id.et_ip)
        val etPort: EditText = dialogView.findViewById(R.id.et_port)
        context.putStringPref(KEY_PROXY_IP, etIp.text.toString())
        context.putStringPref(KEY_PROXY_PORT, etPort.text.toString())
        dialogInterface.dismiss()
        val result = dialogView.postDelayed({ ProcessPhoenix.triggerRebirth(context) }, 200)

      }
    }
    customizeDialog.setNegativeButton(android.R.string.cancel) { dialogInterface, _ ->
      run {
        dialogInterface.dismiss()
      }
    }
    customizeDialog.show()
  }


  private fun Context.getStringPref(name: String, defaultValue: String? = null): String? {
    val sharedPreferences = getSharedPreferences(packageName, android.content.Context.MODE_PRIVATE)
    return sharedPreferences.getString(name, defaultValue)
  }

  private fun Context.getLongPref(name: String, defaultValue: Long = 0L): Long {
    val sharedPreferences = getSharedPreferences(packageName, android.content.Context.MODE_PRIVATE)
    return sharedPreferences.getLong(name, defaultValue)
  }

  private fun Context.getIntPref(name: String, defaultValue: Int = 0): Int {
    val sharedPreferences = getSharedPreferences(packageName, android.content.Context.MODE_PRIVATE)
    return sharedPreferences.getInt(name, defaultValue)
  }

  private fun Context.putStringPref(name: String, value: String? = null) {
    val sharedPreferences = getSharedPreferences(packageName, android.content.Context.MODE_PRIVATE)
    sharedPreferences.edit().putString(name, value).apply()
  }

  private fun Context.putLongPref(name: String, value: Long = 0L) {
    val sharedPreferences = getSharedPreferences(packageName, android.content.Context.MODE_PRIVATE)
    sharedPreferences.edit().putLong(name, value).apply()
  }

  private fun Context.putIntPref(name: String, value: Int = 0) {
    val sharedPreferences = getSharedPreferences(packageName, android.content.Context.MODE_PRIVATE)
    sharedPreferences.edit().putInt(name, value).apply()
  }

  private fun Context.deletePref(name: String) {
    val sharedPreferences = getSharedPreferences(packageName, android.content.Context.MODE_PRIVATE)
    sharedPreferences.edit().remove(name).apply()
  }
}