package com.example.waterme.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.waterme.FIVE_SECONDS
import com.example.waterme.ONE_DAY
import com.example.waterme.R
import com.example.waterme.SEVEN_DAYS
import com.example.waterme.THIRTY_DAYS
import com.example.waterme.data.Reminder
import com.example.waterme.model.Plant
import com.example.waterme.ui.theme.MyApplicationTheme
import java.util.concurrent.TimeUnit

@Composable
fun WaterMeApp(
    waterViewModel: WaterViewModel = viewModel(factory = WaterViewModel.Factory)
) {
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            PlantListContent(plants = waterViewModel.plants, onScheduleReminder = {
                waterViewModel.scheduleReminder(it)
            })
        }
    }
}

@Composable
fun PlantListContent(
    plants: List<Plant>, onScheduleReminder: (Reminder) -> Unit, modifier: Modifier = Modifier
) {
    var selectedPlant by rememberSaveable {
        mutableStateOf(plants[0])
    }
    var showReminderDialog by rememberSaveable {
        mutableStateOf(false)
    }

    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        modifier = modifier
    ) {
        items(plants) { plant ->
            PlantListItem(plant = plant, onItemSelect = {
                selectedPlant = plant
                showReminderDialog = true
            }, modifier = modifier.fillMaxWidth())
        }
    }

    if (showReminderDialog) {
        ReminderDialogContent(
            onDialogDismiss = { showReminderDialog = false },
            plantName = stringResource(id = selectedPlant.name),
            onScheduleReminder = onScheduleReminder
        )
    }
}

@Composable
fun PlantListItem(
    plant: Plant, onItemSelect: (Plant) -> Unit, modifier: Modifier = Modifier
) {
    Card(modifier = modifier.clickable { onItemSelect(plant) }) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            Text(
                text = stringResource(id = plant.name),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = stringResource(id = plant.type), style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(id = plant.description),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${stringResource(id = R.string.water)} ${stringResource(id = plant.schedule)}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun ReminderDialogContent(
    onDialogDismiss: () -> Unit,
    plantName: String,
    onScheduleReminder: (Reminder) -> Unit,
    modifier: Modifier = Modifier
) {
    val reminders = listOf(
        Reminder(R.string.five_seconds, FIVE_SECONDS, TimeUnit.SECONDS, plantName),
        Reminder(R.string.one_day, ONE_DAY, TimeUnit.MINUTES, plantName),
        Reminder(R.string.one_week, SEVEN_DAYS, TimeUnit.DAYS, plantName),
        Reminder(R.string.one_month, THIRTY_DAYS, TimeUnit.DAYS, plantName)
    )

    AlertDialog(onDismissRequest = { onDialogDismiss() },
        confirmButton = { },
        title = { Text(text = stringResource(id = R.string.remind_me, plantName)) },
        text = {
            Column {
                reminders.forEach { reminder ->
                    Text(text = stringResource(id = reminder.durationRes),
                        modifier = Modifier
                            .clickable {
                                onScheduleReminder(reminder)
                                onDialogDismiss()
                            }
                            .fillMaxWidth()
                            .padding(vertical = 8.dp))
                }
            }
        })
}