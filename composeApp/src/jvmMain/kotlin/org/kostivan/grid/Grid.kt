package org.kostivan.grid

class Grid {

    lateinit var grid : Array<Array<Cell>>
    val width: Int = 0
    val height: Int = 0

    fun getGrid(): Array<Array<Cell>>{ return grid; }
    fun setGrid(grid: Array<Array<Cell>>){ this.grid = grid }
}