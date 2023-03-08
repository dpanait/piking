package com.yubstore.piking.util

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yubstore.piking.model.AlmacenModel
import com.yubstore.piking.service.APP_DATA
import androidx.compose.ui.text.TextStyle

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
                Text(" ${bodyContent.value}", style = TextStyle(fontSize = 12.sp) )
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