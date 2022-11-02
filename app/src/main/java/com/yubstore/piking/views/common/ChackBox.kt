package com.yubstore.piking.views.common


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckBox(status: Boolean, onClick: (Any?) -> Unit): MutableState<Boolean> {
    val checkedState = remember { mutableStateOf(status) }
    Checkbox(
        modifier = Modifier.width(60.dp),

        checked = checkedState.value,
        onCheckedChange = {
            checkedState.value = it
            onClick(it)
        }
    )
    return checkedState
}