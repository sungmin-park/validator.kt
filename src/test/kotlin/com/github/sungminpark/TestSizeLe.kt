package com.github.sungminpark

import com.github.sungminpark.validator.SizeLe
import org.junit.Assert.assertEquals
import org.junit.Test
import javax.validation.ConstraintViolation

class TestSizeLe {
    @Test
    fun testString() {
        class Model(@SizeLe(2) val text: String?)

        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(null)))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model("")))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model("1")))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model("12")))

        val invalidModel = Model("123")
        val invalid = validate(invalidModel)
        assertEquals(3, invalidModel.text?.length ?: 0)
        assertEquals(1, invalid.size)
        assertEquals("Field cannot be longer than 2", invalid.first().message)
    }

    @Test
    fun testCollection() {
        class Model(@SizeLe(2) val data: List<Int>?)

        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(null)))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(listOf())))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(listOf(1))))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(listOf(1, 2))))

        val invalidModel = Model(listOf(1, 2, 3))
        val invalid = validate(invalidModel)
        assertEquals(3, invalidModel.data?.size ?: 0)
        assertEquals(1, invalid.size)
        assertEquals("Field cannot be longer than 2", invalid.first().message)
    }

    @Test
    fun testMap() {
        class Model(@SizeLe(2) val data: Map<String, Int>?)

        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(null)))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(mapOf())))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(mapOf("1" to 1))))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(mapOf("1" to 1, "2" to 2))))

        val invalidModel = Model(mapOf("1" to 1, "2" to 2, "3" to 3))
        val invalid = validate(invalidModel)
        assertEquals(3, invalidModel.data?.size ?: 0)
        assertEquals(1, invalid.size)
        assertEquals("Field cannot be longer than 2", invalid.first().message)
    }

    @Test
    fun testArray() {
        class Model(@SizeLe(2) val data: Array<Int>?)

        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(null)))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(arrayOf())))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(arrayOf(1))))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(arrayOf(1, 2))))

        val invalidModel = Model(arrayOf(1, 2, 3))
        val invalid = validate(invalidModel)
        assertEquals(3, invalidModel.data?.size ?: 0)
        assertEquals(1, invalid.size)
        assertEquals("Field cannot be longer than 2", invalid.first().message)
    }
}