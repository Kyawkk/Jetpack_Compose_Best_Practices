@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mycity.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycity.data.LocalCityDataProvider
import com.example.mycity.model.Category

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    canNavigateUp: Boolean = false,
    categories: List<Category> = emptyList(),
    onItemClick: (Category) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(categories){ category ->
            CategoryListItem(category, onItemClick = { onItemClick(category) })
        }
    }
}

@Composable
fun CategoryListItem(
    category: Category,
    onItemClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = Modifier.fillMaxWidth(),
        onClick = { onItemClick(category) }
    ) {
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Box (
                modifier = Modifier.size(128.dp),
            ) {
                Image(
                    painter = painterResource(id = category.categoryImageId),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
            }
            Column (
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = category.categoryTitleId,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${category.recommendations.size} Places"
                )
            }
        }
    }
}

@Composable
@Preview (showBackground = true, showSystemUi = true)
fun CategoryScreenPreview() {

}