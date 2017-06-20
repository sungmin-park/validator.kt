package com.github.sungminpark.validator

internal fun sizeOf(obj: Any): Int {
    return when (obj) {
        is String -> obj.length
        is List<*> -> obj.size
        is Map<*, *> -> obj.size
        is Array<*> -> obj.size
        else -> throw IllegalArgumentException("Cannot handle type of $obj")
    }
}