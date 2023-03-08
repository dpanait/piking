package com.yubstore.piking.views.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CircularIndeterminateProgressBar(
    isDisplayed: Boolean,
    modifier: Modifier?
) {
    var myModuifier: Modifier
    if(modifier == null){
        myModuifier = Modifier.fillMaxWidth().padding(top = 20.dp)
    } else {
        myModuifier = modifier
    }
    if (isDisplayed) {
        Row(
            modifier = myModuifier,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary
            )
        }
    }
}