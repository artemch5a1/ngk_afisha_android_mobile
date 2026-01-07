package com.example.ngkafisha.presentation.components

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.domain.eventService.models.Event
import com.example.domain.eventService.models.Invitation
import com.example.ngkafisha.R
import java.util.UUID

@Composable
fun EventImageWithAutoTextColor(
    eventCard: Event?,
    imageUrl: String?,
    onBack: () -> Unit,
    imageModifier: Modifier = Modifier
) {
    var isDarkImage by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(imageUrl) {
        if (imageUrl.isNullOrEmpty()) return@LaunchedEffect

        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)   // важно, чтобы можно было получить Bitmap
            .build()

        val result = (loader.execute(request) as? SuccessResult)?.drawable
        val bmp = (result as? BitmapDrawable)?.bitmap ?: return@LaunchedEffect

        val palette = Palette.from(bmp).generate()
        val dominant = palette.getDominantColor(Color.White.toArgb())

        // tёмный ли цвет? (простая формула яркости)
        val r = android.graphics.Color.red(dominant)
        val g = android.graphics.Color.green(dominant)
        val b = android.graphics.Color.blue(dominant)

        val brightness = 0.299 * r + 0.587 * g + 0.114 * b
        isDarkImage = brightness < 128.0
    }

    val textColor = if (isDarkImage) Color.White else Color.Black

    Box(Modifier.fillMaxWidth()) {

        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = imageModifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.TopStart).padding(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Назад",
                tint = textColor
            )
        }

        // Текст поверх изображения (название, дата, цена)
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = eventCard?.title ?: "",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        offset = Offset(1f, 1f),
                        blurRadius = 4f
                    )
                )
            )

            DateTimeText(eventCard?.dateStart)


            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}



@Composable
fun EventImageWithGradientText(
    eventCard: Event?,
    imageUrl: String?,
    onBack: () -> Unit,
    onButtonClick: (eventId: UUID?) -> Unit = {},
    imageModifier: Modifier = Modifier,
) {
    Box(
        modifier = imageModifier
            .fillMaxWidth()
    ) {

        // Фоновое изображение
        AsyncImage(
            model = imageUrl ?: R.drawable.empty,
            contentDescription = null,
            modifier = imageModifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Градиент поверх изображения (чтобы текст всегда читался)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.9f),
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.8f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        // Кнопка назад
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.TopStart).padding(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Назад",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(13.dp).fillMaxWidth()
        ) {
            Text(
                text = eventCard?.title ?: "",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        offset = Offset(1f, 1f),
                        blurRadius = 4f
                    )
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            DateTimeText(
                eventCard?.dateStart,
                fontSize = 21.sp,
                color = Color.White)

            DefaultButton("Посмотреть приглашения", onClick = {
                onButtonClick(eventCard?.eventId)
            }, Modifier.align(Alignment.CenterHorizontally), width = 320.dp)

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun InvitationImageWithGradientText(
    invitation: Invitation?,
    isAuthor: Boolean,
    imageUrl: String?,
    onBack: () -> Unit,
    onEvent:(Invitation?) -> Unit,
    onApplyClick: (Invitation?) -> Unit,
    modifier: Modifier = Modifier,
    isAlreadyRequest: Boolean = false
) {
    Box(modifier = modifier.fillMaxWidth()) {

        AsyncImage(
            model = imageUrl ?: R.drawable.empty,
            contentDescription = null,
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Градиент сверху вниз
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.9f),
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.8f)
                        )
                    )
                )
        )

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Назад",
                tint = Color.White
            )
        }

        Row(modifier = Modifier
            .align(Alignment.TopEnd)) {
            // Назад


            Text(
                text = "К событию",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        blurRadius = 5f
                    )
                ),
                modifier = Modifier
                    .padding(top = 20.dp)
            )

            IconButton(
                onClick = { onEvent(invitation) },
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "К событию",
                    tint = Color.White,
                    modifier = Modifier.scale(scaleX = -1f, scaleY = 1f)
                )
            }
        }



        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(13.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = invitation?.shortDescription ?: "",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        blurRadius = 5f
                    )
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row {
                Text(
                    text = "Дата события: ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W800,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                DateTimeText(
                    eventDate = invitation?.event?.dateStart,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(3.dp))

            Row {
                Text(
                    text = "Набор открыт до: ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W800,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                DateTimeText(
                    eventDate = invitation?.deadLine,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            if(!isAuthor){
                // Кнопка
                if(!isAlreadyRequest){
                    Button(
                        onClick = { onApplyClick(invitation) },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(48.dp)
                            .width(260.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE55A54)
                        )
                    ) {
                        Text("Записаться", color = Color.White, fontSize = 18.sp)
                    }
                }else{
                    Button(
                        onClick = {  },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(48.dp)
                            .width(260.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE55A54),
                            disabledContainerColor = Color("#B4A4A3".toColorInt())
                        ),
                        enabled = false
                    ) {
                        Text("Вы уже записаны", color = Color.White, fontSize = 18.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}