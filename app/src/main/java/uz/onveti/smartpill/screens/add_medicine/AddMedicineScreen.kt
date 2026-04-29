package uz.onveti.smartpill.screens.add_medicine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.onveti.smartpill.R
import uz.onveti.smartpill.data.medicine.MealTiming
import uz.onveti.smartpill.ui.theme.AppTheme
import uz.onveti.smartpill.screens.add_medicine.component.AddMedicineBottomBar
import uz.onveti.smartpill.screens.add_medicine.component.AddMedicineTopBar
import uz.onveti.smartpill.screens.add_medicine.state.AddMedicineAction
import uz.onveti.smartpill.screens.add_medicine.state.AddMedicineState

@Composable
internal fun AddMedicineScreen(
    state: AddMedicineState,
    onAction: (AddMedicineAction) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        topBar = {
            AddMedicineTopBar(
                state = state,
                onAction = onAction,
            )
        },
        bottomBar = {
            AddMedicineBottomBar(
                state = state,
                onAction = onAction,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        AddMedicineContent(
            state = state,
            onAction = onAction,
            paddingValues = paddingValues,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddMedicineContent(
    state: AddMedicineState,
    onAction: (AddMedicineAction) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    var activePicker by remember { mutableStateOf<MedicineTimeSlot?>(null) }

    Column(
        modifier = modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        OutlinedTextField(
            value = state.name,
            onValueChange = { onAction(AddMedicineAction.NameChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.medicine_name_label)) },
            singleLine = true,
        )

        TimePickerField(
            value = state.morningTime,
            label = stringResource(id = R.string.medicine_morning_time),
            onClick = { activePicker = MedicineTimeSlot.Morning },
        )

        TimePickerField(
            value = state.afternoonTime,
            label = stringResource(id = R.string.medicine_afternoon_time),
            onClick = { activePicker = MedicineTimeSlot.Afternoon },
        )

        TimePickerField(
            value = state.eveningTime,
            label = stringResource(id = R.string.medicine_evening_time),
            onClick = { activePicker = MedicineTimeSlot.Evening },
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = stringResource(id = R.string.meal_timing_title),
                style = MaterialTheme.typography.titleSmall,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                FilterChip(
                    selected = state.mealTiming == MealTiming.BEFORE_MEAL,
                    onClick = {
                        onAction(AddMedicineAction.MealTimingChanged(MealTiming.BEFORE_MEAL))
                    },
                    label = { Text(text = stringResource(id = R.string.before_meal)) },
                )
                FilterChip(
                    selected = state.mealTiming == MealTiming.AFTER_MEAL,
                    onClick = {
                        onAction(AddMedicineAction.MealTimingChanged(MealTiming.AFTER_MEAL))
                    },
                    label = { Text(text = stringResource(id = R.string.after_meal)) },
                )
            }
        }

        state.validationMessage?.let { message ->
            Text(
                text = message,
                color = Color(0xFFDC2626),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Button(
            onClick = { onAction(AddMedicineAction.SaveClicked) },
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 14.dp),
        ) {
            Text(
                text = if (state.isLoading) {
                    stringResource(id = R.string.saving)
                } else {
                    stringResource(id = R.string.save)
                },
            )
        }
    }

    activePicker?.let { slot ->
        val pickerState = rememberTimePickerState(is24Hour = true)
        AlertDialog(
            onDismissRequest = { activePicker = null },
            confirmButton = {
                TextButton(
                    onClick = {
                        val time = "%02d:%02d".format(pickerState.hour, pickerState.minute)
                        when (slot) {
                            MedicineTimeSlot.Morning -> onAction(AddMedicineAction.MorningTimeChanged(time))
                            MedicineTimeSlot.Afternoon -> onAction(AddMedicineAction.AfternoonTimeChanged(time))
                            MedicineTimeSlot.Evening -> onAction(AddMedicineAction.EveningTimeChanged(time))
                        }
                        activePicker = null
                    },
                ) {
                    Text(text = stringResource(id = R.string.select_time))
                }
            },
            dismissButton = {
                TextButton(onClick = { activePicker = null }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
            title = {
                Text(text = slot.title())
            },
            text = {
                TimePicker(state = pickerState)
            },
        )
    }
}

@Composable
private fun TimePickerField(
    value: String,
    label: String,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = "$label: ${value.ifBlank { "--:--" }}")
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = null,
            )
        }
    }
}

private enum class MedicineTimeSlot {
    Morning,
    Afternoon,
    Evening,
}

private fun MedicineTimeSlot.title(): String = when (this) {
    MedicineTimeSlot.Morning -> "1-mahal soati"
    MedicineTimeSlot.Afternoon -> "2-mahal soati"
    MedicineTimeSlot.Evening -> "3-mahal soati"
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewAddMedicineScreen() {
    AppTheme {
        AddMedicineScreen(
            state = AddMedicineState(),
            onAction = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}
