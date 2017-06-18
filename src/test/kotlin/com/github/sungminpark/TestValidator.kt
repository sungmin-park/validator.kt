package com.github.sungminpark

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.constraints.NotNull

class TestValidator {
    @Test
    fun testValidate() {
        class Empty

        assertEquals(setOf<ConstraintViolation<Empty>>(), validate(Empty()))

        class Invalid {
            @NotNull
            val notNull: String? = null
        }

        val invalid = Invalid()
        val validate = validate(invalid)
        assertNull(invalid.notNull)
        assertEquals(1, validate.size)
        assertEquals("may not be null", validate.first().message)
    }

    fun <T> validate(obj: T): Set<ConstraintViolation<T>> {
        return Validation.buildDefaultValidatorFactory().validator.validate(obj)
    }
}