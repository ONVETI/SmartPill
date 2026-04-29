package uz.onveti.smartpill.screens.medicine_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.onveti.smartpill.R
import uz.onveti.smartpill.data.medicine.MealTiming
import uz.onveti.smartpill.ui.theme.AppTheme
import uz.onveti.smartpill.screens.medicine_detail.component.MedicineDetailBottomBar
import uz.onveti.smartpill.screens.medicine_detail.component.MedicineDetailTopBar
import uz.onveti.smartpill.screens.medicine_detail.state.MedicineDetailAction
import uz.onveti.smartpill.screens.medicine_detail.state.MedicineDetailState

@Composable
internal fun MedicineDetailScreen(
    state: MedicineDetailState,
    onAction: (MedicineDetailAction) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        topBar = {
            MedicineDetailTopBar(
                state = state,
                onAction = onAction,
            )
        },
        bottomBar = {
            MedicineDetailBottomBar(
                state = state,
                onAction = onAction,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        MedicineDetailContent(
            state = state,
            onAction = onAction,
            paddingValues = paddingValues,
        )
    }
}

@Composable
internal fun MedicineDetailContent(
    state: MedicineDetailState,
    onAction: (MedicineDetailAction) -> Unit,
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

    val medicine = state.medicine
    if (medicine == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(id = R.string.medicine_not_found),
                color = Color(0xFF64748B),
            )
        }
        return
    }

    Column(
        modifier = modifier
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Text(
                    text = medicine.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                DetailRow(
                    title = stringResource(id = R.string.medicine_morning_time),
                    value = medicine.morningTime,
                )
                DetailRow(
                    title = stringResource(id = R.string.medicine_afternoon_time),
                    value = medicine.afternoonTime,
                )
                DetailRow(
                    title = stringResource(id = R.string.medicine_evening_time),
                    value = medicine.eveningTime,
                )
            }
        }

        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Restaurant,
                    contentDescription = null,
                    tint = Color(0xFF2563EB),
                )
                Column {
                    Text(
                        text = stringResource(id = R.string.meal_timing_title),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF64748B),
                    )
                    Text(
                        text = when (medicine.mealTiming) {
                            MealTiming.BEFORE_MEAL -> stringResource(id = R.string.before_meal)
                            MealTiming.AFTER_MEAL -> stringResource(id = R.string.after_meal)
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    title: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = null,
                tint = Color(0xFF64748B),
            )
            Text(
                text = title,
                color = Color(0xFF64748B),
            )
        }
        Text(
            text = value.ifBlank { stringResource(id = R.string.time_not_selected) },
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewMedicineDetailScreen() {
    AppTheme {
        MedicineDetailScreen(
            state = MedicineDetailState(),
            onAction = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}
