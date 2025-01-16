package com.example.arkhelper

import androidx.compose.runtime.mutableStateListOf

import androidx.lifecycle.ViewModel
import com.example.arkhelper.ui.Creature
import com.example.arkhelper.ui.Setting
import com.example.arkhelper.ui.Weapon
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class CreatureViewModel: ViewModel() {
    private val db = Firebase.firestore
    val creatureData = mutableStateListOf<Creature>()
    val weaponData= mutableStateListOf<Weapon>()
    var settings=mutableStateListOf<Setting>()
    init {
        fetchDatabaseData()
    }
    private fun fetchDatabaseData() {
        db.collection("Creatures")
            .get()
            .addOnSuccessListener { result ->
                for (data in result.documents) {
                    val creature = data.toObject(Creature::class.java)
                    if (creature != null) {
                        creature.id = data.id
                        creatureData.add(creature)
                    }
                }
            }
        db.collection("Weapons")
            .get()
            .addOnSuccessListener { result ->
                for (data in result.documents) {
                    val weapon = data.toObject(Weapon::class.java)
                    if (weapon != null) {
                        weapon.id = data.id
                        weaponData.add(weapon)
                    }
                }
            }
        db.collection("Settings")
            .get()
            .addOnSuccessListener { result ->
                for (data in result.documents) {
                    val setting = data.toObject(Setting::class.java)
                    if (setting != null) {
                        setting.id=data.id
                        settings.add(setting)

                    }

                }
            }

    }

    fun updateSettings(setting: Setting) {
        db.collection("Settings")
            .document(setting.id)
            .set(setting)
    }




}