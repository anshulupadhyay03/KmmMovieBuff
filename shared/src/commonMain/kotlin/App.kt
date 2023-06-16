import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import decompose.MovieBuffRoot
import kotlinx.coroutines.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import style.MovieBuffTheme
import ui.features.MovieList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(root: MovieBuffRoot) {


    MovieBuffTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        AppDrawer()
                    }
                }
            ) {
                AppScaffoldContent(root) {
                    scope.launch {
                        drawerState.open()
                    }
                }
            }
        }
    }
}

@Composable
fun AppDrawer() {
    Row {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(10.dp)
                .border(2.dp, Color.Gray)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize(),
                textAlign = TextAlign.Center,
                text = "Hi, All good!"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffoldContent(root: MovieBuffRoot, onHamburgerClicked: () -> Unit) {

    var bottomBarVisibilityState by rememberSaveable { (mutableStateOf(true)) }
    val topBarVisibilityState by rememberSaveable { (mutableStateOf(true)) }
    Scaffold(
        topBar = {
            SetupTopBar(onHamburgerClicked, topBarVisibilityState)
        },
        /*bottomBar = {
            SetupBottomBar(navController, bottomBarVisibilityState)
        }*/
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Children(root.childStack){
                when(val child = it.instance){
                    is MovieBuffRoot.Child.MainScreen ->{
                        MovieList(child.mainScreenComponent)
                    }

                    is MovieBuffRoot.Child.DetailScreen ->{

                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun SetupTopBar(
    onHamburgerClicked: () -> Unit,
    topBarVisibilityState: Boolean
) {
    val scope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = topBarVisibilityState,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
    ) {
        TopAppBar(
            title = { Text(text = "Movie Buff"/*stringResource(id = R.string.app_name)*/) },
            navigationIcon = {
                IconButton(
                    onClick = {
                        onHamburgerClicked()
                    }) {
                    Icon(
                        painterResource("menu_top_icon.xml"),
                        contentDescription = null
                    )
                }
            },
        )
    }
}


/*@Composable
private fun ShowMainContent(paddingValues: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home",
        Modifier.padding(paddingValues)
    ) {
        bottomNavGraph(navController)
        composable(
            "details/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            MovieDetailsScreen()
        }
    }
}*/

/*
@Composable
fun SetupBottomBar(bottomBarVisibilityState: Boolean) {
    AnimatedVisibility(
        visible = bottomBarVisibilityState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        NavigationBar {
            BOTTOM_LEVEL_NAVIGATION.forEach { tabDetails ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == tabDetails.route } == true,
                    onClick = {
                        navController.navigate(tabDetails.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when re-selecting a previously selected item
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(
                                id = if (currentDestination?.hierarchy?.any { it.route == tabDetails.route } == true) tabDetails.selectedIcon else tabDetails.unselectedIcon
                            ),
                            contentDescription = null
                        )
                    },
                    label = { Text(text = stringResource(id = tabDetails.iconTextId)) }
                )
            }
        }
    }
}

*/
