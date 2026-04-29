package uz.onveti.smartpill.screens.medicine_detail

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.onveti.smartpill.screens.medicine_detail.state.MedicineDetailAction
import uz.onveti.smartpill.screens.medicine_detail.state.MedicineDetailSideEffect

@Serializable
data class MedicineDetailRoute(val medicineId: String)

fun NavGraphBuilder.medicineDetailRoute(
    navController: NavController,
) = composable<MedicineDetailRoute> { backStackEntry ->

        val route = backStackEntry.toRoute<MedicineDetailRoute>()

        val viewModel: MedicineDetailViewModel = koinViewModel()
        val state by viewModel.collectAsState()
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(route.medicineId) {
            viewModel.onAction(MedicineDetailAction.LoadMedicine(route.medicineId))
        }

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is MedicineDetailSideEffect.NavigateBack -> navController.navigateUp()

                is MedicineDetailSideEffect.Error -> scope.launch {
                    snackbarHostState.showSnackbar(sideEffect.throwable.message ?: "Unknown error occurred")
                }
            }
        }

        MedicineDetailScreen(
            state = state,
            onAction = viewModel::onAction,
            snackbarHostState = snackbarHostState,
        )
    }
