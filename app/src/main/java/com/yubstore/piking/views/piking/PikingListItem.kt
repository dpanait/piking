package com.yubstore.piking.views.piking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yubstore.piking.R
import com.yubstore.piking.service.Piking

@Composable
fun PikingListItem(piking: Piking, onItemClick: (Piking) -> Unit) {
    /*Row(
        modifier = Modifier
            .clickable(onClick = { onItemClick(piking) })
            .background(colorResource(id = R.color.searchBarBackground))
            .height(57.dp)
            .fillMaxWidth()
            .padding(PaddingValues(8.dp, 16.dp))
    ) {
        Text(text = piking.city!!, fontSize = 18.sp, color = Color.White)
        Text(text = piking.orders_id, fontSize = 18.sp, color = Color.White)
    }*/
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable{  onItemClick(piking)  },
        elevation = 10.dp

    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                buildAnnotatedString {
                    append("SKU: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF363533),fontSize = 20.sp)
                    ) {
                        append(piking.orders_sku)
                    }
                }

            )
            Text(
                buildAnnotatedString {
                    append("Ciudad: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {
                        var city = piking.city
                        if(piking.city == null){
                            city  = ""
                        }

                        append(city!!)

                    }
                }
            )
            Text(
                buildAnnotatedString {
                    var postalCode = piking.postcode
                    if(piking.postcode == null){
                        postalCode = ""
                    }
                    append("C.Postal: $postalCode")

                }
            )
            Text(
                buildAnnotatedString {
                    append("Tipo factura: ${piking.orders_type_id}")

                }
            )
            Text(
                buildAnnotatedString {
                    append("Fecha: ${piking.date_purchased}")

                }
            )
            /*Row{
                Text(text = piking.orders_id, fontSize = 18.sp, color = Color.White)
            }*/
        }

    }
}