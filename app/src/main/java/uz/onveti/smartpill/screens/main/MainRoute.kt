package uz.onveti.smartpill.screens.main

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinActivityViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.onveti.smartpill.screens.main.state.MainSideEffect

@Serializable
data object MainRoute

fun NavGraphBuilder.mainRoute(
    navController: NavController,
) = composable<MainRoute> {

    val viewModel: MainViewModel = koinActivityViewModel()
    val state by viewModel.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is MainSideEffect.NavigateBack -> navController.navigateUp()

            is MainSideEffect.Error -> scope.launch {
                snackbarHostState.showSnackbar(
                    sideEffect.throwable.message ?: "Unknown error occurred"
                )
            }
        }
    }

    MainScreen(
        rootNavController = navController,
        state = state,
        onAction = viewModel::onAction,
        snackbarHostState = snackbarHostState,
    )
}
