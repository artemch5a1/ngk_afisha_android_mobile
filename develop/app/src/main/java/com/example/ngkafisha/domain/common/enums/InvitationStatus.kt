package com.example.ngkafisha.domain.common.enums

enum class InvitationStatus(val value: Int) {
    DONE(0),
    ACTIVE(1);

    companion object {
        fun fromInt(value: Int): InvitationStatus =
            InvitationStatus.entries.firstOrNull { it.value == value }
                ?: throw IllegalArgumentException("Unknown InvitationStatus value: $value")
    }
}