package com.example.pokedoxapplication.utils

object FilterDataProvider {
    /**
     * This method is responsible for providing filter data for type
     */
    fun getTypeFilterData(): MutableList<String> {
        val typeList = mutableListOf<String>()
        typeList.add("Normal")
        typeList.add("Fighting")
        typeList.add("Flying")
        typeList.add("Poison")
        typeList.add("Ground")
        typeList.add("Rock")
        typeList.add("Bug")
        typeList.add("Ghost")
        typeList.add("Steel")
        typeList.add("Fire")
        typeList.add("Water")
        typeList.add("Grass")
        typeList.add("Electric")
        typeList.add("Psychic")
        typeList.add("Ice")
        typeList.add("Dragon")
        typeList.add("Dark")
        typeList.add("Fairy")
        typeList.add("Unknown")
        typeList.add("Shadow")
        return typeList
    }

    /**
     * This method is responsible for providing filter data for gender
     */
    fun getGenderFilterData(): MutableList<String> {
        val typeList = mutableListOf<String>()
        typeList.add("Male")
        typeList.add("Female")
        typeList.add("Genderless")
        typeList.add("Other")
        return typeList
    }
}