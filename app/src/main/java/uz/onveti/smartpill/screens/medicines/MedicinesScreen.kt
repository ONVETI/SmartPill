package uz.onveti.smartpill.screens.medicines

import androidx.compose.foundation.clickable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.onveti.smartpill.R
import uz.onveti.smartpill.data.medicine.MealTiming
import uz.onveti.smartpill.data.medicine.Medicine
import uz.onveti.smartpill.ui.theme.AppTheme
import uz.onveti.smartpill.screens.medicines.component.MedicinesBottomBar
import uz.onveti.smartpill.screens.medicines.component.MedicinesTopBar
import uz.onveti.smartpill.screens.medicines.state.MedicinesAction
import uz.onveti.smartpill.screens.medicines.state.MedicinesState

@Composable
internal fun MedicinesScreen(
    state: MedicinesState,
    onAction: (MedicinesAction) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    var medicineToDelete by remember { mutableStateOf<Medicine?>(null) }

    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        topBar = {
            MedicinesTopBar(
                state = state,
                onAction = onAction,
            )
        },
        bottomBar = {
            MedicinesBottomBar(
                state = state,
                onAction = onAction,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        MedicinesContent(
            state = state,
            onAction = onAction,
            onDeleteClick = { medicineToDelete = it },
            paddingValues = paddingValues,
        )
    }

    medicineToDelete?.let { medicine ->
        AlertDialog(
            onDismissRequest = { medicineToDelete = null },
            title = {
                Text(text = stringResource(id = R.string.delete_medicine_title))
            },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.delete_medicine_message,
                        medicine.name,
                    ),
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onAction(MedicinesAction.DeleteMedicineClicked(medicine.id))
                        medicineToDelete = null
                    },
                ) {
                    Text(text = stringResource(id = R.string.delete_confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { medicineToDelete = null },
                ) {
                    Text(text = stringResource(id = R.string.delete_cancel))
                }
            },
        )
    }
}

@Composable
internal fun MedicinesContent(
    state: MedicinesState,
    onAction: (MedicinesAction) -> Unit,
    onDeleteClick: (Medicine) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
        return
    }

    if (state.medicines.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(id = R.string.medicines_empty_state),
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF64748B),
            )
        }
        return
    }

    LazyColumn(
        modifier = modifier.padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {

        items(
            items = state.medicines,
            key = { medicine -> medicine.id },
        ) { medicine ->
            MedicineListItem(
                medicine = medicine,
                pillStatus = state.lastPillStatus,
                onClick = { onAction(MedicinesAction.MedicineClicked(medicine.id)) },
                onDeleteClick = { onDeleteClick(medicine) },
            )
        }
    }
}

@Composable
private fun MedicineListItem(
    medicine: Medicine,
    pillStatus: String?,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            color = Color(0xFFDBEAFE),
                            shape = CircleShape,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Medication,
                        contentDescription = null,
                        tint = Color(0xFF2563EB),
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = medicine.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )

                        if (pillStatus != null) {
                            val isTaken = pillStatus == "1"
                            Surface(
                                color = if (isTaken) Color(0xFFDCFCE7) else Color(0xFFFEE2E2),
                                shape = RoundedCornerShape(12.dp),
                            ) {
                                Text(
                                    text = if (isTaken) stringResource(id = R.string.pill_taken) else stringResource(id = R.string.pill_missed),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isTaken) Color(0xFF166534) else Color(0xFF991B1B),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    Text(
                        text = when (medicine.mealTiming) {
                            MealTiming.BEFORE_MEAL -> stringResource(id = R.string.before_meal)
                            MealTiming.AFTER_MEAL -> stringResource(id = R.string.after_meal)
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64748B),
                    )
                }

                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete_medicine),
                        tint = Color(0xFFDC2626),
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                MedicineTimeBadge(
                    modifier = Modifier.weight(1f),
                    label = stringResource(id = R.string.medicine_morning_time),
                    time = medicine.morningTime,
                )
                MedicineTimeBadge(
                    modifier = Modifier.weight(1f),
                    label = stringResource(id = R.string.medicine_afternoon_time),
                    time = medicine.afternoonTime,
                )
                MedicineTimeBadge(
                    modifier = Modifier.weight(1f),
                    label = stringResource(id = R.string.medicine_evening_time),
                    time = medicine.eveningTime,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = Color(0xFF16A34A),
                    )
                    Text(
                        text = medicine.nextTimeLabel(),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF166534),
                    )
                }
                LinearProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF16A34A),
                    trackColor = Color(0xFFE2E8F0),
                )
            }
        }
    }
}

@Composable
private fun MedicineTimeBadge(
    label: String,
    time: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 8.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(3.dp),
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF64748B),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = time.ifBlank { stringResource(id = R.string.time_not_selected) },
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0F172A),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

private fun Medicine.nextTimeLabel(): String = listOf(
    morningTime,
    afternoonTime,
    eveningTime,
).firstOrNull { time -> time.isNotBlank() } ?: "--:--"

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewMedicinesScreen() {
    AppTheme {
        MedicinesScreen(
            state = MedicinesState(),
            onAction = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}
