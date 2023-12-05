package net.mullvad.mullvadvpn.compose.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import net.mullvad.mullvadvpn.R
import net.mullvad.mullvadvpn.compose.component.AutoConnectCarousel
import net.mullvad.mullvadvpn.compose.component.NavigateBackIconButton
import net.mullvad.mullvadvpn.compose.component.ScaffoldWithLargeTopBarAndButton
import net.mullvad.mullvadvpn.lib.common.util.openVpnSettings
import net.mullvad.mullvadvpn.lib.theme.AppTheme

@Preview
@Composable
private fun PreviewAutoConnectAndLockdownModeScreen() {

    AppTheme {
        AutoConnectAndLockdownModeScreen(
            onBackClick = {},
            toastMessagesSharedFlow = MutableSharedFlow<String>().asSharedFlow()
        )
    }
}

@Composable
fun AutoConnectAndLockdownModeScreen(
    onBackClick: () -> Unit = {},
    toastMessagesSharedFlow: SharedFlow<String>
) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        toastMessagesSharedFlow.distinctUntilChanged().collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    ScaffoldWithLargeTopBarAndButton(
        appBarTitle = stringResource(id = R.string.auto_connect_and_lockdown_mode_two_lines),
        navigationIcon = { NavigateBackIconButton(onBackClick) },
        buttonTitle = stringResource(id = R.string.go_to_vpn_settings),
        onButtonClick = { context.openVpnSettings() },
        content = { modifier -> AutoConnectCarousel() }
    )
}
