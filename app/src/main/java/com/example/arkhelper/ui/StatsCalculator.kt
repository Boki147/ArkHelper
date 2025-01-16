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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.arkhelper.CreatureViewModel
import com.example.arkhelper.R
import com.example.arkhelper.Routes
import kotlinx.coroutines.launch


@Composable
fun StatsCalculatorscreen(viewModel: CreatureViewModel, navigation:NavController) {
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
                    onClick = { navigation.navigate(route = Routes.SCREEN_ALL_CREATURES) }
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Knockout Calculator") },
                    selected = false,
                    onClick = { navigation.navigate(route = Routes.SCREEN_KNOCKOUT_CALCULATOR) }
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Stats Calculator") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Server Settings") },
                    selected = false,
                    onClick = { navigation.navigate(route = Routes.SCREEN_SERVER_SETTINGS)}
                )
            }
        },
    ) {
        val scrollState = rememberScrollState()
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Button(
                    contentPadding = PaddingValues(),
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    modifier = Modifier.padding(15.dp).size(55.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(Color.LightGray)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.menuicon),
                        contentDescription = "Menu",
                        modifier = Modifier.fillMaxSize().padding(5.dp)
                    )
                }
                Spacer(Modifier.width(75.dp))
                Image(
                    painter = painterResource(id = R.drawable.arklogo),
                    contentDescription = "Ark Icon",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(5.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Ark Helper",
                style = TextStyle(fontSize = 25.sp, color = MaterialTheme.colorScheme.onSurface)
            )
            Spacer(Modifier.height(20.dp))
            HorizontalDivider()
            Spacer(Modifier.height(20.dp))
            Text(modifier = Modifier.padding(horizontal = 17.dp), text = "Stats Calculator", style = TextStyle(fontSize = 18.sp))
            Text(modifier = Modifier.padding(horizontal = 17.dp), text ="This calculator is used to determine how many levels of a wild creature went in a certain stat")

            var healthPoints by remember { mutableStateOf<Int?>(0) }
            var staminaPoints by remember { mutableStateOf<Int?>(0) }
            var oxygenPoints by remember { mutableStateOf<Int?>(0) }
            var foodPoints by remember { mutableStateOf<Int?>(0) }
            var weightPoints by remember { mutableStateOf<Int?>(0) }
            var damagePoints by remember { mutableStateOf<Int?>(0) }
            var selectedCreature by remember { mutableStateOf<Creature?>(null) }
            CreatureDropdownMenu(viewModel.creatureData,onCreatureSelected = { selectedCreature = it })

            var Health by remember { mutableStateOf("") }
            CreatureStatInput("Health",Health, onStatChange = {Health=it},healthPoints)

            var Stamina by remember { mutableStateOf("") }
            CreatureStatInput("Stamina",Stamina, onStatChange = {Stamina=it},staminaPoints)

            var Oxygen by remember { mutableStateOf("") }
            CreatureStatInput("Oxygen",Oxygen, onStatChange = {Oxygen=it},oxygenPoints)

            var Food by remember { mutableStateOf("") }
            CreatureStatInput("Food",Food, onStatChange = {Food=it},foodPoints)

            var Weight by remember { mutableStateOf("") }
            CreatureStatInput("Weight",Weight, onStatChange = {Weight=it},weightPoints)

            var Damage by remember { mutableStateOf("") }
            CreatureStatInput("Damage",Damage, onStatChange = {Damage=it},damagePoints)


            Button(
                onClick = {
                    if (selectedCreature != null) {
                        healthPoints = Health.toIntOrNull()?.let { CalculateStat(viewModel,selectedCreature!!, "Health", it) }
                        staminaPoints = Stamina.toIntOrNull()?.let { CalculateStat(viewModel,selectedCreature!!, "Stamina", it) }
                        oxygenPoints = Oxygen.toIntOrNull()?.let { CalculateStat(viewModel,selectedCreature!!, "Oxygen", it) }
                        foodPoints = Food.toIntOrNull()?.let { CalculateStat(viewModel,selectedCreature!!, "Food", it) }
                        weightPoints = Weight.toIntOrNull()?.let { CalculateStat(viewModel,selectedCreature!!, "Weight", it) }
                        damagePoints = Damage.toIntOrNull()?.let { CalculateStat(viewModel,selectedCreature!!, "Damage", it) }
                    }
                },
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text("Calculate Stats")
            }



        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatureStatInput(
    type:String,
    stat: String,
    onStatChange: (String) -> Unit,
    calculatedPoints: Int?
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
    OutlinedTextField(
        value = stat,
        onValueChange = onStatChange,
        modifier = Modifier.padding(horizontal = 12.dp).width(200.dp),
        label = { Text(type) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = androidx.compose.ui.text.input.ImeAction.Done
        ),
        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
    )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Stat points: ${calculatedPoints.toString()?:" "}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )


}
}





fun CalculateStat(viewModel: CreatureViewModel,creature: Creature, statName: String, statValue: Int): Int {
    var result:Float=0f
    when (statName) {
        "Health" -> result=(statValue-creature.baseStats.health * viewModel.settings.first().healthSetting.toFloat())/(creature.statsperlevel.health*viewModel.settings.first().healthSetting.toFloat())
        "Stamina" -> result=(statValue-creature.baseStats.stamina * viewModel.settings.first().staminaSetting.toFloat())/(creature.statsperlevel.stamina*viewModel.settings.first().staminaSetting.toFloat())
        "Oxygen" -> result=(statValue-creature.baseStats.oxygen * viewModel.settings.first().oxygenSetting.toFloat())/(creature.statsperlevel.oxygen*viewModel.settings.first().oxygenSetting.toFloat())
        "Food" -> result=(statValue-creature.baseStats.food * viewModel.settings.first().foodSetting.toFloat())/(creature.statsperlevel.food*viewModel.settings.first().foodSetting.toFloat())
        "Weight" -> result=(statValue-creature.baseStats.weight * viewModel.settings.first().weightSetting.toFloat())/(creature.statsperlevel.weight*viewModel.settings.first().weightSetting.toFloat())
        "Damage" -> (statValue-creature.baseStats.damage * viewModel.settings.first().damageSetting.toFloat())/(creature.statsperlevel.damage*viewModel.settings.first().damageSetting.toFloat())
    }


    return  result.toInt()
}