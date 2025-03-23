package org.white.green.designSystem.ui.ui_state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import org.white.green.designSystem.ui.helper.noRippleClickable
import org.white.green.utils.constant.AppConstant
import spacing

sealed interface UIState<out T> {
    data object Loading : UIState<Nothing>
    data class Success<T>(val data: T) : UIState<T>
    data class Error(val error: ErrorType) : UIState<Nothing>
}

sealed interface ErrorType {
    data class NetworkError(val message: String) : ErrorType
    data class NoDataFound(val message: String) : ErrorType
}

fun handleException(exception: Exception): UIState.Error {
    return when {
        AppConstant.NO_DOCUMENT_FOUND == exception.message ->
            UIState.Error(ErrorType.NoDataFound(exception.message.orEmpty()))

        else ->
            UIState.Error(ErrorType.NetworkError(exception.message.orEmpty()))
    }
}

@Composable
fun ErrorUI(
    content: @Composable () -> Unit,
    error: ErrorType,
    onRetry: () -> Unit = {},
    onAction: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {
                    when (error) {
                        is ErrorType.NetworkError -> onRetry()
                        is ErrorType.NoDataFound -> onAction()
                    }
                }
        ) {
            content()  // Now it's wrapped in a clickable Box
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = when (error) {
                    is ErrorType.NoDataFound -> "No data found. Add information."
                    is ErrorType.NetworkError -> "Internet connection error. Try again."
                },
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .weight(1f)
                    .alpha(0.9f)
            )
        }
    }
}


