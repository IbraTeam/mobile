package com.ibra.keytrackerapp.key_requests.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.ui.theme.BlueColor
import com.ibra.keytrackerapp.common.ui.theme.GrayColor
import com.ibra.keytrackerapp.common.ui.theme.GreenColor
import com.ibra.keytrackerapp.common.ui.theme.RedColor
import com.ibra.keytrackerapp.key_requests.domain.model.KeyRequestDto
import com.ibra.keytrackerapp.key_requests.domain.enums.RequestStatus
import com.ibra.keytrackerapp.key_requests.domain.enums.RequestType

// Список заявок пользователя
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RequestsList(
    viewModel: RequestsViewModel = hiltViewModel()
) {
    val vmValue by viewModel.uiState.collectAsState()

    RequestsLabel()

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 78.dp),
            state = rememberLazyListState()
        ) {
            item{
                for (i in 0..6) {
                    var isEmpty = true

                    for (keyRequest in vmValue.dayRequests) {
                        if (keyRequest.pairNumber.ordinal == i){
                            Request(keyRequest)
                            isEmpty = false
                        }
                    }

                    if (isEmpty)
                        EmptyPair(time = "${viewModel.getPairStartTime(i)} - ${viewModel.getPairEndTime(i)}")
                }
            }
        }
    }

}

// Элемент списка заявок
@Composable
fun Request(
    keyRequest : KeyRequestDto,
    viewModel : RequestsViewModel = hiltViewModel()
) {
    Row(
        modifier = Modifier
            .padding(24.dp, 0.dp, 24.dp, 16.dp)
            .height(intrinsicSize = IntrinsicSize.Max)
    ) {
        Box(modifier = Modifier
            .padding(0.dp, 8.dp)
            .width(3.dp)
            .fillMaxHeight()
            .background(
                color = when (keyRequest.status) {
                    RequestStatus.Accepted, RequestStatus.Issued -> GreenColor
                    RequestStatus.Pending -> BlueColor
                    RequestStatus.Rejected -> RedColor
                }
            )
        )

        Column(
            modifier = Modifier
                .padding(24.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Time(time = viewModel.getPairStartTime(keyRequest.pairNumber.ordinal))
            TypeOfRequest(keyRequest)
            Author(keyRequest)
            RequestRoom(keyRequest)
            StatusOfRequest(keyRequest)
            Time(time = viewModel.getPairEndTime(keyRequest.pairNumber.ordinal))
        }
    }
}

// Отображение времени пары
@Composable
fun Time (time : String) {
    Row {
        Text(
            text = time,
            style = TextStyle(
                fontSize = 16.sp,
                color = GrayColor
            )
        )

        Box(
            modifier = Modifier
                .padding(14.dp, 12.dp, 0.dp, 0.dp)
                .fillMaxWidth()
                .height(1.dp)
                .alpha(0.14f)
                .background(
                    color = GrayColor
                )
        )
    }
}

// Отображение типа пары
@Composable
fun TypeOfRequest(
    keyRequest: KeyRequestDto,
    viewModel: RequestsViewModel = hiltViewModel()
) {
    Row(
        modifier = Modifier
            .padding(0.dp, 4.dp, 0.dp, 0.dp)
    ) {
        Text(
            text = viewModel.getPairName(keyRequest),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        if ((keyRequest.status != RequestStatus.Accepted && keyRequest.status != RequestStatus.Issued)|| keyRequest.typeBooking != RequestType.Pair)
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .clip(shape = CircleShape)
                    .clickable {
                        viewModel.deleteRequest(keyRequest.id)
                    },
                painter = painterResource(id = when (keyRequest.status) {
                    RequestStatus.Rejected -> R.drawable.red_cross
                    RequestStatus.Pending -> R.drawable.blue_cross
                    RequestStatus.Accepted, RequestStatus.Issued -> R.drawable.green_cross
                }),
                contentDescription = null
            )
    }
}

// Автор заявки
@Composable
fun Author(keyRequest: KeyRequestDto) {
    Row(
        modifier = Modifier
            .padding(0.dp, 4.dp, 0.dp, 0.dp)
    ){
        Image(
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.CenterVertically),
            imageVector = ImageVector.vectorResource(R.drawable.request_author),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                .align(Alignment.CenterVertically),
            text = keyRequest.user.name,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            ),
        )
    }
}

// Аудитория заявки
@Composable
fun RequestRoom(keyRequest: KeyRequestDto) {
    Row(
        modifier = Modifier
            .padding(0.dp, 8.dp, 0.dp, 0.dp)
    ){
        Image(
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.CenterVertically),
            imageVector = ImageVector.vectorResource(R.drawable.location_gray),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                .align(Alignment.CenterVertically),
            text = keyRequest.name,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            ),
        )
    }
}

// Статус заявки
@Composable
fun StatusOfRequest(
    keyRequest: KeyRequestDto,
    viewModel: RequestsViewModel = hiltViewModel()
) {
    Row(
        modifier = Modifier
            .padding(0.dp, 2.dp, 0.dp, 0.dp)
    ){
        Image(
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.CenterVertically),
            imageVector = ImageVector.vectorResource(
                when (keyRequest.status) {
                    RequestStatus.Accepted, RequestStatus.Issued -> R.drawable.accepted_status
                    RequestStatus.Pending -> R.drawable.pending_status
                    RequestStatus.Rejected -> R.drawable.rejected_status
                }),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )

        Column (
            modifier = Modifier
                .padding(0.dp, 6.dp, 0.dp, 0.dp)
                .align(Alignment.CenterVertically)
        ){
            Text(
                modifier = Modifier
                    .padding(8.dp, 0.dp, 0.dp, 8.dp),
                text = viewModel.getRequestStatusName(keyRequest),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                ),
            )
        }
    }
}

// Пара без заявок
@Composable
fun EmptyPair(time: String) {
    Row(
        modifier = Modifier
            .padding(22.dp, 0.dp, 24.dp, 16.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(0.dp, 0.dp, 21.dp, 0.dp)
                .align(Alignment.CenterVertically),
            imageVector = ImageVector.vectorResource(R.drawable.empty_pair),
            contentDescription = null
        )
        Time(time)
    }
}

// Надпись "Ваши заявки"
@Composable
fun RequestsLabel()
{
    Text(
        modifier = Modifier
            .padding(24.dp, 10.dp, 0.dp, 16.dp),
        text = stringResource(id = R.string.your_requests),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    )
}