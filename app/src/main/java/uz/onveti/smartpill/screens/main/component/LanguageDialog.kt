package uz.onveti.smartpill.screens.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.appcompat.app.AppCompatDelegate
import uz.onveti.smartpill.R
import uz.onveti.smartpill.screens.main.state.MainAction

@Composable
internal fun LanguageDialog(
    onAction: (MainAction) -> Unit,
) {
    val currentLocale = AppCompatDelegate.getApplicationLocales().get(0)?.language ?: "uz"

    AlertDialog(
        onDismissRequest = { onAction(MainAction.DismissLanguageDialog) },
        title = {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.select_language),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1E293B)
                )
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 4.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF3B82F6))
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                LanguageItem(
                    flag = "🇺🇿",
                    label = stringResource(id = R.string.uzbek),
                    isSelected = currentLocale == "uz",
                    onClick = { onAction(MainAction.SelectLanguage("uz")) },
                )
                LanguageItem(
                    flag = "🇷🇺",
                    label = stringResource(id = R.string.russian),
                    isSelected = currentLocale == "ru",
                    onClick = { onAction(MainAction.SelectLanguage("ru")) },
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onAction(MainAction.DismissLanguageDialog) },
                modifier = Modifier.padding(bottom = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = Color(0xFF94A3B8),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        shape = RoundedCornerShape(28.dp),
        containerColor = Color.White,
        tonalElevation = 8.dp
    )
}

@Composable
private fun LanguageItem(
    flag: String,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    // Premium Colors
    val selectedGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFDBEAFE), Color(0xFFEFF6FF))
    )
    val unselectedBackground = Color(0xFFF8FAFC)
    val borderColor = if (isSelected) Color(0xFF3B82F6) else Color(0xFFF1F5F9)
    val textColor = if (isSelected) Color(0xFF1E3A8A) else Color(0xFF475569)

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            ),
        color = Color.Transparent, // Managed by Box background
        shadowElevation = if (isSelected) 4.dp else 0.dp
    ) {
        Box(
            modifier = Modifier
                .background(if (isSelected) selectedGradient else Brush.linearGradient(listOf(unselectedBackground, unselectedBackground)))
                .padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Icon / Flag Section
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color.White else Color(0xFFF1F5F9)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = flag,
                        fontSize = 26.sp,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }

                // Text Section
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.SemiBold,
                        color = textColor,
                        letterSpacing = 0.5.sp
                    )
                    if (isSelected) {
                        Text(
                            text = "Active",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF3B82F6).copy(alpha = 0.8f),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Indicator Section
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF3B82F6),
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .border(1.5.dp, Color(0xFFCBD5E1), CircleShape)
                    )
                }
            }
        }
    }
}
