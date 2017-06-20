package com.github.sungminpark

import com.github.sungminpark.validator.SizeGe
import org.junit.Assert.assertEquals
import org.junit.Test
import javax.validation.ConstraintViolation

class TestSizeGe {
    @Test
    fun testString() {
        class Model(@SizeGe(2) val text: String?)

        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(null)))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model("12")))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model("123")))

        val invalidModel = Model("1")
        val invalid = validate(invalidModel)
        assertEquals(1, invalidModel.text?.length ?: 0)
        assertEquals(1, invalid.size)
        assertEquals("Field must be longer than 2", invalid.first().message)
    }

    @Test
    fun testCollection() {
        class Model(@SizeGe(2) val data: List<Int>?)

        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(null)))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(listOf(1, 2))))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(listOf(1, 2, 3))))

        val invalidModel = Model(listOf(1))
        val invalid = validate(invalidModel)
        assertEquals(1, invalidModel.data?.size ?: 0)
        assertEquals(1, invalid.size)
        assertEquals("Field must be longer than 2", invalid.first().message)
    }

    @Test
    fun testMap() {
        class Model(@SizeGe(2) val data: Map<String, Int>?)

        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(null)))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(mapOf("1" to 1, "2" to 2))))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(mapOf("1" to 1, "2" to 2, "3" to 3))))

        val invalidModel = Model(mapOf("1" to 1))
        val invalid = validate(invalidModel)
        assertEquals(1, invalidModel.data?.size ?: 0)
        assertEquals(1, invalid.size)
        assertEquals("Field must be longer than 2", invalid.first().message)
    }

    @Test
    fun testArray() {
        class Model(@SizeGe(2) val data: Array<Int>?)

        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(null)))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(arrayOf(1, 2))))
        assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(arrayOf(1, 2, 3))))

        val invalidModel = Model(arrayOf(1))
        val invalid = validate(invalidModel)
        assertEquals(1, invalidModel.data?.size ?: 0)
        assertEquals(1, invalid.size)
        assertEquals("Field must be longer than 2", invalid.first().message)
    }
}