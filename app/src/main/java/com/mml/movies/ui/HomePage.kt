package com.mml.movies.ui

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.mml.movies.network.Constants
import com.mml.movies.network.models.Movies
import com.mml.movies.viewmodels.ActiveVM
import com.mml.movies.viewmodels.MoviesVM
import com.mml.movies.viewmodels.SearchVM
import com.mml.movies.viewmodels.TrendingVM
import java.util.*
import android.os.Build




@Composable
fun MovieActions(
    movie: Movies,
    mod: Modifier = Modifier.fillMaxWidth(),
    moviesViewModel: MoviesVM = hiltViewModel()
) {
    var bookmarked = moviesViewModel.bookmarked.subscribeAsState(initial = listOf<Movies>())
    var status by remember(bookmarked.value){
        mutableStateOf(
            bookmarked.value.any{it.id == movie.id}
        )
    }
    val context = LocalContext.current
    Row(
        modifier = mod,
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        IconButton(onClick = {
            if(status)
                moviesViewModel.removeBookmark(movie.id)
            else
                moviesViewModel.addBookmark(movie.id)
        }) {
            Icon(
                if(status)
                    Icons.Filled.Favorite
                else
                    Icons.Filled.FavoriteBorder,
                "Bookmark")
        }
        IconButton(onClick = {
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://www.testmovies.org/${movie.id}")
                // (Optional) Here we're setting the title of the content
                putExtra(Intent.EXTRA_TITLE, "Directly Share Movies")
                type = "text/html"
            }
            context.startActivity(Intent.createChooser(shareIntent, "Deep Link"))
        }) {
            Icon(Icons.Filled.Share, "Share")
        }
    }
}

@Composable
fun MovieDetailed(
    navController: NavController,
    id: Int,
    movieViewModel: MoviesVM = hiltViewModel()
) {
    var allMovies = movieViewModel.allMovies.subscribeAsState(initial = listOf<Movies>())
    var movie by remember(allMovies.value){
        var temp = allMovies.value.filter{it.id == id}
        mutableStateOf(
        if(temp.isNotEmpty())
            temp[0]
        else null)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column{
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
            ) {
                if(movie!=null){
                    var networkImagePainter = rememberImagePainter(
                        data = Constants.imageBaseUrl + movie!!.backdropPath,
                        builder = {
                            crossfade(true)
                            scale(Scale.FILL)
                        }
                    )
                    Image(networkImagePainter, "", contentScale = ContentScale.FillBounds, modifier = Modifier.fillMaxSize())
                }
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    },
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null, modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .background(Color(0x9FFFFFFF)))
                }
            }
            if(movie!=null)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                ){
                    Row{
                        Icon(Icons.Filled.Stars, "")
                        Text("${movie!!.voteAverage}")
                    }
                    Row{
                        Text("${movie!!.voteCount} Votes")
                    }
                }
        }
        if(movie!=null){
            Text(movie!!.title, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally))
            Text(movie!!.overview, modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally))
            MovieActions(movie!!,
                Modifier
                    .height(40.dp)
                    .fillMaxWidth())
        }
        else{
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("")
                Icon(Icons.Filled.Search, "")
                Text("No movie found using this id")
                Text("")
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MovieBox(
    navController: NavController,
    movie: Movies
){
    var networkImagePainter = rememberImagePainter(
        data = Constants.imageBaseUrl + movie.posterPath,
        builder = {
            crossfade(true)
        }
    )
    var overview = movie.overview
    val overviewMaxLength = 100
    if(overview.length > overviewMaxLength){
        overview = overview.chunkedSequence(overviewMaxLength) { "$it..." }.toList()[0]
    }
    Card(
        onClick = {
            Log.d("Compose Navigation", "MovieBox: Navigating to movie/${movie.id}")
            navController.navigate("movie/${movie.id}")
        },
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(230.dp),
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = networkImagePainter, "Sprite", modifier = Modifier.size(150.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly
                ){
                    Text(movie.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(overview)
                }
            }
            MovieActions(movie = movie, Modifier.fillMaxSize())
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ActiveMovies(
    navController: NavController,
    activeVMViewModel: ActiveVM = hiltViewModel()
){
    var movies = activeVMViewModel.active.subscribeAsState(initial = listOf<Movies>())
    val locale: String
    val context = LocalContext.current
    locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales.get(0).country
    } else {
        context.resources.configuration.locale.country
    }
    activeVMViewModel.locale = locale
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ){
        items(movies.value){
                movie -> MovieBox(navController, movie)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TrendingMovies(
    navController: NavController,
    trendingVM: TrendingVM = hiltViewModel()
) {
    var trendingMovies = trendingVM.trendingMovies.subscribeAsState(initial = listOf<Movies>())
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ){
        items(trendingMovies.value){
                movie -> MovieBox(navController, movie)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun BookmarkedMovies(
    navController: NavController,
    moviesVM: MoviesVM = hiltViewModel()
) {
    var bookmarked = moviesVM.bookmarked.subscribeAsState(initial = listOf<Movies>())
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ){
        items(bookmarked.value){
                movie -> MovieBox(navController, movie)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SearchMovies(
    navController: NavController,
    searchVM: SearchVM = hiltViewModel()
) {
    var results = searchVM.searchResults.subscribeAsState(initial = listOf<Movies>())
    Column {
        OutlinedTextField(
            value = searchVM.searchString.value,
            onValueChange = {searchVM.searchString.value = it; searchVM.search()},
            label = { Text("Search") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ){
            items(results.value){
                    movie -> MovieBox(navController, movie)
            }
        }
    }
}


sealed class Screen(val route: String, val desc: String, val icon: ImageVector) {
    object Trending : Screen("Trending", "Currently Trending", Icons.Filled.TrendingUp)
    object Search : Screen("Search", "Search Movies", Icons.Filled.Search)
    object Active : Screen("Active", "Now Playing", Icons.Filled.Movie)
    object Bookmark : Screen("Bookmarks", "Bookmarked", Icons.Filled.Bookmark)
}

@ExperimentalMaterialApi
@Composable
fun Home(){
    val items = listOf(Screen.Trending, Screen.Search, Screen.Bookmark, Screen.Active)
    val navController = rememberNavController()
    Scaffold(
        content = {
                innerPadding ->
            NavHost(navController, startDestination = Screen.Trending.route, Modifier.padding(innerPadding)) {
                composable(Screen.Active.route) { ActiveMovies(navController) }
                composable(Screen.Trending.route) { TrendingMovies(navController) }
                composable(Screen.Search.route) { SearchMovies(navController) }
                composable(Screen.Bookmark.route) {BookmarkedMovies(navController)}
                composable(
                    "movie/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType }),
                    deepLinks = listOf(navDeepLink { uriPattern = "${Constants.appUri}/{id}" })
                ){backStackEntry ->
                    MovieDetailed(navController, backStackEntry.arguments?.getInt("id") ?: 0)
                }
            }
        },
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = {Text(screen.route)},
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    )
}