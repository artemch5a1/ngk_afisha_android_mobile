package com.example.domain.common.enums

enum class Role(val value: Int) {
    Publisher(0),
    User(1);

    fun toRussianName(): String = when (this) {
        Publisher -> "Организатор"
        User -> "Пользователь"
    }

    companion object {
        fun fromInt(value: Int): Role =
            Role.entries.firstOrNull { it.value == value }
                ?: throw IllegalArgumentException("Unknown Role value: $value")
    }
}