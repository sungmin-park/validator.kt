package com.github.sungminpark.validator

import org.springframework.web.multipart.MultipartFile
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention
@Constraint(validatedBy = arrayOf(NotEmptyFileValidator::class))
annotation class NotEmptyFile(val message: String = "{org.hibernate.validator.constraints.NotEmpty.message}",
                              @Suppress("unused") val groups: Array<KClass<*>> = arrayOf(),
                              @Suppress("unused") val payload: Array<KClass<out Payload>> = arrayOf())


class NotEmptyFileValidator : ConstraintValidator<NotEmptyFile, MultipartFile> {
    private lateinit var message: String

    override fun initialize(constraintAnnotation: NotEmptyFile) {
        message = constraintAnnotation.message
    }

    override fun isValid(value: MultipartFile?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }

        return !value.isEmpty
    }
}