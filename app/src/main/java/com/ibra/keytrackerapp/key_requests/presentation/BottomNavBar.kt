package com.ibra.keytrackerapp.key_requests.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.navigation.Screen
import com.ibra.keytrackerapp.common.ui.theme.LightPink
import com.ibra.keytrackerapp.common.ui.theme.Pink
import com.ibra.keytrackerapp.create_request.presentation.CreateRequestViewModel

data class NavBarItem(
    val element : () -> Unit
)

@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    NavigationBar(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .height(78.dp),
        containerColor = Color.White
    ) {
        Spacer(modifier = Modifier.weight(1f))
        RequestElement(navController)
        Spacer(modifier = Modifier.weight(1f))
        CreateRequestButton(navController)
        Spacer(modifier = Modifier.weight(1f))
        KeysElement(navController)
        Spacer(modifier = Modifier.weight(1f))
    }
}

// Кнопка "Заявки"
@Composable
fun RequestElement(navController: NavHostController)
{
    val isSelected = navController.currentBackStackEntry?.destination?.route == Screen.RequestsScreen.name

    Column(
        modifier = Modifier
            .padding(24.dp, 16.dp, 0.dp, 8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .clickable {

            }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Заявки",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Pink else Color.Black
            )
        )
        
        Image(
            modifier = Modifier
                .size(39.dp, 33.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
            imageVector = ImageVector
                .vectorResource(
                    if (isSelected) R.drawable.selected_requests_element else R.drawable.unselected_requests_element
                ),
            contentDescription = null
        )
    }
}

// Кнопка "Создать заявку"
@Composable
fun CreateRequestButton(
    navController: NavHostController,
    viewModel: CreateRequestViewModel = hiltViewModel()
)
{
    val vmValues by viewModel.uiState.collectAsState()

    val isSelected = navController.currentBackStackEntry?.destination?.route == Screen.CreateRequestScreen.name
    val isEnabled = isSelected && !vmValues.isError

    Box(modifier = Modifier
        .padding(0.dp, 16.dp, 0.dp, 0.dp)
    ){
        Button(
            modifier = Modifier
                .height(56.dp)
                .padding(16.dp, 0.dp, 16.dp, 8.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .background(brush = Brush.horizontalGradient(colors = listOf(Pink, LightPink))),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            enabled = isEnabled,
            onClick = { /*TODO*/ }
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = if (isSelected) "Отправить заявку" else "Создать заявку",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            )
        }
    }

}

// Кнопка "Ключи"
@Composable
fun KeysElement(navController: NavHostController)
{
    val isSelected = navController.currentBackStackEntry?.destination?.route == Screen.KeyTracker.name

    Column(
        modifier = Modifier
            .padding(0.dp, 16.dp, 24.dp, 8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .clickable {
                // TODO
            }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Ключи",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Pink else Color.Black
            )
        )

        Image(
            modifier = Modifier
                .size(39.dp, 33.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
            imageVector = ImageVector
                .vectorResource(
                    if(isSelected) R.drawable.selected_keys_element else R.drawable.unselected_keys_element
                ),
            contentDescription = null
        )
    }
}