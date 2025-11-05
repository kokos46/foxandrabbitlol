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

abstract class Animals: objectOnGrid(
    var x: Int,
    var y: Int,
    val stability: Int, // S - через сколько ходов меняет направление
    initialDirection: Direction,
    val birthTurn: Int, // Ход, на котором животное родилось
    val initialId: Int // Уникальный ID для реализации правила старшинства (чем меньше, тем старше)
) {
    abstract val maxAge: Int;
    abstract val stepSize: Int;
    var age: Int = 0;

    var direction: Direction = initialDirection;

    fun getOlder(){
        age++;
    }

    fun move(N: Int, M: Int){
        val moveSize = stepSize;
        x = (x + moveSize * direction.dx + N) % N;
        y = (y + moveSize * direction.dy + M) % M;
    }

    fun isTooOld(): Boolean = age >= maxAge;

    abstract fun Eat()

    abstract fun makeChild()
}

class Rabbit(
    x: Int, y: Int, stability: Int, initialDirection: Direction, birthTurn: Int, initialId: Int
) : GrassEater(x, y, stability, initialDirection, birthTurn, initialId) {

    override val maxAge: Int = 10;
    override val stepSize: Int = 1;

    fun shouldReproduce(): Boolean = age == 5 || age == 10
}

// 4. Класс Лиса
class Fox(
    x: Int, y: Int, stability: Int, initialDirection: Direction, birthTurn: Int, initialId: Int
) : Predator(x, y, stability, initialDirection, birthTurn, initialId) {

    var foodEaten: Int = 0 // Количество съеденных зайцев

    override val maxAge: Int = 15 // Умирает после достижения возраста 15
    override val stepSize: Int = 2 // Движется на 2 клетки

    fun shouldReproduce(): Boolean = foodEaten >= 2
    fun resetFood() { foodEaten = 0 }
}


abstract class Predator : Animals {

    fun eat (meat : ArrayList<GrassEater>){
        return ArrayList<GrassEater>
    }

}

abstract class GrassEater : Animals{

    fun eat(grass: ArrayList<Green>){

        return ArrayList<Green>
    }
}

abstract class Grass: objectOnGrid {

    abstract fun MakeChild()
}

abstract class objectOnGrid {
    var x
    var y
}

abstract class Other: objectOnGrid{

}