package com.example.arkhelper.ui

import androidx.annotation.DrawableRes
import com.example.arkhelper.R

data class Creature(
    val name:String,
    val shortDescription:String="",
    @DrawableRes val dossierImg:Int,
    @DrawableRes val image: Int,
    val description:Description,
    val toporPerLevel:Int
)

data class Description(val dossier:String,val behaviour:String,val taming:String)

val creatures: List<Creature> = listOf(

    Creature("Rex", shortDescription = "This is the king of dinosaurs in ark",R.drawable.rexdosier,R.drawable.rexdefault,
        Description("Dossier Text","Behaviour text","Taming text"),230
    ),
    Creature("Triceratops", shortDescription = "This is the a reliable herbivore in ark",R.drawable.trikedosier,R.drawable.triceratopsdefault,
        Description("Dossier Text","Behaviour text","Taming text"),150),
    Creature("Pteranodon", shortDescription = "This is best early flying dinosaurs in ark",R.drawable.pteranodondosier,R.drawable.rexdefault,
        Description("Dossier Text","Behaviour text","Taming text"),80)
)



data class Weapon(
    val name:String,
    @DrawableRes val image:Int,
    val toporPerHit:Int
)


val weapons: List<Weapon> = listOf(
    Weapon("Crossbow",R.drawable.crossbowimg,123),
    Weapon("Bow",R.drawable.crossbowimg,123),
    Weapon("Rifle",R.drawable.crossbowimg,123)

)