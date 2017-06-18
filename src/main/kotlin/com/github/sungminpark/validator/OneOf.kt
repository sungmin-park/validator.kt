package com.github.sungminpark.validator

import org.apache.commons.beanutils.PropertyUtils
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass


@Suppress("DEPRECATED_JAVA_ANNOTATION")
@Target(AnnotationTarget.CLASS)
@Retention
@Constraint(validatedBy = arrayOf(OneOfValidator::class))
@Repeatable
@java.lang.annotation.Repeatable(OneOfList::class)
annotation class OneOf(val value: String,
                       val items: String,
                       val message: String = "{com.github.sungminpark.validator.OneOf}",
                       @Suppress("unused") val groups: Array<KClass<*>> = arrayOf(),
                       @Suppress("unused") val payload: Array<KClass<out Payload>> = arrayOf())

@Target(AnnotationTarget.CLASS)
@Retention
annotation class OneOfList(val value: Array<OneOf>)


class OneOfValidator : ConstraintValidator<OneOf, Any> {
    private lateinit var value: String
    private lateinit var items: String
    private lateinit var message: String

    override fun initialize(constraintAnnotation: OneOf) {
        this.value = constraintAnnotation.value
        this.items = constraintAnnotation.items
        this.message = constraintAnnotation.message
    }

    override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }

        val target = PropertyUtils.getSimpleProperty(value, this.value) ?: return true
        val items = PropertyUtils.getSimpleProperty(value, items) ?: return true
        val targets = when (target) {
            is Collection<*> -> target.filterNotNull()
            else -> listOf(target)
        }
        val isValid = targets.firstOrNull { !validate(it, items) } == null
        if (!isValid) {
            context.disableDefaultConstraintViolation()
            context.defaultConstraintMessageTemplate
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(this.value)
                    .addConstraintViolation()
        }
        return isValid
    }

    private fun validate(value: Any, items: Any): Boolean {
        return when (items) {
            is Collection<*> -> {
                items.firstOrNull { it == value } != null
            }

            is Map<*, *> -> {
                validate(value, items.keys)
            }

            is Array<*> -> {
                validate(value, items.toList())
            }

            else -> throw IllegalArgumentException("Items should be collection or map.")
        }
    }
}
