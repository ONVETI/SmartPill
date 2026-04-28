package uz.onveti.smartpill.screens.medicines

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            paddingValues = paddingValues,
        )
    }
}

@Composable
internal fun MedicinesContent(
    state: MedicinesState,
    onAction: (MedicinesAction) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val medicines = listOf(
        MedicineUi("Metformin", "08:00", "Qabul qilindi", 1f),
        MedicineUi("Cardio Aspirin", "14:00", "Kuzatuvda", 0.62f),
        MedicineUi("Omega-3", "20:30", "Kutilmoqda", 0.35f),
    )

    LazyColumn(
        modifier = modifier.padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {

        items(medicines.size) { index ->
            val medicine = medicines[index]

            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Medication,
                                contentDescription = null,
                                tint = Color(0xFF2563EB),
                            )
                            Column {
                                Text(
                                    text = medicine.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(
                                    text = medicine.status,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF64748B),
                                )
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = Color(0xFF64748B),
                            )
                            Text(
                                text = medicine.time,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                    LinearProgressIndicator(
                        progress = { medicine.progress },
                        modifier = Modifier.fillMaxWidth(),
                        color = if (medicine.progress == 1f) Color(0xFF16A34A) else Color(0xFF2563EB),
                        trackColor = Color(0xFFE2E8F0),
                    )
                }
            }
        }
    }
}

private data class MedicineUi(
    val name: String,
    val time: String,
    val status: String,
    val progress: Float,
)

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
