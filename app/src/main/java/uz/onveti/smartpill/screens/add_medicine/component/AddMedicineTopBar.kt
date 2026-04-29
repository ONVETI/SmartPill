package uz.onveti.smartpill.screens.add_medicine.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import uz.onveti.smartpill.R
import uz.onveti.smartpill.core.ui.SmartPillTopBar
import uz.onveti.smartpill.screens.add_medicine.state.AddMedicineAction
import uz.onveti.smartpill.screens.add_medicine.state.AddMedicineState

@Composable
internal fun AddMedicineTopBar(
    state: AddMedicineState,
    onAction: (AddMedicineAction) -> Unit,
    modifier: Modifier = Modifier.padding(horizontal = 16.dp),
) {
    SmartPillTopBar(
        title = stringResource(id = R.string.add_medicine_title),
        subtitle = stringResource(id = R.string.add_medicine_subtitle),
        onNavigateBack = { onAction(AddMedicineAction.NavigateBack) },
        modifier = modifier,
    )
}
