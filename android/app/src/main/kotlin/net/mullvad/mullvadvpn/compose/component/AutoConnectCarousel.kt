package net.mullvad.mullvadvpn.compose.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.mullvad.mullvadvpn.R
import net.mullvad.mullvadvpn.lib.theme.Dimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoConnectCarousel() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = Dimens.largePadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text =
                    when (page) {
                        0 -> stringResource(id = R.string.carousel_slide_1_text_1)
                        1 -> stringResource(id = R.string.carousel_slide_2_text_1)
                        2 -> stringResource(id = R.string.carousel_slide_3_text_1)
                        else -> ""
                    }
            )
            Spacer(modifier = Modifier.padding(18.dp))
            Image(
                painter =
                    when (page) {
                        0 -> painterResource(id = R.drawable.carousel_slide_1_cogwheel)
                        1 -> painterResource(id = R.drawable.carousel_slide_2_always_on)
                        2 -> painterResource(id = R.drawable.carousel_slide_3_block_connections)
                        else -> painterResource(id = R.drawable.launch_logo)
                    },
                contentDescription = null,
            )
            Spacer(modifier = Modifier.padding(3.dp))
            Text(
                text =
                    when (page) {
                        0 -> stringResource(id = R.string.carousel_slide_1_text_2)
                        1 -> stringResource(id = R.string.carousel_slide_2_text_2)
                        2 -> stringResource(id = R.string.carousel_slide_3_text_2)
                        else -> ""
                    }
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Row(
                Modifier.wrapContentHeight().fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.LightGray else Color.DarkGray
                    Box(
                        modifier =
                            Modifier.padding(2.dp).clip(CircleShape).background(color).size(6.dp)
                    )
                }
            }
        }
    }
}
