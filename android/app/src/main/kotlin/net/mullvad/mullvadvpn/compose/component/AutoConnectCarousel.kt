package net.mullvad.mullvadvpn.compose.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
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
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (
                upperTextRef,
                backButtonRef,
                imageRef,
                nextButtonRef,
                lowerTextRef,
                pageIndicatorRef) =
                createRefs()
            HtmlText(
                modifier =
                    Modifier.padding(horizontal = Dimens.largePadding).constrainAs(upperTextRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(imageRef.top)
                    },
                htmlFormattedString =
                    textResource(
                        id =
                            when (page) {
                                0 -> R.string.carousel_slide_1_text_1
                                1 -> R.string.carousel_slide_2_text_1
                                else -> R.string.carousel_slide_3_text_1a
                            }
                    ),
                textSize = 16.sp.value,
                textColor =
                    MaterialTheme.colorScheme.onPrimary.copy(alpha = AlphaDescription).toArgb()
            )
            if (page != 0) {
                IconButton(
                    modifier =
                        Modifier.constrainAs(backButtonRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                        },
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
            }
            Image(
                modifier =
                    Modifier.padding(top = 18.dp, bottom = 3.dp).constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                painter =
                    when (page) {
                        0 -> painterResource(id = R.drawable.carousel_slide_1_cogwheel)
                        1 -> painterResource(id = R.drawable.carousel_slide_2_always_on)
                        else -> painterResource(id = R.drawable.carousel_slide_3_block_connections)

                    },
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = AlphaDescription)
            )
            if (page != 2) {
                IconButton(
                    modifier =
                        Modifier.constrainAs(nextButtonRef) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
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
            }

            HtmlText(
                modifier =
                    Modifier.padding(horizontal = Dimens.largePadding).constrainAs(lowerTextRef) {
                        top.linkTo(imageRef.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    },
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
            Row(
                Modifier.wrapContentHeight().fillMaxWidth().padding(top = 20.dp).constrainAs(
                    pageIndicatorRef
                ) {
                    top.linkTo(lowerTextRef.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
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
