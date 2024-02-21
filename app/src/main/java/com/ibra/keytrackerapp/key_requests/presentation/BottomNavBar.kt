package com.ibra.keytrackerapp.key_requests.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.navigation.NavHostController
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.navigation.Screen
import com.ibra.keytrackerapp.common.ui.theme.LightPink
import com.ibra.keytrackerapp.common.ui.theme.Pink

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
        RequestElement(navController)
        CreateRequestButton(navController)
        KeysElement(navController)
    }
}

// Кнопка "Заявки"
@Composable
fun RequestElement(navController: NavHostController)
{
    val isSelected = navController.currentBackStackEntry?.destination?.route == Screen.RequestsScreen.name

    Column(
        modifier = Modifier
            .padding(24.dp, 16.dp, 0.dp, 0.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable {

            }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Заявки",
            style = TextStyle(
                fontSize = 18.sp,
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
fun CreateRequestButton(navController: NavHostController)
{
    Box(modifier = Modifier
        .padding(0.dp, 16.dp, 0.dp, 0.dp)
    ){
        Button(
            modifier = Modifier
                .height(56.dp)
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .background(brush = Brush.horizontalGradient(colors = listOf(Pink, LightPink))),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            onClick = { /*TODO*/ }
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = "Создать заявку",
                style = TextStyle(
                    fontSize = 18.sp,
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
            .padding(0.dp, 16.dp, 24.dp, 0.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable {

            }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Ключи",
            style = TextStyle(
                fontSize = 18.sp,
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