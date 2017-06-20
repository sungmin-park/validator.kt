package com.github.sungminpark.validator

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention
@Constraint(validatedBy = arrayOf(SizeLeValidator::class))
annotation class SizeLe(val value: Int,
                        val message: String = "{com.github.sungminpark.validator.SizeLe}",
                        @Suppress("unused") val groups: Array<KClass<*>> = arrayOf(),
                        @Suppress("unused") val payload: Array<KClass<out Payload>> = arrayOf())

class SizeLeValidator : ConstraintValidator<SizeLe, Any> {
    private var size: Int = 0
    private lateinit var message: String

    override fun initialize(constraintAnnotation: SizeLe) {
        size = constraintAnnotation.value
        message = constraintAnnotation.message
    }

    override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }

        return sizeOf(value) <= size
    }

    private fun sizeOf(obj: Any): Int {
        return when (obj) {
            is String -> obj.length
            is List<*> -> obj.size
            is Map<*, *> -> obj.size
            is Array<*> -> obj.size
            else -> throw IllegalArgumentException("Cannot handle type of $obj")
        }
    }
}