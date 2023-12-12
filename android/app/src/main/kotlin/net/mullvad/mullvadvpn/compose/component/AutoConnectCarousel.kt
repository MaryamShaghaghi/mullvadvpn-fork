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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import net.mullvad.mullvadvpn.R
import net.mullvad.mullvadvpn.lib.theme.AppTheme
import net.mullvad.mullvadvpn.lib.theme.Dimens
import net.mullvad.mullvadvpn.lib.theme.color.AlphaDescription

@Preview
@Composable
fun PreviewAutoConnectCarousel() {
    AppTheme { AutoConnectCarousel() }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoConnectCarousel() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
        val scope = rememberCoroutineScope()
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(horizontal = Dimens.largePadding),
                text =
                    when (page) {
                        0 -> stringResource(id = R.string.carousel_slide_1_text_1)
                        1 -> stringResource(id = R.string.carousel_slide_2_text_1)
                        else -> stringResource(id = R.string.carousel_slide_3_text_1a)
                    },
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = AlphaDescription)
            )
            if (page == 2) {
                HtmlText(
                    modifier = Modifier.padding(horizontal = Dimens.largePadding),
                    htmlFormattedString = textResource(id = R.string.carousel_slide_3_text_1b),
                    textSize = 16.sp.value,
                    textColor =
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = AlphaDescription).toArgb()
                )
            }

            Spacer(modifier = Modifier.padding(18.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (page != 0) {
                    IconButton(
                        onClick = {
                            scope.launch { pagerState.scrollToPage(pagerState.currentPage - 1) }
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_chevron),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.rotate(180f).alpha(0.6f)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.padding(18.dp))
                }

                Image(
                    painter =
                        when (page) {
                            0 -> painterResource(id = R.drawable.carousel_slide_1_cogwheel)
                            1 -> painterResource(id = R.drawable.carousel_slide_2_always_on)
                            else ->
                                painterResource(id = R.drawable.carousel_slide_3_block_connections)
                        },
                    contentDescription = null,
                )

                if (page != 2) {
                    IconButton(
                        onClick = {
                            scope.launch { pagerState.scrollToPage(pagerState.currentPage + 1) }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_chevron),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(Dimens.titleIconSize).alpha(0.6f)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.padding(18.dp))
                }
            }

            Spacer(modifier = Modifier.padding(3.dp))
            HtmlText(
                modifier = Modifier.padding(horizontal = Dimens.largePadding),
                htmlFormattedString =
                    textResource(
                        id =
                            when (page) {
                                0 -> R.string.carousel_slide_1_text_2
                                1 -> R.string.carousel_slide_2_text_2
                                else -> R.string.carousel_slide_3_text_2
                            }
                    ),
                textSize = 16.sp.value,
                textColor =
                    MaterialTheme.colorScheme.onPrimary.copy(alpha = AlphaDescription).toArgb()
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
