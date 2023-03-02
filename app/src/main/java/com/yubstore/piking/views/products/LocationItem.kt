package com.yubstore.piking.views.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yubstore.piking.service.Inventory
import com.yubstore.piking.service.Piking

@Composable
fun LocationItem(productLocation: Inventory, onItemClick: (Inventory) -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable{  onItemClick(productLocation)  },
        elevation = 10.dp

    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                buildAnnotatedString {
                    append("Localización: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF363533),fontSize = 20.sp)
                    ) {
                        append("111" + productLocation.location.padStart(12, '0'))
                    }
                }

            )
            Text(
                buildAnnotatedString {
                    append("Id producto: ${productLocation.productsId}")

                }
            )
            Text(
                buildAnnotatedString {
                    append("Cantidad: ${productLocation.quantityProducts}")

                }
            )

        }

    }
}