package uz.onveti.smartpill.screens.support.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.onveti.smartpill.R
import uz.onveti.smartpill.core.ui.SmartPillTopBar
import uz.onveti.smartpill.screens.support.state.SupportAction
import uz.onveti.smartpill.screens.support.state.SupportState

@Composable
internal fun SupportTopBar(
    state: SupportState,
    onAction: (SupportAction) -> Unit,
    modifier: Modifier = Modifier.padding(horizontal = 16.dp),
) {
    SmartPillTopBar(
        title = stringResource(id = R.string.support_title),
        subtitle = stringResource(id = R.string.topbar_support_subtitle),
        onNavigateBack = { onAction(SupportAction.NavigateBack) },
        modifier = modifier,
    )
}
