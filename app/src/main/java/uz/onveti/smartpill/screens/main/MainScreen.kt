package uz.onveti.smartpill.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import uz.onveti.smartpill.R
import uz.onveti.smartpill.screens.ai_assistant.AiAssistantRoute
import uz.onveti.smartpill.screens.main.state.MainAction
import uz.onveti.smartpill.screens.main.state.MainModule
import uz.onveti.smartpill.screens.main.state.MainState
import uz.onveti.smartpill.screens.medicines.MedicinesRoute
import uz.onveti.smartpill.screens.pharmacies.PharmaciesRoute
import uz.onveti.smartpill.screens.smart_watch.SmartWatchRoute
import uz.onveti.smartpill.screens.sos.SosRoute
import uz.onveti.smartpill.screens.support.SupportRoute
import uz.onveti.smartpill.ui.theme.AppTheme

@Composable
internal fun MainScreen(
    rootNavController: NavController,
    state: MainState,
    onAction: (MainAction) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        topBar = {
            MainTopBar()
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        containerColor = Color(0xFFF7FAFC),
    ) { paddingValues ->
        MainContent(
            rootNavController = rootNavController,
            paddingValues = paddingValues,
        )
    }
}

@Composable
internal fun MainContent(
    rootNavController: NavController,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            MissionCard()
        }

        item {
            ModuleGrid(rootNavController = rootNavController)
        }

        item {
            IbnSinoQuoteCard()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainTopBar() {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = stringResource(id = R.string.main_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF7FAFC),
        ),
    )
}

@Composable
private fun MissionCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0F766E),
        ),
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.MonitorHeart,
                    contentDescription = null,
                    tint = Color.White,
                )
                Text(
                    text = stringResource(id = R.string.mission_title),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
            Text(
                text = stringResource(id = R.string.mission_description),
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFE0F2F1),
            )
        }
    }
}

@Composable
private fun IbnSinoQuoteCard() {
    val quotes = listOf(
        QuoteUi(
            text = stringResource(id = R.string.quote_ibn_sino_1),
            author = stringResource(id = R.string.quote_ibn_sino_author),
        ),
        QuoteUi(
            text = stringResource(id = R.string.quote_al_razi),
            author = stringResource(id = R.string.quote_al_razi_author),
        ),
        QuoteUi(
            text = stringResource(id = R.string.quote_hippocrates),
            author = stringResource(id = R.string.quote_hippocrates_author),
        ),
        QuoteUi(
            text = stringResource(id = R.string.quote_paracelsus),
            author = stringResource(id = R.string.quote_paracelsus_author),
        ),
        QuoteUi(
            text = stringResource(id = R.string.quote_florence),
            author = stringResource(id = R.string.quote_florence_author),
        ),
    )
    var quoteIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(quotes.size) {
        while (true) {
            delay(5000)
            quoteIndex = (quoteIndex + 1) % quotes.size
        }
    }

    val quote = quotes[quoteIndex]

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFBEB),
        ),
        border = BorderStroke(1.dp, Color(0xFFFDE68A)),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = quote.text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF713F12),
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = quote.author,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFFA16207),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun ModuleGrid(
    rootNavController: NavController,
) {
    val modules = listOf(
        DashboardModule(
            module = MainModule.Medicines,
            title = stringResource(id = R.string.module_medicines),
            description = stringResource(id = R.string.module_medicines_desc),
            icon = Icons.Default.Medication,
            accent = Color(0xFF2563EB),
            onClick = { rootNavController.navigate(MedicinesRoute) },
        ),
        DashboardModule(
            module = MainModule.AiAssistant,
            title = stringResource(id = R.string.module_ai),
            description = stringResource(id = R.string.module_ai_desc),
            icon = Icons.Default.Biotech,
            accent = Color(0xFF7C3AED),
            onClick = { rootNavController.navigate(AiAssistantRoute) },
        ),
        DashboardModule(
            module = MainModule.Pharmacies,
            title = stringResource(id = R.string.module_pharmacies),
            description = stringResource(id = R.string.module_pharmacies_desc),
            icon = Icons.Default.LocalPharmacy,
            accent = Color(0xFF0891B2),
            onClick = { rootNavController.navigate(PharmaciesRoute) },
        ),
        DashboardModule(
            module = MainModule.SmartWatch,
            title = stringResource(id = R.string.module_watch),
            description = stringResource(id = R.string.module_watch_desc),
            icon = Icons.Default.MonitorHeart,
            accent = Color(0xFF16A34A),
            onClick = { rootNavController.navigate(SmartWatchRoute) },
        ),
        DashboardModule(
            module = MainModule.Support,
            title = stringResource(id = R.string.module_support),
            description = stringResource(id = R.string.module_support_desc),
            icon = Icons.Default.SupportAgent,
            accent = Color(0xFFEA580C),
            onClick = { rootNavController.navigate(SupportRoute) },
        ),
        DashboardModule(
            module = MainModule.Sos,
            title = stringResource(id = R.string.module_sos),
            description = stringResource(id = R.string.module_sos_desc),
            icon = Icons.Default.Warning,
            accent = Color(0xFFDC2626),
            onClick = { rootNavController.navigate(SosRoute) },
        ),
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        modules.chunked(2).forEach { rowModules ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                rowModules.forEach { item ->
                    ModuleCard(
                        item = item,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun ModuleCard(
    item: DashboardModule,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier
            .height(144.dp)
            .clickable(onClick = item.onClick),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, item.accent),
        colors = CardDefaults.outlinedCardColors(
            containerColor = item.accent.copy(alpha = 0.08f),
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            IconBubble(
                icon = item.icon,
                tint = item.accent,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
private fun IconBubble(
    icon: ImageVector,
    tint: Color,
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(tint.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(22.dp),
        )
    }
}

private data class DashboardModule(
    val module: MainModule,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val accent: Color,
    val onClick: () -> Unit,
)

private data class QuoteUi(
    val text: String,
    val author: String,
)

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewMainScreen() {
    AppTheme {
        MainScreen(
            state = MainState(),
            onAction = {},
            snackbarHostState = SnackbarHostState(),
            rootNavController = rememberNavController(),
        )
    }
}
