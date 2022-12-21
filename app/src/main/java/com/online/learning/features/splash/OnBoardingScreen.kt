package com.online.learning.features.splash

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.online.learning.R
import com.online.learning.ui.theme.ELearningTheme
import kotlinx.coroutines.launch


@Composable
fun OnBoardingScreen(navController: NavController, onComplete: () -> Unit) {
    PagerOnBoardingWidget(navController, onComplete)
}

data class PagerData(val title: String, val message: String, @DrawableRes val painting: Int) {

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerOnBoardingWidget(navController: NavController, onComplete: () -> Unit) {
    val pages = listOf(
        PagerData(
            title = "Access premium courses",
            message = "Learn about investment by enrolling our top class free and paid courses.",
            painting = R.drawable.illus_writing,
        ), PagerData(
            painting = R.drawable.illus_business_plan,
            title = "Get top class mentors",
            message = "Introduce yourself as a investor under the guidence of our expart advisors.",
        ), PagerData(
            painting = R.drawable.illus_finance,
            title = "Invest in most easiest way",
            message = "Invest your money in the most safest way with less commsion and get high ROI.",
        )

    )
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            count = 3, state = pagerState, modifier = Modifier.weight(1f)
        ) { currentPage ->
            val item = pages[currentPage]
            Column(
                modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = item.painting), modifier = Modifier.fillMaxHeight(0.7f), contentDescription = "illustrations"
                )
                Text(
                    text = item.title,
                    modifier = Modifier.fillMaxWidth(fraction = 0.8f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = item.message,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.8f)
                        .padding(top = 16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xff6C737C))
                )
                Spacer(modifier = Modifier.height(48.dp))

            }
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            indicatorShape = RoundedCornerShape(24.dp),
            indicatorHeight = 4.dp,
            activeColor = Color(0xff4673FF),
            inactiveColor = Color(0xff4673FF).copy(alpha = 0.2f),
            indicatorWidth = 32.dp
        )
        val coroutineScope = rememberCoroutineScope()
        Column(
            modifier = Modifier.weight(0.4f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    if (pagerState.currentPage < pagerState.pageCount - 1) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                        }
                    } else {
                        onComplete()
                    }
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(Color(0xff4673FF))
            ) {
                if (pagerState.currentPage < pagerState.pageCount - 1) Text(text = "Next")
                else Text(text = "Continue")

            }
            if (pagerState.currentPage > 0)
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }, modifier = Modifier.fillMaxWidth(0.8f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Back")
                }
            else Spacer(modifier = Modifier.height(48.dp))
            Spacer(modifier = Modifier.weight(1f))
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    ELearningTheme() {
        PagerOnBoardingWidget(navController = navController) {}
    }
}

