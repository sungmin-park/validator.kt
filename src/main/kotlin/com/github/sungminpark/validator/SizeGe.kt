package com.github.sungminpark.validator

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention
@Constraint(validatedBy = arrayOf(SizeGeValidator::class))
annotation class SizeGe(val value: Int,
                        val message: String = "{com.github.sungminpark.validator.SizeGe}",
                        @Suppress("unused") val groups: Array<KClass<*>> = arrayOf(),
                        @Suppress("unused") val payload: Array<KClass<out Payload>> = arrayOf())

class SizeGeValidator : ConstraintValidator<SizeGe, Any> {
    private var size: Int = 0
    private lateinit var message: String

    override fun initialize(constraintAnnotation: SizeGe) {
        size = constraintAnnotation.value
        message = constraintAnnotation.message
    }

    override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }

        return sizeOf(value) >= size
    }
}