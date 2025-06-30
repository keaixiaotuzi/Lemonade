package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme
import java.nio.file.WatchEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonApp()
            }
        }
    }
}

@Composable
fun LemonApp() {
    // Banner
    Row (
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column (modifier = Modifier.background(color = Color(red = 105, green = 205, blue = 130))){
            Text(
                text = stringResource(R.string.app_name),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(1000.dp).height(30.dp).padding(top = 5.dp)
            )
        }
    }

    var description by remember { mutableStateOf(R.string.lemon_tree_description) }
    var hint by remember { mutableStateOf(R.string.select_lemon) }
    var imageResource by remember { mutableStateOf(R.drawable.lemon_tree) }

    // Number of times user has to tap the lemon tree
    var times = (2..4).random()

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            // This button will change the image when clicked
            Button(
                onClick = {
                if (imageResource == R.drawable.lemon_tree){
                    imageResource = R.drawable.lemon_squeeze
                    description = R.string.lemon_description
                    hint = R.string.squeeze_lemon
                }
                else if (imageResource == R.drawable.lemon_squeeze && times > 0) {
                    times--
                    if (times == 0) {
                        imageResource = R.drawable.lemon_drink
                        description = R.string.lemonade_description
                        hint = R.string.drink
                        times = (2..4).random() // Reset the times for the next squeeze
                    }
                } else if (imageResource == R.drawable.lemon_drink) {
                    imageResource = R.drawable.lemon_restart
                    description = R.string.empty_glass_description
                    hint = R.string.restart
                } else if(imageResource == R.drawable.lemon_restart) {
                    imageResource = R.drawable.lemon_tree
                    description = R.string.lemon_tree_description
                    hint = R.string.select_lemon
                }
            },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(red = 105, green = 205, blue = 130))
                ) {
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = stringResource(id = description),
                    modifier = Modifier.border(
                        width = 2.dp,
                        color = Color(red = 105, green = 205, blue = 130),
                        shape = RoundedCornerShape(4.dp)
                    )
                )
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = stringResource(id = hint),
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        LemonApp()
    }
}