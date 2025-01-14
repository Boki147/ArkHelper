package com.example.arkhelper.ui

import com.example.arkhelper.R



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.arkhelper.Routes
import com.example.arkhelper.ui.Creature
import com.example.arkhelper.ui.creatures
import com.example.arkhelper.ui.theme.ArkHelperTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArkWikiScreen(navigation: NavController) {


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(200.dp)) {
                Image(painter = painterResource(R.drawable.arklogo),contentDescription = null, modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(Modifier.height(10.dp))
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Wiki") },
                    selected = false,
                    onClick = { }
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Knockout Calculator") },
                    selected = false,
                    onClick = { navigation.navigate(route = Routes.SCREEN_KNOCKOUT_CALCULATOR) }
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Server Settings") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        },
    ) {

        Column {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Button( contentPadding = PaddingValues(),
                    onClick={ scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    } },
                    modifier = Modifier.padding(15.dp).size(55.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(Color.LightGray)
                ){
                    Icon(painter = painterResource(id= R.drawable.menuicon), contentDescription = "Menu", modifier = Modifier.fillMaxSize().padding(5.dp) )
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
            var inputState by remember { mutableStateOf("") } //searchbar na koji način radi
            var filteredCreatures: List<Creature> = mutableListOf()
            for (onecreature in creatures) {
                if (remember(inputState) {
                        onecreature.name.contains(
                            inputState,
                            ignoreCase = true
                        )
                    })
                    filteredCreatures += onecreature
            }

            TextField(
                value = inputState, onValueChange = { inputState = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)

            )

            CreatureList(filteredCreatures,navigation)

        }
    }
}

@Composable
fun CreatureList(creatures: List<Creature>,navigation: NavController) {
    if (creatures.isEmpty()) {
        Text("No creatures found", style = TextStyle(fontSize = 16.sp, color = Color.Gray))
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)){
            items(creatures.size){
                CreatureCard(creatureName = creatures[it].name,
                    dossier = creatures[it].dossierImg,
                    shortDescription = creatures[it].shortDescription
                ){
                    navigation.navigate(
                        Routes.getCreatureDetailsPath(it)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}




@Composable
fun CreatureCard(creatureName:String, @DrawableRes dossier :Int,
                 shortDescription:String,onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(15.dp),

        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable{onClick()}


    ) {
        Row(verticalAlignment = Alignment.CenterVertically  ,
            modifier = Modifier.padding(10.dp)  ) {
            Image(
                painter = painterResource(dossier),
                contentDescription = "Ark Icon",
                modifier = Modifier
                    .size(125.dp)
                    .width(140.dp)
                    .padding(5.dp)
                ,
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(verticalArrangement = Arrangement.Top) {
                Text(
                    text = creatureName,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text =shortDescription,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

        }
    }
}