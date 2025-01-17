package com.easy.wallet.design.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.easy.wallet.design.R

@Composable
fun DynamicAsyncImage(
  imageUrl: String,
  contentDescription: String?,
  modifier: Modifier = Modifier,
  placeholder: Painter = painterResource(R.drawable.wallet_android_design_system_ic_placeholder_default)
) {
  val iconTint = MaterialTheme.colorScheme.primary
  var isLoading by remember { mutableStateOf(true) }
  var isError by remember { mutableStateOf(false) }
  val imageLoader = rememberAsyncImagePainter(
    model = imageUrl,
    onState = { state ->
      isLoading = state is AsyncImagePainter.State.Loading
      isError = state is AsyncImagePainter.State.Error
    }
  )
  val isLocalInspection = LocalInspectionMode.current
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
  ) {
    if (isLoading && !isLocalInspection) {
      // Display a progress bar while loading
      CircularProgressIndicator(
        modifier = Modifier
          .align(Alignment.Center)
          .size(80.dp),
        color = MaterialTheme.colorScheme.tertiary
      )
    }
    Image(
      modifier = Modifier.fillMaxSize(),
      contentScale = ContentScale.FillBounds,
      painter = if (isError.not() && !isLocalInspection) imageLoader else placeholder,
      contentDescription = contentDescription
//            colorFilter = ColorFilter.tint(iconTint)
    )
  }
}
