package com.example.moviesapp.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.util.TableInfo
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moviesapp.movieList.data.remote.MovieApi
import com.example.moviesapp.movieList.util.RatingBar
import com.example.moviesapp.movieList.util.Resources

@Composable
@Preview(showBackground = true)
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value

    val backDropImagePath = state.movie?.backdrop_path.orEmpty()

    val posterImagePath = state.movie?.poster_path.orEmpty()

    val backState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + backDropImagePath.removePrefix("/"))
            .size(Size.ORIGINAL)
            .build()
    ).state


    val posterState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + posterImagePath.removePrefix("/"))
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            when {
                backState is AsyncImagePainter.State.Error || backDropImagePath.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                MaterialTheme
                                    .colorScheme
                                    .primaryContainer
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Rounded.ImageNotSupported,
                            contentDescription = null,
                            modifier = Modifier
                                .size(70.dp)
                        )
                    }
                }

                backState is AsyncImagePainter.State.Success -> {
                    Image(
                        painter = backState.painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .height(240.dp)
                ) {
                    when {
                        posterState is AsyncImagePainter.State.Error || posterImagePath.isEmpty() -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        MaterialTheme
                                            .colorScheme
                                            .primaryContainer,
                                    )
                                    .clip(shape = RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Rounded.ImageNotSupported,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(70.dp)
                                )
                            }
                        }

                        posterState is AsyncImagePainter.State.Success -> {
                            Image(
                                painter = posterState.painter,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(shape = RoundedCornerShape(12.dp)),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                state.movie?.let { movie ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        state.movie.title?.let {
                            Text(
                                text = it,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            RatingBar()
                            Spacer(modifier = Modifier.height(16.dp))
                            state.movie.original_language?.let { text ->
                                Text(
                                    text = text,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "rating  $state.movie.vote_count",
                            )
                        }
                    }
                }
            }
            state.movie?.overview?.let {
                Text(
                    text = it
                )
            }
        }
    }
}