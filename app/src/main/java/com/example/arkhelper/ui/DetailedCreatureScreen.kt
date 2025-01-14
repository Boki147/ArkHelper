package com.example.arkhelper.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.arkhelper.R
import com.example.arkhelper.Routes
import kotlinx.coroutines.launch
import kotlin.math.ceil


@Composable
fun DetailedCreatureCard(navigation:NavController,creatureId:Int) {
    val creature = creatures[creatureId]
    Column() {
         Row(horizontalArrangement = Arrangement.SpaceBetween) {
             Button( contentPadding = PaddingValues(),
                 onClick={navigation.navigate(Routes.SCREEN_ALL_CREATURES)},
                 modifier = Modifier.padding(15.dp).size(55.dp),
                 shape = RoundedCornerShape(20.dp),
                 colors = ButtonDefaults.buttonColors(Color.LightGray)
             ){
                 Icon(painter = painterResource(id= R.drawable.icontoreturn), contentDescription = "Menu", modifier = Modifier.fillMaxSize().padding(5.dp) )
             }
             Spacer(Modifier.width(75.dp))
             Image(
                 painter = painterResource(id = R.drawable.arklogo),
                 contentDescription = "Ark Icon",
                 modifier = Modifier
                     .size(100.dp)
                     .padding(5.dp),
                 contentScale = ContentScale.Fit)
         }
         Text(
             modifier = Modifier.align(Alignment.CenterHorizontally),
             text = "Ark Helper",
             style = TextStyle(fontSize = 25.sp, color = MaterialTheme.colorScheme.onSurface)
         )
        Text(text = creature.name, modifier = Modifier.align(Alignment.Start),style = TextStyle(fontSize = 50.sp, color = MaterialTheme.colorScheme.onSurface))
         Image(painter = painterResource(creature.image), contentDescription =creature.name, modifier = Modifier.fillMaxWidth().padding(10.dp))
         Text(text="Dossier",style = TextStyle(fontSize = 30.sp))
         Text(text=creature.description.dossier)
         Spacer(Modifier.height(20.dp))
         HorizontalDivider()
         Spacer(Modifier.height(20.dp))
         Text(text="Behaviour",style = TextStyle(fontSize = 30.sp))
         Text(text=creature.description.behaviour)
         Spacer(Modifier.height(20.dp))
         HorizontalDivider()
         Spacer(Modifier.height(20.dp))
         Text(text="Taming",style = TextStyle(fontSize = 30.sp))
         Text(text=creature.description.taming)
    }

}