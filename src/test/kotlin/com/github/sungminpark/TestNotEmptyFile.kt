package com.github.sungminpark

import com.github.sungminpark.validator.NotEmptyFile
import org.junit.Assert
import org.junit.Test
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.InputStream
import javax.validation.ConstraintViolation

class TestNotEmptyFile {
    @Test
    fun testNotEmptyFile() {
        class Model(@NotEmptyFile val file: MultipartFile?)
        class TestMultipartFile(private val empty: Boolean) : MultipartFile {
            override fun isEmpty(): Boolean {
                return empty
            }

            override fun getName(): String {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getSize(): Long {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getBytes(): ByteArray {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getOriginalFilename(): String {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getInputStream(): InputStream {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getContentType(): String {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun transferTo(dest: File?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

        Assert.assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(null)))
        Assert.assertEquals(setOf<ConstraintViolation<Model>>(), validate(Model(TestMultipartFile(false))))

        val invalidModel = Model(TestMultipartFile(true))
        Assert.assertEquals(true, invalidModel.file?.isEmpty)

        val invalid = validate(invalidModel)
        Assert.assertEquals(1, invalid.size)
        Assert.assertEquals("may not be empty", invalid.first().message)
    }
}