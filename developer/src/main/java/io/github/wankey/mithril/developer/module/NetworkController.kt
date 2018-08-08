package io.github.wankey.mithril.developer.module

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.lang.ref.WeakReference


/**
 * @author wankey
 */
object NetworkController {
  lateinit var contextRef: WeakReference<Context>

  enum class BluetoothState {
    ON, OFF, TURNING_ON, TURNING_OFF, UNKNOWN
  }

  interface OnNetworkChangedListener {
    fun onChanged(event: NetworkChangeEvent)
  }

  private var receiver: NetworkReceiver? = null

  var onNetworkChangedListener: OnNetworkChangedListener? = null

  /**
   * Register network state broadcast receiver
   */
  fun registerReceiver() {
    if (receiver == null) {
      receiver = NetworkReceiver(object : Listener {
        override fun post(event: NetworkChangeEvent) {
          onNetworkChangedListener?.onChanged(event)
        }
      })
    }
    val context = contextRef.get()
    if (context != null) {
      val filter = IntentFilter()
      filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
      filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
      context.registerReceiver(receiver, filter)
    }
  }

  /**
   * Unregister network state broadcast receiver
   */
  fun unregisterReceiver() {
    try {
      contextRef.get()?.unregisterReceiver(receiver)
    } catch (e: IllegalArgumentException) {
    }

  }

  private fun hasBluetoothPermission(context: Context): Boolean {
    return context.checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED
  }

  /**
   * Receiver that handles wifi, mobile networks and
   * Bluetooth connectivity change intents and sends
   * a NetworkChangeEvent using listener
   *
   */
  internal class NetworkReceiver(private val listener: Listener?) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
      if (listener != null) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val bluetoothInfo = BluetoothAdapter.getDefaultAdapter()

        val networkChangeEvent = NetworkChangeEvent(
            if (wifiInfo != null) wifiInfo.state else NetworkInfo.State.UNKNOWN,
            if (mobileInfo != null) mobileInfo.state else NetworkInfo.State.UNKNOWN,
            if (bluetoothInfo != null && hasBluetoothPermission(context)) getBluetoothState(bluetoothInfo.state) else BluetoothState.UNKNOWN)

        listener.post(networkChangeEvent)
      }

    }

    /**
     * Converts Bluetooth state representation to an Enum
     *
     * @param state
     */
    private fun getBluetoothState(state: Int): BluetoothState {
      when (state) {
        BluetoothAdapter.STATE_ON -> return BluetoothState.ON
        BluetoothAdapter.STATE_OFF -> return BluetoothState.OFF
        BluetoothAdapter.STATE_TURNING_ON -> return BluetoothState.TURNING_ON
        BluetoothAdapter.STATE_TURNING_OFF -> return BluetoothState.TURNING_OFF
      }

      return BluetoothState.UNKNOWN
    }
  }

  class NetworkChangeEvent(val wifiState: NetworkInfo.State, val mobileState: NetworkInfo.State, val bluetoothState: BluetoothState)

  internal interface Listener {
    fun post(event: NetworkChangeEvent)
  }
}