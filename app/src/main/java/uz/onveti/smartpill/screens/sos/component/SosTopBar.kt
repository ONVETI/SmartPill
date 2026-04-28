package uz.onveti.smartpill.screens.sos.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.onveti.smartpill.R
import uz.onveti.smartpill.core.ui.SmartPillTopBar
import uz.onveti.smartpill.screens.sos.state.SosAction
import uz.onveti.smartpill.screens.sos.state.SosState

@Composable
internal fun SosTopBar(
    state: SosState,
    onAction: (SosAction) -> Unit,
    modifier: Modifier = Modifier.padding(horizontal = 16.dp),
) {
    SmartPillTopBar(
        title = stringResource(id = R.string.sos_title),
        subtitle = stringResource(id = R.string.topbar_sos_subtitle),
        onNavigateBack = { onAction(SosAction.NavigateBack) },
        modifier = modifier,
    )
}
