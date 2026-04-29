package uz.onveti.smartpill

import android.Manifest
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import uz.onveti.smartpill.screens.add_medicine.addMedicineRoute
import uz.onveti.smartpill.screens.ai_assistant.aiAssistantRoute
import uz.onveti.smartpill.screens.main.MainRoute
import uz.onveti.smartpill.screens.main.mainRoute
import uz.onveti.smartpill.screens.medicine_detail.medicineDetailRoute
import uz.onveti.smartpill.screens.medicines.medicinesRoute
import uz.onveti.smartpill.screens.pharmacies.pharmaciesRoute
import uz.onveti.smartpill.screens.smart_watch.smartWatchRoute
import uz.onveti.smartpill.screens.sos.sosRoute
import uz.onveti.smartpill.screens.support.supportRoute
import uz.onveti.smartpill.ui.theme.AppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.WHITE,
                Color.WHITE,
            ),
        )
        setContent {
            AppTheme {

                val notificationPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = {},
                )
                val context = LocalContext.current
                LaunchedEffect(Unit) {
                    if (
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS,
                        ) != android.content.pm.PackageManager.PERMISSION_GRANTED
                    ) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = MainRoute,
                    modifier = Modifier,
//                        .background(color = AppTheme.colors.surfaceBackground1)
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(300),
                        ) + fadeIn(animationSpec = tween(300))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it / 10 },
                            animationSpec = tween(300),
                        ) + fadeOut(animationSpec = tween(300))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it / 10 },
                            animationSpec = tween(300),
                        ) + fadeIn(animationSpec = tween(300))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(300),
                        ) + fadeOut(animationSpec = tween(300))
                    },
                ) {

                    mainRoute(navController)
                    medicinesRoute(navController)
                    addMedicineRoute(navController)
                    medicineDetailRoute(navController)
                    aiAssistantRoute(navController)
                    pharmaciesRoute(navController)
                    smartWatchRoute(navController)
                    supportRoute(navController)
                    sosRoute(navController)

                }
            }
        }
    }
}
