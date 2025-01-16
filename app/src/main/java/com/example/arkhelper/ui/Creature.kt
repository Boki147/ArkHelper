package com.example.arkhelper.ui

import androidx.annotation.DrawableRes
import com.example.arkhelper.R

data class Creature(
    val name:String="",
    val shortDescription:String="",
    val dossierImg:String="",
    val image: String="",
    val description:Description=Description(),
    val toporPerLevel:Float=0f,
    val statsperlevel:StatsPerLevel=StatsPerLevel(),
    val basetopor:Int=600,
    val baseStats: BaseStats=BaseStats(),
    var id: String = ""
)
data class StatsPerLevel(
    val health:Float=0f,
    val stamina:Float=0f,
    val oxygen:Float=0f,
    val food:Float=0f,
    val weight:Float=0f,
    val damage:Float=0f)

data class Description(
    val dossier:String="hmm",
    val behaviour:String="hmm2",
    val taming:String="hmmm3")

data class Weapon(
    val name:String="",
    val image:String="",
    val toporPerHit:Int=0,
    var id:String=""
)
data class BaseStats(
    val health:Float=0f,
    val stamina:Float=0f,
    val oxygen:Float=0f,
    val food:Float=0f,
    val weight:Float=0f,
    val damage:Float=0f
)

data class Setting(
    var torporSetting: String="",
    var healthSetting: String="",
    var staminaSetting: String="",
    var oxygenSetting: String="",
    var foodSetting: String="",
    var weightSetting: String="",
    var damageSetting: String="",
    var id:String=""
)

