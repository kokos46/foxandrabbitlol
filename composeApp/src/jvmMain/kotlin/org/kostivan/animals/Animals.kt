package org.kostivan.animals

enum class Direction(val code: Int, val dx: Int, val dy: Int) {
    NORTH(0, 0, -1), // Вверх
    EAST(1, 1, 0),    // Вправо
    SOUTH(2, 0, 1),    // Вниз
    WEST(3, -1, 0);   // Влево

    fun next(): Direction {
        return entries[(this.code + 1) % entries.size]
    }
}

fun getNextBirthOrder(): Long = 0L

abstract class Animals(
    initialDirection: Direction,
    val stability: Int, // Стабильность
    var birthOrder: Long // Правило старшинства
) : objectOnGrid() {

    abstract val maxAge: Int
    abstract val stepSize: Int

    var age: Int = 0
    var direction: Direction = initialDirection
    var movesSinceDirChange: Int = 0

    fun getOlder() { age++ }
    fun isTooOld(): Boolean = age >= maxAge

    fun move(N: Int, M: Int) {
        // Движение на stepSize
        repeat(stepSize) {
            x = (x + direction.dx + N) % N
            y = (y + direction.dy + M) % M
        }

        // Смена направления (только если stability > 0)
        movesSinceDirChange++
        if (stability > 0 && movesSinceDirChange >= stability) {
            direction = direction.next()
            movesSinceDirChange = 0
        }
    }

    abstract fun performEat()
    abstract fun makeChild(): Animals?

    fun setBirthOrder(order: Long) {
        this.birthOrder = order
    }
}

class Rabbit(
    initialDirection: Direction,
    stability: Int,
    birthOrder: Long,
) : GrassEater(initialDirection, stability, birthOrder) {
    // Специфические правила Зайца
    override val maxAge: Int = 10
    override val stepSize: Int = 1

    override fun performEat() {} // Заяц ест только для размножения в этой модели

    override fun makeChild(): Rabbit? {
        return if (age == 5 || age == 10) {
            Rabbit(this.direction, this.stability, getNextBirthOrder())
        } else {
            null
        }
    }
}

// Конкретный класс Лиса (наследуется от Predator)
class Fox(
    initialDirection: Direction,
    stability: Int,
    birthOrder: Long,
    var foodEaten: Int = 0
) : Predator(initialDirection, stability, birthOrder) {
    // Специфические правила Лисы
    override val maxAge: Int = 15
    override val stepSize: Int = 2

    override fun performEat() {} // Лиса ест на этапе "Питание" в Model

    override fun makeChild(): Fox? {
        return if (foodEaten >= 2) {
            foodEaten = 0
            Fox(this.direction, this.stability, getNextBirthOrder())
        } else {
            null
        }
    }
}


abstract class GrassEater(
    initialDirection: Direction,
    stability: Int,
    birthOrder: Long
) : Animals(initialDirection, stability, birthOrder) {

}

// Общий класс для всех Хищников
abstract class Predator(
    initialDirection: Direction,
    stability: Int,
    birthOrder: Long
) : Animals(initialDirection, stability, birthOrder) {

}

abstract class Grass: objectOnGrid() {
    abstract fun MakeChild()
}

abstract class objectOnGrid {
    var x: Int = 0
    var y: Int = 0

    // Мы используем поля напрямую в Model, но оставим геттеры/сеттеры для инкапсуляции
    fun getX(): Int = this.x
    fun getY(): Int = this.y
    fun setCoords(newX: Int, newY: Int) {
        this.x = newX
        this.y = newY
    }
}

abstract class Other: objectOnGrid(){

}