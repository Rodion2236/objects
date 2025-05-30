package ru.netology.notes

class GenericRepository<T : Identification> {
    private val items = mutableListOf<T>()
    private var nextId = 1

    fun add(item: T): T {
        item.id = nextId++
        items.add(item)
        return item
    }

    fun getById(id: Int): T? {
        return items.find { it.id == id && !it.deleted }
    }

    fun getAll(): List<T> {
        return items.filter { !it.deleted }
    }

    fun update(item: T): Boolean {
        for ((index, value) in items.withIndex()) {
            if (value.id == item.id && !value.deleted) {
                items[index] = item
                return true
            }
        }
        return false
    }

    fun delete(id: Int): Boolean {
        val item = getById(id) ?: return false
        item.deleted = true
        return true
    }

    fun restore(id: Int): Boolean {
        val item = items.find { it.id == id && it.deleted }
        if (item != null) {
            item.deleted = false
            return true
        }
        return false
    }

    fun clear() {
        items.clear()
        nextId = 1
    }
}