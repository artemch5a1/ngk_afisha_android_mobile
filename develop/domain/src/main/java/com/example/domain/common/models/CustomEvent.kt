package com.example.domain.common.models

class CustomEvent<T> constructor() {

    private val listeners = mutableSetOf<(T) -> Unit>()

    fun subscribe(listener: (T) -> Unit): Subscription {
        listeners.add(listener)
        return Subscription { listeners.remove(listener) }
    }

    fun invoke(value: T) {
        listeners.forEach { it(value) }
    }

    class Subscription(private val remove: () -> Unit) {
        fun unsubscribe() = remove()
    }
}

class ReadOnlyEvent<T>(private val customEvent: CustomEvent<T>) {
    fun subscribe(listener: (T) -> Unit) = customEvent.subscribe(listener)
}