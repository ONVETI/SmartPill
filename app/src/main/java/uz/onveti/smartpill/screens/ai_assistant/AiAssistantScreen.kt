package uz.onveti.smartpill.screens.ai_assistant

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.onveti.smartpill.R
import uz.onveti.smartpill.ui.theme.AppTheme
import uz.onveti.smartpill.screens.ai_assistant.component.AiAssistantBottomBar
import uz.onveti.smartpill.screens.ai_assistant.component.AiAssistantTopBar
import uz.onveti.smartpill.screens.ai_assistant.state.AiAssistantAction
import uz.onveti.smartpill.screens.ai_assistant.state.AiAssistantState

@Composable
internal fun AiAssistantScreen(
    state: AiAssistantState,
    onAction: (AiAssistantAction) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        topBar = {
            AiAssistantTopBar(
                state = state,
                onAction = onAction,
            )
        },
        bottomBar = {
            AiAssistantBottomBar(
                state = state,
                onAction = onAction,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        AiAssistantContent(
            state = state,
            onAction = onAction,
            paddingValues = paddingValues,
        )
    }
}

@Composable
internal fun AiAssistantContent(
    state: AiAssistantState,
    onAction: (AiAssistantAction) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (state.messages.isEmpty()) {
            item {
                Text(
                    text = stringResource(id = R.string.ai_empty_state),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF64748B),
                )
            }
        }

        items(state.messages.size) { index ->
            val message = state.messages[index]
            ChatBubble(
                label = stringResource(id = R.string.ai_question_label),
                text = message.question,
                containerColor = Color(0xFFEFF6FF),
            )
            ChatBubble(
                label = stringResource(id = R.string.ai_answer_label),
                text = message.answer,
                containerColor = Color(0xFFF3E8FF),
            )
        }

        if (state.isLoading) {
            item {
                ChatBubble(
                    label = stringResource(id = R.string.ai_answer_label),
                    text = stringResource(id = R.string.ai_loading),
                    containerColor = Color(0xFFF3E8FF),
                )
            }
        }

        item {
            QuestionInputBlock(
                state = state,
                onAction = onAction,
            )
        }
    }
}

@Composable
private fun QuestionInputBlock(
    state: AiAssistantState,
    onAction: (AiAssistantAction) -> Unit,
) {
    val suggestedQuestions = listOf(
        stringResource(id = R.string.ai_suggestion_missed_dose),
        stringResource(id = R.string.ai_suggestion_side_effect),
        stringResource(id = R.string.ai_suggestion_best_time),
        stringResource(id = R.string.ai_suggestion_double_dose),
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            suggestedQuestions.forEach { question ->
                Surface(
                    onClick = { onAction(AiAssistantAction.QuestionChanged(question)) },
                    enabled = !state.isLoading,
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFEFF6FF),
                    contentColor = Color(0xFF1D4ED8),
                ) {
                    Text(
                        text = question,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 12.dp,
                            ),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }

        OutlinedTextField(
            value = state.question,
            onValueChange = { onAction(AiAssistantAction.QuestionChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading,
            label = {
                Text(text = stringResource(id = R.string.ai_question_hint))
            },
            trailingIcon = {
                IconButton(
                    onClick = { onAction(AiAssistantAction.QuestionSubmitted) },
                    enabled = !state.isLoading,
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                        )
                    } else {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = stringResource(id = R.string.ai_send),
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onSend = { onAction(AiAssistantAction.QuestionSubmitted) },
            ),
            minLines = 2,
        )
    }
}

@Composable
private fun ChatBubble(
    label: String,
    text: String,
    containerColor: Color,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = containerColor,
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF475569),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewAiAssistantScreen() {
    AppTheme {
        AiAssistantScreen(
            state = AiAssistantState(),
            onAction = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}
