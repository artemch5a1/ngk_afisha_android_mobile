package com.example.ngkafisha.presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.ngkafisha.R
import com.example.ngkafisha.domain.common.enums.MemberStatus
import com.example.ngkafisha.domain.common.enums.Role
import com.example.ngkafisha.domain.eventService.models.Event
import com.example.ngkafisha.domain.eventService.models.Invitation
import com.example.ngkafisha.domain.eventService.models.Member
import com.example.ngkafisha.domain.identityService.userContext.models.Student
import java.time.format.DateTimeFormatter

@Composable
fun InvitationCard(
    invitation: Invitation,
    onMoreClick: () -> Unit,
    onEventClick:(event: Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 50.dp)
            .padding(end = 16.dp)
    ) {

        // Фото берём из события
        val imageModel = invitation.event?.downloadUrl.takeIf { !it.isNullOrEmpty() }
            ?: R.drawable.empty

        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp

        // те же пропорции, что в EventCard
        val imageHeight = remember(screenHeight, screenWidth) {
            minOf(
                screenHeight * 0.23f,
                screenWidth * 0.6f
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))

        AsyncImage(
            model = imageModel,
            contentDescription = "Изображение события",
            modifier = Modifier
                .widthIn(max = 310.dp)
                .fillMaxWidth()
                .height(imageHeight)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.empty),
            placeholder = painterResource(R.drawable.empty)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        // Краткое описание роли
        Text(
            text = invitation.shortDescription,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.W800,
            modifier = Modifier
                .padding(bottom = 6.dp)
        )

        Spacer(modifier = Modifier.padding(5.dp))

        // Название мероприятия
        HyperlinkText(
            text = invitation.event?.title ?: "",
            onClick = {
                invitation.event?. let { event -> onEventClick(event) }
            },
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Row {
            // Дэдлайн
            Text(
                text = "Набор открыт до: ",
                fontSize = 15.sp,
                fontWeight = FontWeight.W800,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            DateTimeText(invitation.deadLine, Modifier.padding(bottom = 4.dp))
        }


        // Кнопка + переход
        Row {
            Button(
                onClick = onMoreClick,
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Подробнее",
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Требуется участников
            Text(
                text = "Требуется участников: ${invitation.requiredMember - invitation.acceptedMember}",
                fontSize = 15.sp,
                color = Color.Black,
                fontWeight = FontWeight.W800,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun EventCard(event: Event?, controlNav: NavHostController, isAuthor:Boolean, isAdmin:Boolean){
    Column(modifier = Modifier
        .padding(16.dp)
        .padding(top = 50.dp)
        .padding(end = 16.dp)) {

        val imageModel = event?.downloadUrl.takeIf { !it.isNullOrEmpty() }
            ?: R.drawable.empty // Запасное изображение

        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp

        // Вычисляем высоту (30% от высоты экрана, но не более 60% от ширины)
        val imageHeight = remember(screenHeight, screenWidth) {
            minOf(
                screenHeight * 0.23f,  // 30% от высоты экрана
                screenWidth * 0.6f     // Ограничение: не более 60% от ширины
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))
        AsyncImage(
            model = imageModel,
            contentDescription = "Концертное изображение",
            modifier = Modifier
                .widthIn(max = 310.dp)
                .fillMaxWidth()
                .height(imageHeight)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.empty), // Если ошибка загрузки
            placeholder = painterResource(R.drawable.empty) // Плейсхолдер при загрузке
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            Text(
                text = "" + event?.title,
                fontSize = 25.sp,
                color = Color.Black,
                fontWeight = FontWeight.W800,
                modifier = Modifier
                    .padding(bottom = 16.dp)

            )
        }

        Text(
            text = "" + event?.shortDescription,
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.W800,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )
        Row(){
            Button(
                onClick = {
                    controlNav.navigate("eventScreen/${event?.eventId}")
                },
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Подробнее",
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            DateTimeText(event?.dateStart, Modifier
                .align(Alignment.CenterVertically))
        }
    }
}

@Composable
fun InvitationCard(
    member: Member,
    onTitleClick: () -> Unit,
    modifier: Modifier = Modifier,
    currentRole: Role? = null,
    onAccept: (member: Member) -> Unit = { },
    onRejectRequest: (member: Member) -> Unit = { },
    onCancelRequest: (member: Member) -> Unit = { },
) {
    val event = member.invitation.event ?: return

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFE0E0E0))
            .padding(12.dp)
    ) {

        Image(
            painter = rememberAsyncImagePainter(event.downloadUrl),
            contentDescription = "Фото события",
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(12.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {

            HyperlinkText(
                text = event.title,
                onClick = onTitleClick
            )

            if(member.student == null){
                InvitationCardText("Дата: ", "${
                    event.dateStart.format(
                        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                    )
                }")

                InvitationCardText("Адрес: ", event.location?.address ?: "—")

                InvitationCardText("Статус:  ", member.status.toRussianName())
            } else{
                InvitationCardText("Фио: ", member.student?.user?.fullName() ?: "")

                InvitationCardText("Группа:  ", member.student?.group?.fullName ?: "")
            }
        }
    }

    Spacer(modifier = Modifier.height(4.dp))

    when(currentRole){
        Role.Publisher -> {
            PublisherPanel(
                {
                    onAccept(member)
                },
                {
                    onRejectRequest(member)
                },
                member.status
            )
        }

        Role.User, null -> {
            StudentPanel(
                {
                    onCancelRequest(member)
                },
                member.status
            )
        }
    }
}


private val InvitationTitleWidth = 60.dp

@Composable
fun StudentPanel(
    onCancelRequest: () -> Unit,
    status: MemberStatus
)
{
    Row {
        if(status != MemberStatus.ACCEPTED){
            Button(
                onClick = {
                    onCancelRequest()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                modifier = Modifier
            ) {

                Text(text = "Отменить заявку", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun PublisherPanel(
    onAccept: () -> Unit,
    onRejectRequest: () -> Unit,
    status: MemberStatus
)
{
    Row {
        if(status != MemberStatus.ACCEPTED){
            Button(
                onClick = {
                    onAccept()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color("#007926".toColorInt()),
                    contentColor = Color.White
                ),
                modifier = Modifier
            ) {

                Text(text = "Принять", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.width(5.dp))

        Button(
            onClick = {
                onRejectRequest()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            ),
            modifier = Modifier
        ) {

            Text(text = "Удалить участника", fontSize = 18.sp)
        }
    }
}

@Composable
fun InvitationCardText(
    title: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = title,
            modifier = Modifier.width(InvitationTitleWidth),
            style = MaterialTheme.typography.bodySmall,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 15.sp
        )
    }

    Spacer(modifier = Modifier.height(4.dp))
}