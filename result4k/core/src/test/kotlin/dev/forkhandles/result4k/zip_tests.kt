package dev.forkhandles.result4k

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ZipTests {
    private data class SomeError(val message: String)

    @Test
    fun `success zip returns value`() {
        val result = zip(Success(123)) { it + 1 }
        assertEquals(Success(124), result)
    }

    @Test
    fun `success flatZip returns value`() {
        val result = flatZip(Success(123)) { Success(it + 1) }
        assertEquals(Success(124), result)
    }

    @Test
    fun `failure flatZip returns value`() {
        val result = flatZip(Success(123)) { Failure(SomeError("flatZip failure")) }
        assertEquals(Failure(SomeError("flatZip failure")), result)
    }

    @Test
    fun `success double flatZip returns value`() {
        val r1: Result<String, SomeError> = Success("x")
        val r2: Result<Int, SomeError> = Success(123)

        val result: Result<Boolean, SomeError> = flatZip(r1, r2) { s: String, i: Int ->
            assertEquals("x", s)
            assertEquals(123, i)
            Success(false)
        }

        assertEquals(Success(false), result)
    }

    @Test
    fun `failure on first result of a double flatZip returns value`() {
        val r1: Result<String, SomeError> = Failure(SomeError("r1"))
        val r2: Result<Int, SomeError> = Success(123)

        val result: Result<Boolean, SomeError> = flatZip(r1, r2) { _: String, _: Int ->
            fail("shouldn't be called")
        }

        assertEquals(Failure(SomeError("r1")), result)
    }

    @Test
    fun `failure on second result of a double flatZip returns value`() {
        val r1: Result<String, SomeError> = Success("x")
        val r2: Result<Int, SomeError> = Failure(SomeError("r2"))

        val result: Result<Boolean, SomeError> = flatZip(r1, r2) { _: String, _: Int ->
            fail("shouldn't be called")
        }

        assertEquals(Failure(SomeError("r2")), result)
    }

    @Test
    fun `failure on transformation of a double flatZip returns value`() {
        val r1: Result<String, SomeError> = Success("x")
        val r2: Result<Int, SomeError> = Success(123)

        val result: Result<Boolean, SomeError> = flatZip(r1, r2) { s: String, i: Int ->
            assertEquals("x", s)
            assertEquals(123, i)
            Failure(SomeError("fail on transformation"))
        }

        assertEquals(Failure(SomeError("fail on transformation")), result)
    }
}
