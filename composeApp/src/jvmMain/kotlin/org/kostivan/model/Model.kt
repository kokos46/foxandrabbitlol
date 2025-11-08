package org.kostivan.model

import org.kostivan.animals.*
import org.kostivan.grid.Cell
import org.kostivan.grid.Grid

class Model {
    private var grid = Grid()
    private val animals = mutableListOf<Animals>() // Список всех живых существ
    private var nextBirthOrder = 0L // Уникальный счетчик для правила старшинства
    var N: Int = 0 // Ширина поля
    var M: Int = 0 // Высота поля

    /**
     * Инициализация модели начальными данными
     */
    fun start(startGrid: Grid, initialAnimals: List<Animals>) {
        grid = startGrid
        M = grid.getGrid().size
        N = grid.getGrid()[0].size

        initialAnimals.forEach { animal ->
            animal.setBirthOrder(nextBirthOrder++)
            animals.add(animal)
            grid.getGrid()[animal.y][animal.x].addAnimal(animal)
        }
    }

    /**
     * Выполняет один полный ход симуляции
     */
    fun run(): Grid {
        performMove()
        performEating()
        performAge()
        performMultiply()
        performDie()
        updateGrid()
        return grid
    }

    /**
     * Движение
     */
    private fun performMove() {
        animals.forEach { it.move(N, M) }
    }

    /**
     * Питание
     */
    private fun performEating() {
        val deadRabbits = ArrayList<Rabbit>()

        // Группируем животных по новым координатам
        val animalsByCell = animals.groupBy { it.x to it.y }

        animalsByCell.forEach { (_, animalsInCell) ->
            val rabbitsInCell = animalsInCell.filterIsInstance<Rabbit>()
            val foxesInCell = animalsInCell.filterIsInstance<Fox>()

            if (rabbitsInCell.isNotEmpty() && foxesInCell.isNotEmpty()) {

                val eater = foxesInCell.minByOrNull { it.birthOrder }

                if (eater != null) {
                    eater.foodEaten += rabbitsInCell.size
                    deadRabbits.addAll(rabbitsInCell)
                }
            }
        }

        animals.removeAll(deadRabbits)
    }

    /**
     * Старение
     */
    private fun performAge() {
        animals.forEach { it.getOlder() }
    }

    /**
     * Размножение
     */
    private fun performMultiply() {
        val newborn = ArrayList<Animals>()

        // Размножение по правилам Лисы/Зайца (полиморфизм)
        animals.forEach { animal ->
            animal.makeChild()?.let { child ->
                // Назначаем порядок и добавляем в список
                child.setBirthOrder(nextBirthOrder++)
                newborn.add(child)
            }
        }

        animals.addAll(newborn)
    }

    /**
     * Вымирание
     */
    private fun performDie() {
        animals.removeAll { it.isTooOld() }
    }

    /**
     * Обновляет Grid, синхронизируя его с текущим списком animals.
     */
    private fun updateGrid() {
        // 1. Очищаем все ячейки
        for (i in 0 until M) {
            for (j in 0 until N) {
                grid.getGrid()[i][j].clear()
            }
        }

        // 2. Добавляем зверей в их новые ячейки
        animals.forEach { animal ->
            // Используем y для строки, x для столбца
            grid.getGrid()[animal.y][animal.x].addAnimal(animal)
        }
    }
}