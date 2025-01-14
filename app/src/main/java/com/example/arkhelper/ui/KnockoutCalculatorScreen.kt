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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.arkhelper.R
import com.example.arkhelper.Routes
import kotlinx.coroutines.launch


@Composable
fun KnockoutCalculatorscreen(navigation:NavController) {
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
                    onClick = { /*TODO*/ }
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
            Text(modifier = Modifier.padding(horizontal = 17.dp), text = "Knockout Calculator", style = TextStyle(fontSize = 18.sp))
            Text(modifier = Modifier.padding(17.dp), text ="This calculator is used to determine how much ammo you need to prepare to knock out a certain creature")

            var selectedCreature by remember { mutableStateOf<Creature?>(null) }
            CreatureDropdownMenu(creatures,onCreatureSelected = { selectedCreature = it })

            var level by remember { mutableStateOf("") }
            CreatureLevelInput(level=level,onLevelChange = { level = it })
            var selectedWeapon by remember{ mutableStateOf<Weapon?>(null) }
            WeaponDropdownMenu(weapons, onWeaponSelected = { selectedWeapon=it})

            var quality by remember { mutableStateOf("") }
            WeaponQualityInput(quality = quality, onQualityChange = {quality=it})

            var knockoutValue by remember { mutableStateOf<Long>(0) }
            CalculateButton(selectedCreature,level,selectedWeapon,quality,onCalculate = { result ->
                knockoutValue = result
                println("Knockout Value: $result")
            })
            knockoutValue?.let {
                Text(text = "You will need ${it} ${selectedWeapon?.name?:"weapon"} ammo to knockout level ${level} ${selectedCreature?.name?:"creature"}", style = MaterialTheme.typography.bodyLarge)
            }



        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatureDropdownMenu(creatures: List<Creature>, onCreatureSelected: (Creature) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCreature by remember { mutableStateOf<Creature?>(null) }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        // Dropdown menu box
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            // Text field that triggers the dropdown
            OutlinedTextField(
                readOnly = true,
                value = selectedCreature?.name ?: "Select a Creature",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                label = { Text("Creature") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )
            // Dropdown menu
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                creatures.forEach { creature ->
                    DropdownMenuItem(
                        leadingIcon = {Image(painter = painterResource(id = creature.dossierImg), contentDescription = null)},
                        text = { Text(creature.name) },
                        onClick = {
                            selectedCreature = creature
                            expanded = false
                            onCreatureSelected(creature) // Notify parent of the selection
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Display selected creature details (optional)
        selectedCreature?.let { creature ->

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatureLevelInput(
    level: String,
    onLevelChange: (String) -> Unit,

) {
    OutlinedTextField(
        value = level,
        onValueChange = onLevelChange,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 17.dp),
        label = { Text("Creature Level. Min:1") },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = androidx.compose.ui.text.input.ImeAction.Done
        ),
        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
    )
    Spacer(Modifier.height(16.dp))
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeaponDropdownMenu(weapons: List<Weapon>, onWeaponSelected: (Weapon) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedWeapon by remember { mutableStateOf<Weapon?>(null) }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        // Dropdown menu box
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            // Text field that triggers the dropdown
            OutlinedTextField(
                readOnly = true,
                value = selectedWeapon?.name ?: "Select a Weapon",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                label = { Text("Weapon") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )
            // Dropdown menu
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                weapons.forEach { weapon ->
                    DropdownMenuItem(
                        leadingIcon = {Image(painter = painterResource(id = weapon.image), contentDescription = null)},
                        text = { Text(weapon.name) },
                        onClick = {
                            selectedWeapon = weapon
                            expanded = false
                            onWeaponSelected(weapon) // Notify parent of the selection
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Display selected creature details (optional)
        selectedWeapon?.let { weapon->

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeaponQualityInput(
    quality: String,
    onQualityChange: (String) -> Unit,

    ) {
    OutlinedTextField(
        value = quality,
        onValueChange = onQualityChange,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 17.dp),
        label = { Text("Weapon Quality. Min:100") },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = androidx.compose.ui.text.input.ImeAction.Done
        ),
        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
    )
    Spacer(Modifier.height(16.dp))
}


@Composable
fun CalculateButton(
    creature: Creature?,
    level: String,
    weapon:Weapon?,
    quality:String,
    onCalculate: (knockoutValue: Long) -> Unit
) {
    val isButtonEnabled = creature != null && level.isNotEmpty() && level.toIntOrNull() != null && weapon!=null && level.toInt()>0 && quality.isNotEmpty()&& quality.toIntOrNull()!=null&& quality.toInt()>99
    var result:Long=0
    Button(
        onClick = {
            if (creature != null && level.toIntOrNull() != null && weapon!=null && quality.toIntOrNull()!=null) {


                val knockoutValue=CalculateKnockoutValue(creature,level.toInt(),weapon,quality.toInt())
                onCalculate(knockoutValue)
            }

        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        enabled = isButtonEnabled
    ) {
        Text("Calculate")

    }


}



fun CalculateKnockoutValue(creature:Creature,level:Int,weapon:Weapon,quality:Int):Long{

    val result=(creature.toporPerLevel*level).toLong()/(weapon.toporPerHit*(quality.toLong()/100).toLong())

    return result


}