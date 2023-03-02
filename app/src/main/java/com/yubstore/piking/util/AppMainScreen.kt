package com.yubstore.piking.util

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yubstore.piking.model.AlmacenModel
import com.yubstore.piking.model.ProductsModel
import com.yubstore.piking.service.ProductSearch
import com.yubstore.piking.views.account.AccountScreen
import com.yubstore.piking.views.home.HomeScreen
import com.yubstore.piking.views.login.LoginScreen
import com.yubstore.piking.views.piking.PikingDetail
import com.yubstore.piking.views.piking.PikingScreen
import com.yubstore.piking.views.products.LocationItemDetails
import com.yubstore.piking.views.products.LocationsItems
import com.yubstore.piking.views.products.MoveProductsScreen
import com.yubstore.piking.views.products.ProductsScreen

@SuppressLint("SuspiciousIndentation")
@Composable
fun AppMainScreen() {
    val navController = rememberNavController()
    val currentScreen = remember { mutableStateOf(DrawerScreens.Home.route) }
    var productsModel = ProductsModel()
    var almacenModel = AlmacenModel()

    Surface(color = MaterialTheme.colors.background) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val openDrawer = {
            scope.launch {
                drawerState.open()
            }
        }
        ModalDrawer(

            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerShape = MaterialTheme.shapes.small,
            drawerContent = {
                Column(
                    //Modifier.width(200.dp).background(Color.Green)
                ){
                    Drawer(
                        selectedScreen = currentScreen.value,
                        onDestinationClicked = { route, param ->
                            scope.launch {
                                drawerState.close()
                            }
                            println("Route: $route, $param")
                            var route_screen = route
                            if(param != ""){
                                route_screen= route.replace("{idcliente}", param)
                                println("New route: $route_screen")
                            }
                            navController.navigate(route_screen) {

                                /*popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }*/
                                launchSingleTop = true

                            }
                        },
                        onMenuSelected = { drawerScreen: DrawerScreens ->

                            println("DreaverScreen: ${drawerScreen.route}")
                            currentScreen.value =  drawerScreen.route

                        }
                    )
                }

            }
        ) {
            NavHost(
                navController = navController,
                startDestination = DrawerScreens.Home.route//DrawerScreens.Login.route //DrawerScreens.Home.route
            ) {
                composable(DrawerScreens.Home.route, arguments = listOf(navArgument("idcliente") { type = NavType.StringType })) { backStackEntry ->
                    var idcliente = backStackEntry.arguments?.getString("idcliente")
                    HomeScreen(
                        navController,
                        idcliente,
                        almacenModel,
                        openDrawer = {
                            openDrawer()
                        }
                    )
                }
                composable(DrawerScreens.Account.route) {
                    AccountScreen(
                        navController,
                        almacenModel,
                        openDrawer = {
                            openDrawer()
                        }
                    )
                }
                /*composable(DrawerScreens.Piking.route) {
                    PikingScreen(
                        navController,
                        openDrawer = {
                            openDrawer()
                        }
                    )
                }*/
                composable(DrawerScreens.Piking.route, arguments = listOf(navArgument("idcliente") { type = NavType.StringType })) { backStackEntry ->
                    var idcliente = backStackEntry.arguments?.getString("idcliente")
                    PikingScreen(
                        navController,
                        idcliente,
                        almacenModel,
                        openDrawer = {
                            openDrawer()
                        }
                    )
                }
                composable(DrawerScreens.Products.route) {
                    var productSearch = navController.previousBackStackEntry?.savedStateHandle?.get<ProductSearch>("product_search")

                        ProductsScreen(
                            navController,
                            productsModel,
                            almacenModel,
                            openDrawer = {
                                openDrawer()
                            },
                            productSearch
                        )

                }
                composable(DrawerScreens.MoveProducts.route) {
                    MoveProductsScreen(
                        navController,
                        almacenModel,
                        openDrawer = {
                            openDrawer()
                        }
                    )
                }
                composable(DrawerScreens.Login.route ) {
                    LoginScreen(
                        navController
                    )
                }
                composable("multiLocation/{productsId}", arguments = listOf(navArgument("productsId") { type = NavType.StringType })){backStackEntry->
                    var productsId = backStackEntry.arguments?.getString("productsId")
                    LocationsItems(navController, productsId, productsModel)
                }
                composable(
                    "location/{location}/{productsId}/{quantity}",
                    arguments = listOf(
                        navArgument("location") { type = NavType.StringType },
                        navArgument("productsId") { type = NavType.StringType },
                        navArgument("quantity") { type = NavType.StringType }
                    )){backStackEntry->
                    var location = backStackEntry.arguments?.getString("location")
                    var productsId = backStackEntry.arguments?.getString("productsId")
                    var quantity = backStackEntry.arguments?.getString("quantity")
                    if (location != null && productsId != null && quantity != null) {
                            LocationItemDetails(
                                location,
                                productsId,
                                quantity.toInt(),
                                openDrawer = {
                                    openDrawer()
                                }
                            )
                    }
                }
                composable(
                    "products/{idCliente}/{ordersId}",
                    arguments = listOf(
                        navArgument("idCliente") {
                            type = NavType.StringType
                        },
                        navArgument("ordersId") {
                            type = NavType.StringType
                        }
                    ),
                    /*arguments = listOf(
                        navArgument("ordersId") {
                            type = NavType.StringType
                        }
                    )*/
                ) { backStackEntry ->
                    var idCliente = backStackEntry.arguments?.getString("idCliente")!!
                    var ordersId = backStackEntry.arguments?.getString("ordersId")!!
                    var product_search = navController.previousBackStackEntry?.savedStateHandle?.get<ProductSearch>("product_search")
                    println("getSavedStateProvider: $product_search")
                    PikingDetail(
                        navController,
                        idCliente,
                        ordersId,
                        product_search
                    )

                }
            }
        }
    }
}

