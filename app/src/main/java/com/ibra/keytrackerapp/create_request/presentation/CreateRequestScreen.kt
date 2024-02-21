package com.ibra.keytrackerapp.create_request.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.navigation.Screen
import com.ibra.keytrackerapp.common.ui.theme.Pink
import com.ibra.keytrackerapp.key_requests.presentation.BottomNavBar

// Экран создания заявки
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateRequestScreen(navController: NavHostController)
{
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) {
        Column {
            CreateRequestLabel(navController)
            DateSelection()
            PairSelection()
            RepeatRequest()
            RoomSelection()
        }
    }

}

// Заголовок экрана создания заявки
@Composable
fun CreateRequestLabel(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .padding(24.dp, 50.dp, 34.dp, 0.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(0.dp, 0.dp, 24.dp, 0.dp),
            text = stringResource(id = R.string.create_request_label),
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        Column(
            modifier = Modifier
                .padding(0.dp, 6.dp, 0.dp, 0.dp)
                .align(Alignment.TopEnd)
        ) {
            Image(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(shape = CircleShape)
                    .clickable {
                        navController.navigate(Screen.RequestsScreen.name) {
                            popUpTo(Screen.RequestsScreen.name) {
                                inclusive = true
                            }
                        }
                    },
                painter = painterResource(id = R.drawable.cancel_button),
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.cancel_create_request),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Pink
                )
            )
        }
    }
}