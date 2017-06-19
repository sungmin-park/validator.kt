package com.github.sungminpark

import com.github.sungminpark.validator.SizeLe
import org.junit.Assert.assertEquals
import org.junit.Test
import javax.validation.ConstraintViolation

class TestSizeLe {
    @Test
    fun test() {
        class Model(@SizeLe(2) val text: String?)

        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(null)))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model("")))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model("1")))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model("12")))

        val invalidModel = Model("123")
        val invalid = validate(invalidModel)
        assertEquals(3, invalidModel.text?.length ?: 0)
        assertEquals(1, invalid.size)
        assertEquals("Field cannot be longer than 2 characters", invalid.first().message)
    }
}