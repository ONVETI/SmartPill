package uz.onveti.smartpill.screens.smart_watch.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.onveti.smartpill.R
import uz.onveti.smartpill.core.ui.SmartPillTopBar
import uz.onveti.smartpill.screens.smart_watch.state.SmartWatchAction
import uz.onveti.smartpill.screens.smart_watch.state.SmartWatchState

@Composable
internal fun SmartWatchTopBar(
    state: SmartWatchState,
    onAction: (SmartWatchAction) -> Unit,
    modifier: Modifier = Modifier.padding(horizontal = 16.dp),
) {
    SmartPillTopBar(
        title = stringResource(id = R.string.watch_title),
        subtitle = stringResource(id = R.string.topbar_watch_subtitle),
        onNavigateBack = { onAction(SmartWatchAction.NavigateBack) },
        modifier = modifier,
    )
}
