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
import androidx.compose.material3.DrawerValue
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
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun SettingsScreen(
    viewModel: CreatureViewModel,
    navigation: NavController,
    onSaveSettings: (Setting) -> Unit // Callback to save updated settings

) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(200.dp)) {
                Image(
                    painter = painterResource(R.drawable.arklogo),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
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
                    onClick = { navigation.navigate(route = Routes.SCREEN_STATS_CALCULATOR) }
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {


                var torporSetting by remember { mutableStateOf(viewModel.settings.first().torporSetting) }
                var healthSetting by remember { mutableStateOf(viewModel.settings.first().healthSetting) }
                var staminaSetting by remember { mutableStateOf(viewModel.settings.first().staminaSetting) }
                var oxygenSetting by remember { mutableStateOf(viewModel.settings.first().oxygenSetting) }
                var foodSetting by remember { mutableStateOf(viewModel.settings.first().foodSetting) }
                var weightSetting by remember { mutableStateOf(viewModel.settings.first().weightSetting) }
                var damageSetting by remember { mutableStateOf(viewModel.settings.first().damageSetting) }


                Text("Settings", style = MaterialTheme.typography.headlineSmall)

                // Text fields for each setting
                SettingInputField("Torpor Setting", torporSetting) { torporSetting = it }
                SettingInputField("Health Setting", healthSetting) { healthSetting = it }
                SettingInputField("Stamina Setting", staminaSetting) { staminaSetting = it }
                SettingInputField("Oxygen Setting", oxygenSetting) { oxygenSetting = it }
                SettingInputField("Food Setting", foodSetting) { foodSetting = it }
                SettingInputField("Weight Setting", weightSetting) { weightSetting = it }
                Row(horizontalArrangement =Arrangement.SpaceBetween ) {
                SettingInputField("Damage Setting", damageSetting) { damageSetting = it }
                    Spacer(Modifier.width(30.dp))
                // Save button
                Button(modifier = Modifier.padding(top=20.dp),
                    onClick = {
                        // Update the currentSettings object or save to the database
                        val updatedSettings = Setting(
                            torporSetting = torporSetting,
                            healthSetting = healthSetting,
                            staminaSetting = staminaSetting,
                            oxygenSetting = oxygenSetting,
                            foodSetting = foodSetting,
                            weightSetting = weightSetting,
                            damageSetting = damageSetting,
                            id=viewModel.settings.first().id

                        )
                        viewModel.settings.first().torporSetting = torporSetting
                        viewModel.settings.first().healthSetting = healthSetting
                        viewModel.settings.first().staminaSetting = staminaSetting
                        viewModel.settings.first().oxygenSetting = oxygenSetting
                        viewModel.settings.first().foodSetting = foodSetting
                        viewModel.settings.first().weightSetting = weightSetting
                        viewModel.settings.first().damageSetting = damageSetting
                        onSaveSettings(updatedSettings)
                    },

                ) {
                    Text("Save")
                }
            }
            }
        }
    }
}
@Composable
fun SettingInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier.width(200.dp)
        )
    }
}
