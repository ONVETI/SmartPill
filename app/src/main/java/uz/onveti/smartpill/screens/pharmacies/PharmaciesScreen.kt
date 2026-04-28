package uz.onveti.smartpill.screens.pharmacies

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
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.onveti.smartpill.ui.theme.AppTheme
import uz.onveti.smartpill.screens.pharmacies.component.PharmaciesBottomBar
import uz.onveti.smartpill.screens.pharmacies.component.PharmaciesTopBar
import uz.onveti.smartpill.screens.pharmacies.state.PharmaciesAction
import uz.onveti.smartpill.screens.pharmacies.state.PharmaciesState

@Composable
internal fun PharmaciesScreen(
    state: PharmaciesState,
    onAction: (PharmaciesAction) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        topBar = {
            PharmaciesTopBar(
                state = state,
                onAction = onAction,
            )
        },
        bottomBar = {
            PharmaciesBottomBar(
                state = state,
                onAction = onAction,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        PharmaciesContent(
            state = state,
            onAction = onAction,
            paddingValues = paddingValues,
        )
    }
}

@Composable
internal fun PharmaciesContent(
    state: PharmaciesState,
    onAction: (PharmaciesAction) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val pharmacies = listOf(
        PharmacyUi("Med24 Apteka", "0.8 km", "24/7", "Metformin mavjud"),
        PharmacyUi("Shifo Pharm", "1.4 km", "08:00-23:00", "Cardio Aspirin mavjud"),
        PharmacyUi("Grand Pharmacy", "2.1 km", "09:00-22:00", "Retsept bo‘yicha"),
    )

    LazyColumn(
        modifier = modifier.padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {

        items(pharmacies.size) { index ->
            val pharmacy = pharmacies[index]

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
                        imageVector = Icons.Default.LocalPharmacy,
                        contentDescription = null,
                        tint = Color(0xFF0891B2),
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = pharmacy.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "${pharmacy.distance} • ${pharmacy.schedule} • ${pharmacy.note}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF64748B),
                        )
                    }
                }
            }
        }
    }
}

private data class PharmacyUi(
    val name: String,
    val distance: String,
    val schedule: String,
    val note: String,
)

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewPharmaciesScreen() {
    AppTheme {
        PharmaciesScreen(
            state = PharmaciesState(),
            onAction = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}
