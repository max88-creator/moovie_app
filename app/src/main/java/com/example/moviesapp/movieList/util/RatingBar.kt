package com.example.moviesapp.movieList.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    starsModifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))
    Row(
        modifier = modifier
    ) {
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = starsColor,
                modifier = starsModifier,
            )
        }
        if (halfStar) {
            Icon(
                imageVector = Icons.Rounded.StarHalf,
                contentDescription = null,
                tint = starsColor,
                modifier = starsModifier
            )
        }
        repeat(unfilledStars) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = starsColor,
                modifier = starsModifier,
            )
        }
    }
}

@Composable
fun RatingItem(
    rating: Double,
    label: String,
    starsColor: Color = Color.Yellow,
    starSize: Int = 24
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        RatingBar(
            rating = rating,
            starsColor = starsColor,
            starsModifier = Modifier.size(starSize.dp)
        )
        Text(
            text = "Оценка: $rating",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}


@Preview(name = "Разные оценки", showBackground = true)
@Composable
fun RatingBarPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Компонент Рейтинга",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )


                RatingItem(rating = 5.0, label = "Максимальная оценка")
                RatingItem(rating = 4.5, label = "С половиной звезды")
                RatingItem(rating = 3.7, label = "3.7 звезды (округление вниз)")
                RatingItem(rating = 2.0, label = "Целое число звезд")
                RatingItem(rating = 0.0, label = "Без оценки")
                RatingItem(rating = 1.2, label = "1.2 звезды")

                Spacer(modifier = Modifier.height(24.dp))

                // Примеры с разными цветами
                Text(
                    text = "Разные цвета звезд",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                RatingItem(
                    rating = 4.0,
                    label = "Золотые звезды",
                    starsColor = Color(0xFFFFD700)
                )

                RatingItem(
                    rating = 3.5,
                    label = "Синие звезды",
                    starsColor = Color.Blue
                )

                RatingItem(
                    rating = 2.8,
                    label = "Красные звезды",
                    starsColor = Color.Red
                )

                // Пример с маленькими звездами
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Маленькие звезды (для компактного отображения)",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Фильм 1",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        RatingBar(
                            rating = 4.2,
                            starsModifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Фильм 2",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        RatingBar(
                            rating = 3.7,
                            starsModifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}