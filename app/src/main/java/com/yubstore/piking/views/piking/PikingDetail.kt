package com.yubstore.piking.views.piking

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yubstore.piking.model.PikingModel
import com.yubstore.piking.service.ProductSearch

@Composable
fun PikingDetail(
    idCliente: String,
    ordersId: String,
    product_search: ProductSearch?
){
    val coroutineScope = rememberCoroutineScope()
    println("IdCliente: $idCliente OrdersId: $ordersId")
    val pikingModel = PikingModel()
    pikingModel.getPikingItem(coroutineScope, idCliente, ordersId)
    val pikingItem = pikingModel.pikingItem//getListOfCountries(coroutineScope, idCliente)
    println("PikingItem: $pikingItem")
    println("product_search: $product_search")
    LazyColumn {
        items(pikingItem) { product ->
            println("ProductsScreen: $product")
            //Text(String(product.products_name.toByteArray(), Charsets.UTF_8))
            val quantity = product.products_quantity.replace(".", ",")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                ,elevation = 10.dp
            ){
                Row(
                    modifier = Modifier.padding(10.dp, 15.dp)
                ){
                    Column() {
                        Text(
                            product.products_sku,
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.padding(3.dp))
                        Text(product.barcode)
                        Text(String(product.products_name.toByteArray(), Charsets.UTF_8))
                        Text((quantity).toString() + " Ud")

                        if (product.image != null) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("https://yuubbb.com/images/" + product.image)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(100.dp)
                                    .padding(10.dp)
                            )
                        }

                        Box(
                            modifier = Modifier.padding(5.dp, 15.dp)
                                .align(Alignment.End)
                        ){
                            Column() {
                                IconButton(
                                    onClick = { Log.d("Icon", "Piking: ${product.products_id}") }
                                ) {
                                    Icon(
                                        Icons.Rounded.CheckCircle,
                                        contentDescription = "Localized description"
                                    )
                                }
                            }
                        }
                    }

                }


                //"https://example.com/image.jpg"

                /*Row(){
                    Column() {
                        Text(String(product.products_name.toByteArray(), Charsets.UTF_8))
                    }
                }*/



            }
        }
    }
}
@Preview
@Composable
fun PreviewPikingDetail() {
    PikingDetail("192", "2263162", ProductSearch("192", "2263162"))
}

