#!/bin/bash

# Script to generate a blank feature with proper naming conventions
# Usage: ./create_feature.sh feature_name
# Example: ./create_feature.sh medicine_schedule

set -e

if [ $# -ne 1 ]; then
    echo "Usage: $0 <feature_name_in_snake_case>"
    echo "Example: $0 medicine_schedule"
    exit 1
fi

FEATURE_NAME_SNAKE=$(echo "$1" | tr '[:upper:]' '[:lower:]')

# Convert snake_case to PascalCase
FEATURE_NAME_PASCAL=$(echo "$FEATURE_NAME_SNAKE" | perl -pe 's/(^|_)([a-z])/uc($2)/ge')

# Convert to camelCase (lowercase first letter of PascalCase)
FEATURE_NAME_CAMEL="$(echo ${FEATURE_NAME_PASCAL:0:1} | tr '[:upper:]' '[:lower:]')${FEATURE_NAME_PASCAL:1}"

echo "Feature Name Snake: $FEATURE_NAME_SNAKE"
echo "Feature Name Pascal: $FEATURE_NAME_PASCAL"
echo "Feature Name Camel: $FEATURE_NAME_CAMEL"

# Define the target directory
# Updated to match project package structure: uz.onveti.smartpill
TARGET_DIR="app/src/main/java/uz/onveti/smartpill/screens/${FEATURE_NAME_SNAKE}"

# Create directory structure
mkdir -p "$TARGET_DIR"
mkdir -p "$TARGET_DIR/component"
mkdir -p "$TARGET_DIR/state"

# Generate Route file
cat > "$TARGET_DIR/${FEATURE_NAME_PASCAL}Route.kt" << EOF
package uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.state.${FEATURE_NAME_PASCAL}SideEffect

@Serializable
data object ${FEATURE_NAME_PASCAL}Route

fun NavGraphBuilder.${FEATURE_NAME_CAMEL}Route(
    navController: NavController,
) = composable<${FEATURE_NAME_PASCAL}Route> {

        val viewModel: ${FEATURE_NAME_PASCAL}ViewModel = koinViewModel()
        val state by viewModel.collectAsState()
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is ${FEATURE_NAME_PASCAL}SideEffect.NavigateBack -> navController.navigateUp()

                is ${FEATURE_NAME_PASCAL}SideEffect.Error -> scope.launch {
                    snackbarHostState.showSnackbar(sideEffect.throwable.message ?: "Unknown error occurred")
                }
            }
        }

        ${FEATURE_NAME_PASCAL}Screen(
            state = state,
            onAction = viewModel::onAction,
            snackbarHostState = snackbarHostState,
        )
    }
EOF

# Generate Screen file
cat > "$TARGET_DIR/${FEATURE_NAME_PASCAL}Screen.kt" << EOF
package uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import uz.onveti.smartpill.ui.theme.AppTheme
import uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.component.${FEATURE_NAME_PASCAL}BottomBar
import uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.component.${FEATURE_NAME_PASCAL}TopBar
import uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.state.${FEATURE_NAME_PASCAL}Action
import uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.state.${FEATURE_NAME_PASCAL}State

@Composable
internal fun ${FEATURE_NAME_PASCAL}Screen(
    state: ${FEATURE_NAME_PASCAL}State,
    onAction: (${FEATURE_NAME_PASCAL}Action) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        topBar = {
            ${FEATURE_NAME_PASCAL}TopBar(
                state = state,
                onAction = onAction,
            )
        },
        bottomBar = {
            ${FEATURE_NAME_PASCAL}BottomBar(
                state = state,
                onAction = onAction,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        ${FEATURE_NAME_PASCAL}Content(
            state = state,
            onAction = onAction,
            paddingValues = paddingValues,
        )
    }
}

@Composable
internal fun ${FEATURE_NAME_PASCAL}Content(
    state: ${FEATURE_NAME_PASCAL}State,
    onAction: (${FEATURE_NAME_PASCAL}Action) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    // Content implementation
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview${FEATURE_NAME_PASCAL}Screen() {
    AppTheme {
        ${FEATURE_NAME_PASCAL}Screen(
            state = ${FEATURE_NAME_PASCAL}State(),
            onAction = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}
EOF

# Generate ViewModel file
cat > "$TARGET_DIR/${FEATURE_NAME_PASCAL}ViewModel.kt" << EOF
package uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.state.${FEATURE_NAME_PASCAL}Action
import uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.state.${FEATURE_NAME_PASCAL}SideEffect
import uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.state.${FEATURE_NAME_PASCAL}State
import kotlinx.coroutines.CoroutineExceptionHandler

internal class ${FEATURE_NAME_PASCAL}ViewModel : ViewModel(), ContainerHost<${FEATURE_NAME_PASCAL}State, ${FEATURE_NAME_PASCAL}SideEffect> {

    override val container: Container<${FEATURE_NAME_PASCAL}State, ${FEATURE_NAME_PASCAL}SideEffect> = container(
        initialState = ${FEATURE_NAME_PASCAL}State(),
        buildSettings = {
            exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(${FEATURE_NAME_PASCAL}SideEffect.Error(throwable))
                }
            }
        },
        onCreate = {

        },
    )

    fun onAction(action: ${FEATURE_NAME_PASCAL}Action) {
        when (action) {
            is ${FEATURE_NAME_PASCAL}Action.NavigateBack -> onNavigateBackClicked()
        }
    }

    private fun onNavigateBackClicked() = intent {
        postSideEffect(${FEATURE_NAME_PASCAL}SideEffect.NavigateBack)
    }
}
EOF

# Generate TopBar component
cat > "$TARGET_DIR/component/${FEATURE_NAME_PASCAL}TopBar.kt" << EOF
package uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.state.${FEATURE_NAME_PASCAL}Action
import uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.state.${FEATURE_NAME_PASCAL}State

@Composable
internal fun ${FEATURE_NAME_PASCAL}TopBar(
    state: ${FEATURE_NAME_PASCAL}State,
    onAction: (${FEATURE_NAME_PASCAL}Action) -> Unit,
    modifier: Modifier = Modifier,
) {

}
EOF

# Generate BottomBar component
cat > "$TARGET_DIR/component/${FEATURE_NAME_PASCAL}BottomBar.kt" << EOF
package uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.state.${FEATURE_NAME_PASCAL}Action
import uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.state.${FEATURE_NAME_PASCAL}State

@Composable
internal fun ${FEATURE_NAME_PASCAL}BottomBar(
    state: ${FEATURE_NAME_PASCAL}State,
    onAction: (${FEATURE_NAME_PASCAL}Action) -> Unit,
    modifier: Modifier = Modifier,
) {

}
EOF

# Generate Action state
cat > "$TARGET_DIR/state/${FEATURE_NAME_PASCAL}Action.kt" << EOF
package uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.state

internal sealed interface ${FEATURE_NAME_PASCAL}Action {

    data object NavigateBack : ${FEATURE_NAME_PASCAL}Action

}
EOF

# Generate SideEffect state
cat > "$TARGET_DIR/state/${FEATURE_NAME_PASCAL}SideEffect.kt" << EOF
package uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.state

import kotlin.jvm.JvmInline

internal sealed interface ${FEATURE_NAME_PASCAL}SideEffect {

    data object NavigateBack : ${FEATURE_NAME_PASCAL}SideEffect

    @JvmInline
    value class Error(val throwable: Throwable) : ${FEATURE_NAME_PASCAL}SideEffect
}
EOF

# Generate State data class
cat > "$TARGET_DIR/state/${FEATURE_NAME_PASCAL}State.kt" << EOF
package uz.onveti.smartpill.screens.${FEATURE_NAME_SNAKE}.state

internal data class ${FEATURE_NAME_PASCAL}State(
    val isLoading: Boolean = false,
)
EOF

echo "Successfully created feature: $FEATURE_NAME_SNAKE"
echo "Directory: $TARGET_DIR"
echo ""
echo "Files created:"
echo "- $TARGET_DIR/${FEATURE_NAME_PASCAL}Route.kt"
echo "- $TARGET_DIR/${FEATURE_NAME_PASCAL}Screen.kt"
echo "- $TARGET_DIR/${FEATURE_NAME_PASCAL}ViewModel.kt"
echo "- $TARGET_DIR/component/${FEATURE_NAME_PASCAL}TopBar.kt"
echo "- $TARGET_DIR/component/${FEATURE_NAME_PASCAL}BottomBar.kt"
echo "- $TARGET_DIR/state/${FEATURE_NAME_PASCAL}Action.kt"
echo "- $TARGET_DIR/state/${FEATURE_NAME_PASCAL}SideEffect.kt"
echo "- $TARGET_DIR/state/${FEATURE_NAME_PASCAL}State.kt"