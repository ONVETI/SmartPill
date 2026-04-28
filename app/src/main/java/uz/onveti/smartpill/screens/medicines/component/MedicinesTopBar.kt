package uz.onveti.smartpill.screens.medicines.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.onveti.smartpill.R
import uz.onveti.smartpill.core.ui.SmartPillTopBar
import uz.onveti.smartpill.screens.medicines.state.MedicinesAction
import uz.onveti.smartpill.screens.medicines.state.MedicinesState

@Composable
internal fun MedicinesTopBar(
    state: MedicinesState,
    onAction: (MedicinesAction) -> Unit,
    modifier: Modifier = Modifier.padding(horizontal = 16.dp),
) {
    SmartPillTopBar(
        title = stringResource(id = R.string.medicines_title),
        subtitle = stringResource(id = R.string.topbar_control_subtitle),
        onNavigateBack = { onAction(MedicinesAction.NavigateBack) },
        modifier = modifier,
    )
}
