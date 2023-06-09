package com.dev.miasnikoff.core_ui.adapter

class CellTypes<T>(vararg cells: Cell<T>) {

    private val cells: List<Cell<T>> = listOf(*cells)

    fun of(item: T): Cell<T> {
        return cells.first { cell -> cell.belongsTo(item) }
    }

    fun of(viewType: Int): Cell<T> {
        return cells.first { cell -> cell.type() == viewType }
    }
}