package com.example.domain.common.enums

enum class MemberStatus(val value: Int) {
    ACCEPTED(0),
    REVIEW(1);

    fun toRussianName(): String = when (this) {
        ACCEPTED -> "Принята"
        REVIEW -> "На рассмотрении"
    }

    companion object {
        fun fromInt(value: Int): MemberStatus =
            MemberStatus.entries.firstOrNull { it.value == value }
                ?: throw IllegalArgumentException("Unknown MemberStatus value: $value")
    }
}