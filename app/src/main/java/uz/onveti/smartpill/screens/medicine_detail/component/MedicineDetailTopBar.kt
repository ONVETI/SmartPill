package uz.onveti.smartpill.screens.medicine_detail.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.onveti.smartpill.R
import uz.onveti.smartpill.core.ui.SmartPillTopBar
import uz.onveti.smartpill.screens.medicine_detail.state.MedicineDetailAction
import uz.onveti.smartpill.screens.medicine_detail.state.MedicineDetailState

@Composable
internal fun MedicineDetailTopBar(
    state: MedicineDetailState,
    onAction: (MedicineDetailAction) -> Unit,
    modifier: Modifier = Modifier.padding(horizontal = 16.dp),
) {
    SmartPillTopBar(
        title = state.medicine?.name ?: stringResource(id = R.string.medicine_detail_title),
        subtitle = stringResource(id = R.string.medicine_detail_subtitle),
        onNavigateBack = { onAction(MedicineDetailAction.NavigateBack) },
        modifier = modifier,
    )
}
