package com.prafull.alarmy.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun AddAndDeleteBottomBar(selectionMode: Boolean, addClicked: () -> Unit, deleteItems: () -> Unit) {
    BottomAppBar(containerColor = Color.Transparent) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (selectionMode) {
                FloatingActionButton(onClick = deleteItems) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Button"
                    )
                }
            } else {
                FloatingActionButton(onClick = addClicked, shape = CircleShape) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Button"
                    )
                }
            }
        }
    }
}