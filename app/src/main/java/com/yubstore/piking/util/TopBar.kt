package com.yubstore.piking.util

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.yubstore.piking.model.AlmacenModel
import com.yubstore.piking.service.APP_DATA

@Composable
fun TopBar(
    title: String = "",
    buttonIcon: ImageVector,
    almacenModel: AlmacenModel,
    onButtonClicked: () -> Unit
) {
    var bodyContent = remember { mutableStateOf(APP_DATA.storeName) }
    TopAppBar(
        title = {
            Text(
                text = title
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End.also { Arrangement.SpaceAround }
            ){
                Text(" ${bodyContent.value}")
            }

        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() } ) {
                Icon(buttonIcon, contentDescription = "")
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        actions = {
            TopAppBarOptionMenu(bodyContent, almacenModel)
        }
    )
}