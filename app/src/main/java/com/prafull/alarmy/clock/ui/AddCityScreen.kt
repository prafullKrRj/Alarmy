package com.prafull.alarmy.clock.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafull.alarmy.clock.data.cityTimes
import com.prafull.alarmy.clock.data.db.CityTime
import com.prafull.alarmy.ui.goBackStack

@Composable
fun AddCityScreen(navController: NavController, viewModel: ClockViewModel) {
    val allCities =
        rememberSaveable { cityTimes }
    val searchQuery = rememberSaveable { mutableStateOf("") }
    val filteredCities = allCities.filter {
        it.city.contains(searchQuery.value, ignoreCase = true) ||
                it.country.contains(searchQuery.value, ignoreCase = true)
    }
    Scaffold(
        topBar = {
            AddCityTopAppBar(navController)
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                item(contentType = "search") {
                    OutlinedTextField(
                        value = searchQuery.value,
                        onValueChange = { searchQuery.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        label = { Text("Search city") },
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        maxLines = 1,
                        trailingIcon = {
                            IconButton(onClick = {
                                searchQuery.value = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear"
                                )

                            }
                        }
                    )
                }
                items(filteredCities, key = { it.uid }) { city ->
                    CityItem(cityModel = city, navController, viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddCityTopAppBar(navController: NavController) {
    CenterAlignedTopAppBar(title = {
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.W500)) {
                append("Select city\n")
            }
            withStyle(style = SpanStyle(fontSize = 14.sp, fontWeight = FontWeight.W400)) {
                append("Time Zones")
            }
        })
    }, navigationIcon = {
        IconButton(onClick = { navController.goBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
    })
}


@Composable
private fun CityItem(cityModel: CityTime, navController: NavController, viewModel: ClockViewModel) {
    ListItem(headlineContent = {
        Text(text = cityModel.city)
    }, supportingContent = {
        Text(text = cityModel.country + " " + cityModel.gmtZone)
    }, modifier = Modifier
        .clickable {
            viewModel.addCity(cityModel)
            navController.goBackStack()
        }
        .padding(horizontal = 8.dp)
    )
}