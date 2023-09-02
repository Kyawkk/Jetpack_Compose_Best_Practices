@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.myapplication.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Drafts
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import com.example.myapplication.data.Email
import com.example.myapplication.data.MailboxType
import com.example.myapplication.data.local.LocalAccountsDataProvider
import com.example.myapplication.ui.ReplyUiState
import com.example.myapplication.utils.ReplyContentType
import com.example.myapplication.utils.ReplyNavigationType

@Composable
fun ReplyHomeScreen(
    replyUiState: ReplyUiState,
    contentType: ReplyContentType,
    replyNavigationType: ReplyNavigationType,
    onTabPressed: (MailboxType) -> Unit,
    onEmailCardPressed: (Email) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navigationItemContentList = listOf(
        NavigationItemContent(
            mailboxType = MailboxType.Inbox,
            icon = Icons.Default.Inbox,
            text = stringResource(id = R.string.tab_inbox)
        ),
        NavigationItemContent(
            mailboxType = MailboxType.Sent,
            icon = Icons.Default.Send,
            text = stringResource(id = R.string.tab_sent)
        ),
        NavigationItemContent(
            mailboxType = MailboxType.Drafts,
            icon = Icons.Default.Drafts,
            text = stringResource(id = R.string.tab_drafts)
        ),
        NavigationItemContent(
            mailboxType = MailboxType.Spam,
            icon = Icons.Default.Report,
            text = stringResource(id = R.string.tab_spam)
        )
    )

    if(replyNavigationType == ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER){
        PermanentNavigationDrawer(drawerContent = {
            PermanentDrawerSheet (
                Modifier.width(dimensionResource(id = R.dimen.drawer_width))
            ) {
                NavigationDrawerContent(
                    selectedDestination = replyUiState.currentMailbox,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.inverseOnSurface)
                        .padding(dimensionResource(id = R.dimen.drawer_padding_content))
                )
            }
        }) {
            ReplyAppContent(
                contentType = contentType,
                navigationType = replyNavigationType,
                replyUiState = replyUiState,
                onTabPressed = onTabPressed,
                onEmailCardPressed = onEmailCardPressed,
                navigationItemContentList = navigationItemContentList,
                modifier = modifier
            )
        }
    }
    else{
        if(replyUiState.isShowingHomepage){
            ReplyAppContent(
                contentType = contentType,
                navigationType = replyNavigationType,
                replyUiState = replyUiState,
                onTabPressed = onTabPressed,
                onEmailCardPressed = onEmailCardPressed,
                navigationItemContentList = navigationItemContentList,
                modifier = modifier
            )
        }else{
            ReplyDetailsScreen(
                isFullScreen = replyNavigationType == ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER,
                replyUiState = replyUiState, onBackPressed = onDetailScreenBackPressed)
        }
    }
}

@Composable
private fun ReplyAppContent(
    contentType: ReplyContentType,
    navigationType: ReplyNavigationType,
    replyUiState: ReplyUiState,
    onTabPressed: ((MailboxType) -> Unit),
    onEmailCardPressed: (Email) -> Unit,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        val navigationRailContentDescription = stringResource(R.string.navigation_rail)
        AnimatedVisibility(visible = navigationType == ReplyNavigationType.NAVIGATION_RAIL) {
            ReplyNavigationRail(
                currentTab = replyUiState.currentMailbox,
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationItemContentList,
                modifier = Modifier
                    .testTag(navigationRailContentDescription)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            if (contentType == ReplyContentType.LIST_ONLY){
                ReplyListOnlyContent(
                    replyUiState = replyUiState,
                    onEmailCardPressed = onEmailCardPressed,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            horizontal = dimensionResource(R.dimen.email_list_only_horizontal_padding)
                        )
                )
            }else {
                ReplyListAndDetailContent(
                    isFullScreen = navigationType == ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER,
                    replyUiState = replyUiState,
                    onEmailCardPressed = onEmailCardPressed,
                    modifier = Modifier.weight(1f)
                )
            }
            val bottomNavigationContentDescription = stringResource(R.string.navigation_bottom)
            ReplyBottomNavigationBar(
                navigationType = navigationType,
                currentTab = replyUiState.currentMailbox,
                onTabPressed = onTabPressed,
                navigationItemContentList = navigationItemContentList,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ReplyNavigationRail(
    currentTab: MailboxType,
    onTabPressed: ((MailboxType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationRailItem(
                selected = currentTab == navItem.mailboxType,
                onClick = { onTabPressed(navItem.mailboxType) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                }
            )
        }
    }
}

@Composable
private fun ReplyBottomNavigationBar(
    navigationType: ReplyNavigationType,
    currentTab: MailboxType,
    onTabPressed: ((MailboxType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(visible = navigationType == ReplyNavigationType.BOTTOM_NAVIGATION) {
        NavigationBar(modifier = modifier) {
            for (navItem in navigationItemContentList) {
                NavigationBarItem(
                    selected = currentTab == navItem.mailboxType,
                    onClick = { onTabPressed(navItem.mailboxType) },
                    icon = {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = navItem.text
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun NavigationDrawerContent(
    selectedDestination: MailboxType,
    onTabPressed: ((MailboxType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        NavigationDrawerHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.profile_image_padding)),
        )
        for (navItem in navigationItemContentList) {
            NavigationDrawerItem(
                selected = selectedDestination == navItem.mailboxType,
                label = {
                    Text(
                        text = navItem.text,
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.drawer_padding_header))
                    )
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                ),
                onClick = { onTabPressed(navItem.mailboxType) }
            )
        }
    }
}

@Composable
private fun NavigationDrawerHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ReplyLogo(modifier = Modifier.size(dimensionResource(R.dimen.reply_logo_size)))
        ReplyProfileImage(
            drawableResource = LocalAccountsDataProvider.defaultAccount.avatar,
            description = stringResource(id = R.string.profile),
            modifier = Modifier
                .size(dimensionResource(R.dimen.profile_image_size))
        )
    }
}

private data class NavigationItemContent(
    val mailboxType: MailboxType,
    val icon: ImageVector,
    val text: String
)