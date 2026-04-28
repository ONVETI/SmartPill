package uz.onveti.smartpill.screens.main.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import uz.onveti.smartpill.R
import uz.onveti.smartpill.screens.main.state.MainAction
import uz.onveti.smartpill.screens.main.state.MainModule
import uz.onveti.smartpill.screens.main.state.MainState
import uz.onveti.smartpill.ui.theme.AppTheme

@Composable
internal fun MainBottomBar(
    state: MainState,
    onAction: (MainAction) -> Unit,
) {
    val items = listOf(
        BottomNavItem(
            module = MainModule.Medicines,
            label = stringResource(id = R.string.nav_medicines),
            icon = Icons.Default.Medication,
        ),
        BottomNavItem(
            module = MainModule.AiAssistant,
            label = stringResource(id = R.string.nav_ai),
            icon = Icons.Default.Biotech,
        ),
        BottomNavItem(
            module = MainModule.Pharmacies,
            label = stringResource(id = R.string.nav_pharmacies),
            icon = Icons.Default.LocalPharmacy,
        ),
        BottomNavItem(
            module = MainModule.SmartWatch,
            label = stringResource(id = R.string.nav_watch),
            icon = Icons.Default.MonitorHeart,
        ),
        BottomNavItem(
            module = MainModule.Support,
            label = stringResource(id = R.string.nav_support),
            icon = Icons.Default.SupportAgent,
        ),
        BottomNavItem(
            module = MainModule.Sos,
            label = stringResource(id = R.string.nav_sos),
            icon = Icons.Default.Warning,
        ),
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = state.selectedModule == item.module,
                onClick = { onAction(MainAction.ModuleSelected(item.module)) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
            )
        }
    }
}

private data class BottomNavItem(
    val module: MainModule,
    val label: String,
    val icon: ImageVector,
)

@Preview
@Composable
private fun PreviewMainBottomBar() {
    AppTheme {
        MainBottomBar(
            state = MainState(),
            onAction = {},
        )
    }
}
