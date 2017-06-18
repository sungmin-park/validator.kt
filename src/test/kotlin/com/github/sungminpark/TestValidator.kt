package com.github.sungminpark

import com.github.sungminpark.validator.OneOf
import com.github.sungminpark.validator.OneOfList
import org.junit.Assert.*
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


    @Test
    fun testOneOfCollection() {
        @OneOf(value = "choice", items = "choices")
        class Form(val choice: Int, val choices: List<Int>)


        val validForm = Form(1, listOf(0, 1, 2))
        assertTrue(validForm.choices.contains(validForm.choice))
        assertEquals(setOf<ConstraintViolation<Form>>(), validate(validForm))

        val invalidForm = Form(-1, listOf(0, 1, 2))
        assertFalse(invalidForm.choices.contains(invalidForm.choice))
        val invalid = validate(invalidForm)
        assertEquals(1, invalid.size)
        assertEquals("choice", invalid.first().propertyPath.joinToString(separator = "."))
        assertEquals("Not a valid choice", invalid.first().message)
    }

    @Test
    fun testOneOfMap() {
        @OneOf(value = "choice", items = "choices")
        class Form(val choice: Int, val choices: Map<Int, String>)

        val validForm = Form(1, mapOf(0 to "zero", 1 to "one", 2 to "two"))
        assertTrue(validForm.choices.contains(validForm.choice))
        assertEquals(setOf<ConstraintViolation<Form>>(), validate(validForm))

        val invalidForm = Form(-1, mapOf(0 to "zero", 1 to "one", 2 to "two"))
        assertFalse(invalidForm.choices.contains(invalidForm.choice))
        val invalid = validate(invalidForm)
        assertEquals(1, invalid.size)
        assertEquals("choice", invalid.first().propertyPath.joinToString(separator = "."))
        assertEquals("Not a valid choice", invalid.first().message)
    }

    @Test
    fun testOneOfArray() {
        @OneOf(value = "choice", items = "choices")
        class Form(val choice: Int, val choices: Array<Int>)

        val validForm = Form(1, arrayOf(0, 1, 2))
        assertTrue(validForm.choices.contains(validForm.choice))
        assertEquals(setOf<ConstraintViolation<Form>>(), validate(validForm))

        val invalidForm = Form(-1, arrayOf(0, 1, 2))
        assertFalse(invalidForm.choices.contains(invalidForm.choice))
        val invalid = validate(invalidForm)
        assertEquals(1, invalid.size)
        assertEquals("choice", invalid.first().propertyPath.joinToString(separator = "."))
        assertEquals("Not a valid choice", invalid.first().message)
    }

    @Test
    fun testOneOfMultipleValues() {
        @OneOf(value = "choice", items = "choices")
        class Form(val choice: List<Int>, val choices: List<Int>)

        val validForm = Form(listOf(1, 2), listOf(0, 1, 2))
        assertTrue(validForm.choices.containsAll(validForm.choice))
        assertEquals(setOf<ConstraintViolation<Form>>(), validate(validForm))

        val invalidForm = Form(listOf(-1, 1), listOf(0, 1, 2))
        assertFalse(invalidForm.choices.containsAll(invalidForm.choice))
        val invalid = validate(invalidForm)
        assertEquals(1, invalid.size)
        assertEquals("choice", invalid.first().propertyPath.joinToString(separator = "."))
        assertEquals("Not a valid choice", invalid.first().message)
    }

    @Test
    fun testOneOfMultipleAnnotations() {
        @OneOfList(arrayOf(OneOf(value = "number", items = "numbers"), OneOf(value = "char", items = "chars")))
        class Form(val number: Int, val numbers: List<Int>, val char: Char, val chars: Array<Char>)

        val validForm = Form(1, listOf(0, 1, 2), 'a', arrayOf('a', 'b', 'c'))
        assertTrue(validForm.numbers.contains(validForm.number))
        assertTrue(validForm.chars.contains(validForm.char))
        assertEquals(setOf<ConstraintViolation<Form>>(), validate(validForm))

        val invalidForm = Form(-1, listOf(0, 1, 2), ' ', arrayOf('a', 'b', 'c'))
        assertFalse(invalidForm.numbers.contains(invalidForm.number))
        assertFalse(invalidForm.chars.contains(invalidForm.char))
        val invalid = validate(invalidForm)
        assertEquals(2, invalid.size)
        assertEquals(setOf("number", "char"), invalid.map { it.propertyPath.joinToString(separator = ".") }.toSet())
        assertEquals(setOf("Not a valid choice"), invalid.map { it.message }.toSet())
    }
}