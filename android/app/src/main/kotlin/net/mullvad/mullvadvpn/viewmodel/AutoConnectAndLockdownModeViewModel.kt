package net.mullvad.mullvadvpn.viewmodel

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import net.mullvad.mullvadvpn.R

class AutoConnectAndLockdownModeViewModel(private val resources: Resources) : ViewModel() {

    private val _toastMessages = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val toastMessages = _toastMessages.asSharedFlow()

    fun showNoVpnSettingWarningToast() {
        _toastMessages.tryEmit(resources.getString(R.string.vpn_setting))
    }
}
