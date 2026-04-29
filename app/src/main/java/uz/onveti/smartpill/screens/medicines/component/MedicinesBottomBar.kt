package uz.onveti.smartpill.screens.medicines.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.onveti.smartpill.R
import uz.onveti.smartpill.screens.medicines.state.MedicinesAction
import uz.onveti.smartpill.screens.medicines.state.MedicinesState

@Composable
internal fun MedicinesBottomBar(
    state: MedicinesState,
    onAction: (MedicinesAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = { onAction(MedicinesAction.AddMedicineClicked) },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentPadding = PaddingValues(vertical = 14.dp),
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
        )
        Text(
            text = stringResource(id = R.string.add_medicine_button),
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}
