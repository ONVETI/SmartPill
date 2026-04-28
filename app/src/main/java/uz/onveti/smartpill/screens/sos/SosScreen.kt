package uz.onveti.smartpill.screens.sos

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.onveti.smartpill.R
import uz.onveti.smartpill.ui.theme.AppTheme
import uz.onveti.smartpill.screens.sos.component.SosBottomBar
import uz.onveti.smartpill.screens.sos.component.SosTopBar
import uz.onveti.smartpill.screens.sos.state.SosAction
import uz.onveti.smartpill.screens.sos.state.SosState

@Composable
internal fun SosScreen(
    state: SosState,
    onAction: (SosAction) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        topBar = {
            SosTopBar(
                state = state,
                onAction = onAction,
            )
        },
        bottomBar = {
            SosBottomBar(
                state = state,
                onAction = onAction,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        SosContent(
            state = state,
            onAction = onAction,
            paddingValues = paddingValues,
        )
    }
}

@Composable
internal fun SosContent(
    state: SosState,
    onAction: (SosAction) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier.padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {

        item {
            Text(
                text = stringResource(id = R.string.sos_description),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        item {
            EmergencyButton(
                title = stringResource(id = R.string.sos_ambulance),
                phone = "103",
                icon = Icons.Default.LocalHospital,
                onClick = { context.openDialer("103") },
            )
        }

        item {
            EmergencyButton(
                title = stringResource(id = R.string.sos_emergency),
                phone = "1050",
                icon = Icons.Default.Warning,
                onClick = { context.openDialer("1050") },
            )
        }

        item {
            EmergencyButton(
                title = stringResource(id = R.string.sos_support),
                phone = "1003",
                icon = Icons.Default.Call,
                onClick = { context.openDialer("1003") },
            )
        }
    }
}

@Composable
private fun EmergencyButton(
    title: String,
    phone: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$title • $phone")
    }
}

private fun Context.openDialer(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phone")
    }
    startActivity(intent)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewSosScreen() {
    AppTheme {
        SosScreen(
            state = SosState(),
            onAction = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}
