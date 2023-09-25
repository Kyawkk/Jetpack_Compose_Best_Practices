package com.example.bluromatic.ui.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bluromatic.R
import com.example.bluromatic.data.BlurAmount
import com.example.bluromatic.ui.theme.MyApplicationTheme

@Composable
fun BluromaticScreen(
    blurViewModel: BlurViewModel = viewModel(factory = BlurViewModel.Factory)
) {
    val uiState by blurViewModel.blurUiState.collectAsStateWithLifecycle()

    MyApplicationTheme {
        Surface (
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BluromaticScreenContent(
                blurUiState = uiState,
                blurAmountOptions = blurViewModel.blurAmount,
                applyBlur = blurViewModel::applyBlur,
                cancelWork = blurViewModel::cancelBlur
            )
        }
    }
}

@Composable
fun BluromaticScreenContent(
    blurUiState: BlurUiState,
    blurAmountOptions : List<BlurAmount>,
    applyBlur : (Int) -> Unit,
    cancelWork: () -> Unit
) {
    var selectedValue by rememberSaveable {
        mutableStateOf(1)
    }
    val context = LocalContext.current

    Column (
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
    ){
        Image(painter = painterResource(id = R.drawable.wallpaper), contentDescription = null, contentScale = ContentScale.Fit)
        BlurAmountContent(
            selectedValue = selectedValue,
            blurAmounts = blurAmountOptions,
            modifier = Modifier.fillMaxWidth(),
            onSelectedValueChange = { selectedValue = it }
        )
        BlurActions(
            blurUiState = blurUiState,
            onStartClick = { applyBlur(selectedValue) },
            onSeeFileClick = {imgUri ->
                             showBlurredImage(context,imgUri)
            },
            onCancelClick = { cancelWork() },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun BlurActions(
    blurUiState: BlurUiState,
    onStartClick: () -> Unit,
    onSeeFileClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        when(blurUiState){
            is BlurUiState.Default -> {
                Button(onClick = onStartClick) {
                    Text(text = stringResource(id = R.string.start))
                }
            }

            is BlurUiState.Loading -> {
                FilledTonalButton(onClick = onCancelClick) {
                    Text(text = stringResource(id = R.string.cancel_work))
                }
                CircularProgressIndicator(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
            }

            is BlurUiState.Complete -> {
                Button(onClick = onStartClick) {
                    Text(text = stringResource(id = R.string.start))
                }
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                FilledTonalButton(onClick = { onSeeFileClick(blurUiState.outputUri) }) {
                    Text(text = stringResource(id = R.string.see_file))
                }
            }
        }
    }
}

@Composable
private fun BlurAmountContent(
    selectedValue: Int,
    blurAmounts: List<BlurAmount>,
    modifier: Modifier = Modifier,
    onSelectedValueChange: (Int) -> Unit
) {
    Column(
        modifier = modifier.selectableGroup()
    ) {
        Text(
            text = stringResource(R.string.blur_title),
            style = MaterialTheme.typography.headlineSmall
        )
        blurAmounts.forEach { amount ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        role = Role.RadioButton,
                        selected = (selectedValue == amount.blurAmount),
                        onClick = { onSelectedValueChange(amount.blurAmount) }
                    )
                    .size(48.dp)
            ) {
                RadioButton(
                    selected = (selectedValue == amount.blurAmount),
                    onClick = null,
                    modifier = Modifier.size(48.dp),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(stringResource(amount.blurAmountRes))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BluromaticScreenContentPreview() {
    MyApplicationTheme   {
        BluromaticScreenContent(
            blurUiState = BlurUiState.Default,
            blurAmountOptions = listOf(BlurAmount(R.string.blur_lv_1, 1)),
            {},
            {}
        )
    }
}

private fun showBlurredImage(context: Context, currentUri: String) {
    val uri = if (currentUri.isNotEmpty()) {
        Uri.parse(currentUri)
    } else {
        null
    }
    val actionView = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(actionView)
}