package com.ibra.keytrackerapp.sign_in_sign_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.ui.theme.LightPink
import com.ibra.keytrackerapp.common.ui.theme.LightPurple
import com.ibra.keytrackerapp.common.ui.theme.Pink
import com.ibra.keytrackerapp.common.ui.theme.Purple

// Экран вабора регистрации или авторизации
@Composable
fun SignInSignUpScreen()
{
    // Картинка сверху экрана
    Column{
        IntroductionImage()
        AppDescription()
        Spacer(modifier = Modifier.weight(1f))
        SignInSignUpButtons()
    }
}

// Приветствующая картинка
@Composable
fun IntroductionImage()
{
    Box{
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.corner_circles),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
        )

        Image(
            painter = painterResource(id = R.drawable.keytrack_introduce),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(56.dp, 78.dp, 56.dp, 0.dp),
            contentScale = ContentScale.Crop
        )
    }
}

// Описание приложения
@Composable
fun AppDescription()
{
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(34.dp, 46.dp, 34.dp, 0.dp),
        text = "Погрузись в мир отслеживания ключей",
        style = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    )

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(34.dp, 16.dp, 34.dp, 0.dp),
        text = "Выберите свой способ\n авторизации для поиска ключа\n от аудитории!",
        style = TextStyle(
            fontSize = 20.sp,
           // fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            lineHeight = 26.sp
        )
    )
}

// Кнопки выбора регистрации или авторизации
@Composable
fun SignInSignUpButtons()
{
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 0.dp, 24.dp, 0.dp)
            .height(56.dp)
            .background(
                brush = Brush.horizontalGradient(colors = listOf(Purple, LightPurple)),
                shape = RoundedCornerShape(30.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        onClick = { /*TODO*/ }
    ) {
        Text(
            text = "Регистрация",
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 22.sp
            )
        )
    }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 16.dp, 24.dp, 88.dp)
            .height(56.dp)
            .background(
                brush = Brush.horizontalGradient(colors = listOf(Pink, LightPink)),
                shape = RoundedCornerShape(30.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        onClick = {/*TODO*/}
    ){
        Text(
            text = "Войти",
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 22.sp
            )
        )
    }
}