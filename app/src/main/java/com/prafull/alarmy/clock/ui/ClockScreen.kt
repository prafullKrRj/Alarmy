package com.prafull.alarmy.clock.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafull.alarmy.ui.Routes
import com.prafull.alarmy.clock.di.CityModel
import com.prafull.alarmy.clock.ui.components.ClockText
import com.prafull.alarmy.commons.AddAndDeleteBottomBar
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ClockScreen(navController: NavController, viewModel: ClockViewModel) {
    val cities by viewModel.cities.collectAsState()
    var selectionMode by remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<String>() }

    Scaffold(
        bottomBar = {
            AddAndDeleteBottomBar(selectionMode = selectionMode, addClicked = {
                navController.navigate(Routes.AddCityScreen)
            }) {
                viewModel.deleteCities(selectedItems.toList())
                selectedItems.clear()
                selectionMode = false
            }
        }
    ) { paddingValues ->
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = paddingValues,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { ClockText() }
            items(cities, key = { it.uid }) { city ->
                CityCard(city, selectionMode, selectedItems) {
                    selectionMode = true
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityCard(
    city: CityModel,
    selectionMode: Boolean = false,
    selectedItems: MutableList<String>,
    onLongPress: () -> Unit
) {
    val localZoneId = remember { ZoneId.systemDefault() }
    val cityZoneId = remember { ZoneId.of(city.timeZone) }
    val timeFormatter = remember { DateTimeFormatter.ofPattern("hh:mm a") }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("MMM dd, yyyy") }

    var currentTime by remember { mutableStateOf("") }
    var currentDate by remember { mutableStateOf("") }
    var timeDifference by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            val nowInCity = ZonedDateTime.now(cityZoneId)
            val nowLocal = ZonedDateTime.now(localZoneId)

            currentTime = nowInCity.format(timeFormatter)
            currentDate = nowInCity.format(dateFormatter)

            val duration =
                Duration.between(nowLocal.toLocalTime(), nowInCity.toLocalTime()).toMinutes()
            timeDifference = when {
                duration == 0L -> "Same time"
                duration > 0 -> "${duration / 60}h ${duration % 60}m ahead"
                else -> "${-duration / 60}h ${-duration % 60}m behind"
            }
            delay(60000)
        }
    }
    val isSelected = selectedItems.contains(city.uid)

    Card(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .combinedClickable(
                onClick = {
                    if (selectionMode) {
                        if (isSelected) selectedItems.remove(city.uid)
                        else selectedItems.add(city.uid)
                    }
                },
                onLongClick = {
                    onLongPress()
                    selectedItems.add(city.uid)
                }
            ),
        elevation = CardDefaults.cardElevation(3.dp),
    ) {
        Row(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(0.85f)
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Text(text = "$currentTime ${city.city}")
                Text(text = currentDate)
                Text(text = "Time Difference: $timeDifference")
            }
            if (selectionMode) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = {
                        if (it) selectedItems.add(city.uid)
                        else selectedItems.remove(city.uid)
                    },
                    modifier = Modifier.weight(.15f)
                )
            }
        }
    }
}
