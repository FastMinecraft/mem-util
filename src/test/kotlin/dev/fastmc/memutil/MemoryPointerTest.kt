package dev.fastmc.memutil

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*
import kotlin.test.assertFailsWith

class MemoryPointerTest {
    private val testDataSize = 1000

    @Test
    fun testProperties() {
        val pointer = MemoryPointer.malloc(0L)
        assert(pointer.address == 0L)
        assert(pointer.length == 0L)
        pointer.address = Long.MAX_VALUE
        pointer.length = Long.MAX_VALUE
        assert(pointer.address == Long.MAX_VALUE)
        assert(pointer.length == Long.MAX_VALUE)
        assertFailsWith(IllegalArgumentException::class) {
            pointer.address = -1
        }
        assertFailsWith(IllegalArgumentException::class) {
            pointer.length = -1
        }
    }

    @Test
    fun testAllocate() {
        val pointer = MemoryPointer.malloc(1)
        assert(pointer.address != 0L)
        assert(pointer.length == 1L)
        pointer.free()
    }

    @Test
    fun testAllocateZero() {
        val pointer = MemoryPointer.malloc(0L)
        assert(pointer.address == 0L)
        assert(pointer.length == 0L)
        pointer.free()
    }

    @Test
    fun testAllocateNegative() {
        assertFailsWith(IllegalArgumentException::class) {
            MemoryPointer.malloc(-1)
        }
    }

    @Test
    fun testCalloc() {
        val pointer = MemoryPointer.calloc(testDataSize.toLong())
        assert(pointer.address != 0L)
        assert(pointer.length == testDataSize.toLong())
        for (i in 0 until pointer.length) {
            assert(pointer.getByteUnsafe(i) == 0.toByte())
        }
        pointer.free()
    }

    @Test
    fun testCheckOffset() {
        val pointer = MemoryPointer.malloc(8L)

        val checkOffsetMethod = MemoryPointer::class.java.getDeclaredMethod(
            "checkOffset",
            Long::class.javaPrimitiveType,
            Int::class.javaPrimitiveType
        )
        checkOffsetMethod.isAccessible = true

        val checkOffset: MemoryPointer.(Long, Int) -> Unit = { offset, size ->
            runCatching {
                checkOffsetMethod.invoke(this, offset, size)
            }.onFailure {
                throw it.cause!!
            }
        }

        assertDoesNotThrow {
            pointer.checkOffset(0, 1)
            pointer.checkOffset(0, 4)
            pointer.checkOffset(7, 1)
            pointer.checkOffset(4, 4)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            checkOffset(pointer, -1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            checkOffset(pointer, -1, 4)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            checkOffset(pointer, 8, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            checkOffset(pointer, 5, 4)
        }

        pointer.free()
    }

    fun foo() {
        val checkDstIndexRange = MemoryPointer::class.java.getDeclaredMethod(
            "checkDstIndexRange",
            Int::class.javaPrimitiveType,
            Int::class.javaPrimitiveType,
            Int::class.javaPrimitiveType
        )
        checkDstIndexRange.isAccessible = true
        val checkDstByteIndexRange = MemoryPointer::class.java.getDeclaredMethod(
            "checkDstByteIndexRange",
            Long::class.javaPrimitiveType,
            Int::class.javaPrimitiveType,
            Long::class.javaPrimitiveType
        )
        checkDstByteIndexRange.isAccessible = true
    }

    @Test
    fun checkIndexRange() {
        val pointer = MemoryPointer.malloc(8L)
        testCheckIndexRange(getCheckIndexRange("checkSrcIndexRange"), pointer)
        testCheckIndexRange(getCheckByteIndexRange("checkSrcByteIndexRange"), pointer)
        testCheckIndexRange(getCheckIndexRange("checkDstIndexRange"), pointer)
        testCheckIndexRange(getCheckByteIndexRange("checkDstByteIndexRange"), pointer)
        pointer.free()
    }

    private fun getCheckByteIndexRange(name: String): MemoryPointer.(Int, Int) -> Unit {
        val checkSrcByteIndexRangeMethod = MemoryPointer::class.java.getDeclaredMethod(
            name,
            Long::class.javaPrimitiveType,
            Int::class.javaPrimitiveType
        )
        checkSrcByteIndexRangeMethod.isAccessible = true

        val checkSrcByteIndexRange: MemoryPointer.(Int, Int) -> Unit = { srcIndex, length ->
            runCatching {
                checkSrcByteIndexRangeMethod.invoke(this, srcIndex, length)
            }.onFailure {
                throw it.cause!!
            }
        }
        return checkSrcByteIndexRange
    }

    private fun getCheckIndexRange(name: String): MemoryPointer.(Int, Int) -> Unit {
        val checkSrcIndexRangeMethod = MemoryPointer::class.java.getDeclaredMethod(
            name,
            Int::class.javaPrimitiveType,
            Int::class.javaPrimitiveType,
            Int::class.javaPrimitiveType
        )
        checkSrcIndexRangeMethod.isAccessible = true

        val checkSrcIndexRange: MemoryPointer.(Int, Int) -> Unit = { srcIndex, length ->
            runCatching {
                checkSrcIndexRangeMethod.invoke(this, srcIndex, length, 8)
            }.onFailure {
                throw it.cause!!
            }
        }
        return checkSrcIndexRange
    }

    private fun testCheckIndexRange(
        checkIndexRange: MemoryPointer.(Int, Int) -> Unit,
        pointer: MemoryPointer
    ) {
        assertDoesNotThrow {
            checkIndexRange(pointer, 0, 0)
            checkIndexRange(pointer, 0, 1)
            checkIndexRange(pointer, 0, 8)
            checkIndexRange(pointer, 7, 1)
            checkIndexRange(pointer, 3, 4)
            checkIndexRange(pointer, 8, 0)
        }

        assertFailsWith(IndexOutOfBoundsException::class) {
            checkIndexRange(pointer, -1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            checkIndexRange(pointer, 9, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            checkIndexRange(pointer, 0, -1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            checkIndexRange(pointer, 0, 9)
        }
    }

    @Test
    fun testByteSingle() {
        val array = randomBytes()

        val pointer = MemoryPointer.malloc(array.size.toLong())

        for (i in array.indices) {
            pointer.setByteUnsafe(i.toLong(), array[i])
        }

        for (i in array.indices) {
            assert(array[i] == pointer.getByteUnsafe(i))
        }

        pointer.free()
    }

    @Test
    fun testShortSingle() {
        val array = randomShorts()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 2)

        for (i in array.indices) {
            pointer.setShortUnsafe(i * 2L, array[i])
        }

        for (i in array.indices) {
            assert(array[i] == pointer.getShortUnsafe(i * 2))
        }

        pointer.free()
    }

    @Test
    fun testIntSingle() {
        val array = randomInts()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 4)

        for (i in array.indices) {
            pointer.setIntUnsafe(i * 4L, array[i])
        }

        for (i in array.indices) {
            assert(array[i] == pointer.getIntUnsafe(i * 4))
        }

        pointer.free()
    }

    @Test
    fun testLongSingle() {
        val array = randomLongs()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 8)

        for (i in array.indices) {
            pointer.setLongUnsafe(i * 8L, array[i])
        }

        for (i in array.indices) {
            assert(array[i] == pointer.getLongUnsafe(i * 8))
        }

        pointer.free()
    }

    @Test
    fun testFloatSingle() {
        val array = randomFloats()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 4)

        for (i in array.indices) {
            pointer.setFloatUnsafe(i * 4L, array[i])
        }

        for (i in array.indices) {
            assert(array[i] == pointer.getFloatUnsafe(i * 4))
        }

        pointer.free()
    }

    @Test
    fun testDoubleSingle() {
        val array = randomDoubles()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 8)

        for (i in array.indices) {
            pointer.setDoubleUnsafe(i * 8L, array[i])
        }

        for (i in array.indices) {
            assert(array[i] == pointer.getDoubleUnsafe(i * 8))
        }

        pointer.free()
    }

    @Test
    fun testByteBulk() {
        val array = randomBytes()

        val pointer = MemoryPointer.malloc(array.size.toLong())

        pointer.setBytesUnsafe(array)

        for (i in array.indices) {
            assert(array[i] == pointer.getByteUnsafe(i))
        }

        assert(array.contentEquals(pointer.getBytesUnsafe()))
        assert(array.contentEquals(pointer.getBytesUnsafe(ByteArray(array.size))))

        pointer.free()
    }


    @Test
    fun testShortBulk() {
        val array = randomShorts()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 2)

        pointer.setShortsUnsafe(array)

        for (i in array.indices) {
            assert(array[i] == pointer.getShortUnsafe(i * 2))
        }

        assert(array.contentEquals(pointer.getShortsUnsafe()))
        assert(array.contentEquals(pointer.getShortsUnsafe(ShortArray(array.size))))

        pointer.free()
    }

    @Test
    fun testIntBulk() {
        val array = randomInts()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 4)

        pointer.setIntsUnsafe(array)

        for (i in array.indices) {
            assert(array[i] == pointer.getIntUnsafe(i * 4))
        }

        assert(array.contentEquals(pointer.getIntsUnsafe()))
        assert(array.contentEquals(pointer.getIntsUnsafe(IntArray(array.size))))

        pointer.free()
    }

    @Test
    fun testLongBulk() {
        val array = randomLongs()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 8)

        pointer.setLongsUnsafe(array)

        for (i in array.indices) {
            assert(array[i] == pointer.getLongUnsafe(i * 8))
        }

        assert(array.contentEquals(pointer.getLongsUnsafe()))
        assert(array.contentEquals(pointer.getLongsUnsafe(LongArray(array.size))))

        pointer.free()
    }

    @Test
    fun testFloatBulk() {
        val array = randomFloats()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 4)

        pointer.setFloatsUnsafe(array)

        for (i in array.indices) {
            assert(array[i] == pointer.getFloatUnsafe(i * 4))
        }

        assert(array.contentEquals(pointer.getFloatsUnsafe()))
        assert(array.contentEquals(pointer.getFloatsUnsafe(FloatArray(array.size))))

        pointer.free()
    }

    @Test
    fun testDoubleBulk() {
        val array = randomDoubles()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 8)

        pointer.setDoublesUnsafe(array)

        for (i in array.indices) {
            assert(array[i] == pointer.getDoubleUnsafe(i * 8))
        }

        assert(array.contentEquals(pointer.getDoublesUnsafe()))
        assert(array.contentEquals(pointer.getDoublesUnsafe(DoubleArray(array.size))))

        pointer.free()
    }

    @Test
    fun testIterationByte() {
        val array = randomBytes()

        val pointer = MemoryPointer.malloc(array.size.toLong())

        pointer.setBytesUnsafe(array)

        var index = 0
        pointer.forEachByteUnsafe { v ->
            assert(v == array[index++])
        }

        pointer.forEachByteIndexedUnsafe(testDataSize / 4L, testDataSize / 2) { i, v ->
            assert(v == array[i + testDataSize / 4])
        }

        pointer.free()
    }

    @Test
    fun testIterationShort() {
        val array = randomShorts()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 2)

        pointer.setShortsUnsafe(array)

        var index = 0
        pointer.forEachShortUnsafe { v ->
            assert(v == array[index++])
        }

        pointer.forEachShortIndexedUnsafe(testDataSize / 4L * 2L, testDataSize / 2) { i, v ->
            assert(v == array[i + testDataSize / 4])
        }

        pointer.free()
    }

    @Test
    fun testIterationInt() {
        val array = randomInts()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 4)

        pointer.setIntsUnsafe(array)

        var index = 0
        pointer.forEachIntUnsafe { v ->
            assert(v == array[index++])
        }

        pointer.forEachIntIndexedUnsafe(testDataSize / 4L * 4L, testDataSize / 2) { i, v ->
            assert(v == array[i + testDataSize / 4])
        }

        pointer.free()
    }

    @Test
    fun testIterationLong() {
        val array = randomLongs()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 8)

        pointer.setLongsUnsafe(array)

        var index = 0
        pointer.forEachLongUnsafe { v ->
            assert(v == array[index++])
        }

        pointer.forEachLongIndexedUnsafe(testDataSize / 4L * 8L, testDataSize / 2) { i, v ->
            assert(v == array[i + testDataSize / 4])
        }

        pointer.free()
    }

    @Test
    fun testIterationFloat() {
        val array = randomFloats()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 4)

        pointer.setFloatsUnsafe(array)

        var index = 0
        pointer.forEachFloatUnsafe { v ->
            assert(v == array[index++])
        }

        pointer.forEachFloatIndexedUnsafe(testDataSize / 4L * 4L, testDataSize / 2) { i, v ->
            assert(v == array[i + testDataSize / 4])
        }

        pointer.free()
    }

    @Test
    fun testIterationDouble() {
        val array = randomDoubles()

        val pointer = MemoryPointer.malloc(array.size.toLong() * 8)

        pointer.setDoublesUnsafe(array)

        var index = 0
        pointer.forEachDouble { v ->
            assert(v == array[index++])
        }

        pointer.forEachDoubleIndexed(testDataSize / 4L * 8L, testDataSize / 2) { i, v ->
            assert(v == array[i + testDataSize / 4])
        }

        pointer.free()
    }

    private fun randomBytes(): ByteArray {
        val random = Random()
        val array = ByteArray(testDataSize)
        random.nextBytes(array)
        return array
    }

    private fun randomShorts(): ShortArray {
        val random = Random()
        val array = ShortArray(testDataSize)
        for (i in array.indices) {
            array[i] = random.nextInt().toShort()
        }
        return array
    }

    private fun randomInts(): IntArray {
        val random = Random()
        val array = IntArray(testDataSize)
        for (i in array.indices) {
            array[i] = random.nextInt()
        }
        return array
    }

    private fun randomLongs(): LongArray {
        val random = Random()
        val array = LongArray(testDataSize)
        for (i in array.indices) {
            array[i] = random.nextLong()
        }
        return array
    }

    private fun randomFloats(): FloatArray {
        val random = Random()
        val array = FloatArray(testDataSize)
        for (i in array.indices) {
            array[i] = random.nextFloat()
        }
        return array
    }

    private fun randomDoubles(): DoubleArray {
        val random = Random()
        val array = DoubleArray(testDataSize)
        for (i in array.indices) {
            array[i] = random.nextDouble()
        }
        return array
    }
}