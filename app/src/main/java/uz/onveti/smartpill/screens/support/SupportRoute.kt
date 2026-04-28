package uz.onveti.smartpill.screens.support

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.onveti.smartpill.screens.support.state.SupportSideEffect

@Serializable
data object SupportRoute

fun NavGraphBuilder.supportRoute(
    navController: NavController,
) = composable<SupportRoute> {

        val viewModel: SupportViewModel = koinViewModel()
        val state by viewModel.collectAsState()
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is SupportSideEffect.NavigateBack -> navController.navigateUp()

                is SupportSideEffect.Error -> scope.launch {
                    snackbarHostState.showSnackbar(sideEffect.throwable.message ?: "Unknown error occurred")
                }
            }
        }

        SupportScreen(
            state = state,
            onAction = viewModel::onAction,
            snackbarHostState = snackbarHostState,
        )
    }
