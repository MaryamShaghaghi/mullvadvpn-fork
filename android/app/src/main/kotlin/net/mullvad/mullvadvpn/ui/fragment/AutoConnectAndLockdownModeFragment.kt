package net.mullvad.mullvadvpn.ui.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import net.mullvad.mullvadvpn.R
import net.mullvad.mullvadvpn.compose.screen.AutoConnectAndLockdownModeScreen
import net.mullvad.mullvadvpn.lib.theme.AppTheme
import net.mullvad.mullvadvpn.viewmodel.AutoConnectAndLockdownModeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AutoConnectAndLockdownModeFragment : BaseFragment() {
    private val vm by viewModel<AutoConnectAndLockdownModeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_compose, container, false).apply {
            findViewById<ComposeView>(R.id.compose_view).setContent {
                AppTheme {
                    AutoConnectAndLockdownModeScreen(
                        onBackClick = { activity?.onBackPressedDispatcher?.onBackPressed() },
                        toastMessagesSharedFlow = vm.toastMessages
                    )
                }
            }
        }
    }

    private fun openVpnSettings() {
        try {
            val intent = Intent("android.settings.VPN_SETTINGS")

            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            }
        } catch (e: ActivityNotFoundException) {
            vm.showNoVpnSettingWarningToast()
        }
    }
}
