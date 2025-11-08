package org.kostivan.grid

import org.kostivan.animals.*


class Cell {

    private val occupants = ArrayList<Animals>()
    private val green = ArrayList<Grass>()
    private val other = ArrayList<Other>()

    fun addAnimal(animal: Animals) = occupants.add(animal)
    fun addGrass(item: Grass) = green.add(item)
    fun addOther(item: Other) = other.add(item)


    /**
     * Очищает ячейку от всех объектов
     */
    fun clear() {
        occupants.clear()
        green.clear()
        other.clear()
    }

    fun getAllAnimals(): List<Animals> = occupants.toList()
    fun getAllGrass(): List<Grass> = green.toList()

    /**
     * Возвращает всех Хищников в этой ячейке
     */
    fun getPredators(): List<Predator> = occupants.filterIsInstance<Predator>()

    /**
     * Возвращает всех Травоядных в этой ячейке
     */
    fun getGrassEaters(): List<GrassEater> = occupants.filterIsInstance<GrassEater>()

    /**
     * Возвращает конкретно Зайцев
     */
    fun getRabbits(): List<Rabbit> = occupants.filterIsInstance<Rabbit>()

    /**
     * Возвращает конкретно Лис
     */
    fun getFoxes(): List<Fox> = occupants.filterIsInstance<Fox>()

}