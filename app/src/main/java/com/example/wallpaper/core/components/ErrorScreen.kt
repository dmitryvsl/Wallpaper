package com.example.wallpaper.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.wallpaper.R
import com.example.wallpaper.feature.category.presentation.theme.dimens

@Composable
fun ErrorScreen(
    onButtonClick: () -> Unit,
    errorTitle: String = stringResource(R.string.oopsError),
    errorDescription: String = stringResource(R.string.smthWentWrong),
    buttonText: String = stringResource(R.string.reload),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(280.dp, 180.dp),
            painter = painterResource(R.drawable.spaceman),
            contentDescription = null
        )

        Spacer(Modifier.height(MaterialTheme.dimens.padding.padding_1_5))

        Text(
            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.padding.padding_0_25),
            text = errorTitle,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(
                vertical = MaterialTheme.dimens.padding.padding_0_75,
                horizontal = MaterialTheme.dimens.padding.padding_0_25
            ),
            text = errorDescription,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = onButtonClick,
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = buttonText.uppercase(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        }

    }
}