package uz.onveti.smartpill.screens.pharmacies.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.onveti.smartpill.R
import uz.onveti.smartpill.core.ui.SmartPillTopBar
import uz.onveti.smartpill.screens.pharmacies.state.PharmaciesAction
import uz.onveti.smartpill.screens.pharmacies.state.PharmaciesState

@Composable
internal fun PharmaciesTopBar(
    state: PharmaciesState,
    onAction: (PharmaciesAction) -> Unit,
    modifier: Modifier = Modifier.padding(horizontal = 16.dp),
) {
    SmartPillTopBar(
        title = stringResource(id = R.string.pharmacies_title),
        subtitle = stringResource(id = R.string.topbar_pharmacies_subtitle),
        onNavigateBack = { onAction(PharmaciesAction.NavigateBack) },
        modifier = modifier,
    )
}
