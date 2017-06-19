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

class SizeLeValidator : ConstraintValidator<SizeLe, String> {
    private var size: Int = 0
    private lateinit var message: String

    override fun initialize(constraintAnnotation: SizeLe) {
        size = constraintAnnotation.value
        message = constraintAnnotation.message
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }

        if (value.length <= size) {
            return true
        }

        return false
    }
}