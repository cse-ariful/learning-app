package com.online.learning.features.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.online.learning.R
import com.online.learning.ui.theme.ELearningTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState=scaffoldState,
        backgroundColor = Color.White,
        topBar = {
            Toolbar(navController){
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        drawerContent = {
                 NavDrawer()
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(contentPadding = PaddingValues(bottom = 50.dp)) {
                        item { SearchBox() }
                        item { FeaturedCourses() }
                        item { TopMentors() }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 32.dp, vertical = 4.dp)
                            .height(48.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(24.dp), spotColor = Color(0x3D485B0F))
                            .background(Color.White, shape = RoundedCornerShape(24.dp))
                    ) {

                    }
                }
            }
        },
    )
}

@Composable
fun NavDrawer() {
    Box(modifier = Modifier.fillMaxWidth(0.6f)){

    }
}

@Composable
fun TopMentors() {
    val configuration = LocalConfiguration.current
    Column(modifier = Modifier.padding(top = 12.dp)) {
        SectionTitle("Top Mentors") {}
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier, contentPadding = PaddingValues(start = 16.dp)
        ) {
            for (i in 0..5) item {
                BuildCourseItem(configuration)
            }

        }
    }
}

@Composable
private fun SectionTitle(title: String, onShowAll: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title, modifier = Modifier.clickable { onShowAll() }, style = MaterialTheme.typography.titleMedium.copy(color = Color(0xff261D1B))
        )
        Text(text = "See all", color = Color(0xff292D32))
    }
}

@Composable
fun FeaturedCourses() {
    val configuration = LocalConfiguration.current
    Column {
        SectionTitle("Featured Courses") {}
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 4.dp),
            contentPadding = PaddingValues(start = 16.dp)
        ) {
            for (i in 0..5) item {
                BuildMentorsItem(configuration)
            }

        }
    }
}

@Composable
private fun BuildMentorsItem(configuration: Configuration) {
    Box(modifier = Modifier) {
        Column(modifier = Modifier.width((configuration.screenWidthDp * 0.8f).dp)) {
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .aspectRatio(1.4f),
                painter = painterResource(id = R.drawable.sample),
                contentScale = ContentScale.Crop,
                contentDescription = ""

            )
            Text(
                text = "Investement Analysis & Portfo",
                maxLines = 1,
                modifier = Modifier.padding(top = 8.dp),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star, contentDescription = "", tint = Color(0xffF0AC4F), modifier = Modifier.height(16.dp)
                )
                Text(
                    text = "4.6", color = Color(0xff292D32), style = MaterialTheme.typography.labelMedium
                )
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "",
                    tint = Color(0xffB0BCC5),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .height(16.dp)
                )
                Text(
                    text = "Leslie Alexander", color = Color(0xff292D32), style = MaterialTheme.typography.labelMedium
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Free", style = MaterialTheme.typography.titleMedium.copy(color = Color(0xff4673FF))
                )
                Text(
                    text = "Leslie Alexander",
                    color = Color(0xff292D32),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .background(Color(0xffF8F9FD), shape = RoundedCornerShape(48.dp))
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                )
            }

        }
    }
}

@Composable
private fun BuildCourseItem(configuration: Configuration) {
    Box(modifier = Modifier) {
        Column(
            modifier = Modifier
                .width((configuration.screenWidthDp * 0.5f).dp)
                .background(Color(0xffFBFBFB), shape = RoundedCornerShape(8.dp))
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(width = 1.dp, color = Color.White, shape = CircleShape)
                    .border(width = 1.dp, color = Color.Blue, shape = CircleShape)
                    .padding(3.dp),
                painter = painterResource(id = R.drawable.sample),
                contentScale = ContentScale.Crop,
                contentDescription = ""

            )
            Text(
                text = "Annette Black",
                maxLines = 1,
                modifier = Modifier.padding(top = 8.dp),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Analysis Expert", color = Color(0xff292D32).copy(alpha = 0.7f), style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star, contentDescription = "", tint = Color(0xffF0AC4F), modifier = Modifier.height(16.dp)
                )
                Text(
                    text = "5.0",
                    color = Color(0xff292D32),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$200/",
                        color = Color(0xff292D32),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(start = 12.dp)
                    )
                    Text(
                        text = "5 session",
                        color = Color(0xff292D32),
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }

        }
    }
}

@Composable
fun SearchBox() {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = 12.dp, start = 24.dp, end = 24.dp)
            .background(
                Color(0xffF6F6F8), shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 8.dp, horizontal = 12.dp),
    ) {
        Icon(imageVector = Icons.Default.Search, contentDescription = "")
        Text(text = "Search")

    }
}

@Composable
fun Toolbar(navController: NavController,onToggleDrawer:()->Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Learner")
        },
        navigationIcon = {
            IconButton(onClick = {onToggleDrawer.invoke() }) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.sample_avatar),
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        },
        modifier = Modifier.padding(end = 12.dp),
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.White)
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    ELearningTheme() {
//        BuildCourseItem(LocalConfiguration.current)
        HomeScreen(navController = navController)
    }
}
