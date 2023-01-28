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
    fun testReadIndexExceptionSingle() {
        val pointer = MemoryPointer.malloc(8L)

        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getByte(-1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getByte(8)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getShort(-1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getShort(8)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getInt(-1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getInt(8)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getLong(-1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getLong(8)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getFloat(-1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getFloat(8)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getDouble(-1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getDouble(8)
        }

        pointer.free()
    }

    @Test
    fun testWriteIndexExceptionSingle() {
        val pointer = MemoryPointer.malloc(8L)

        assertDoesNotThrow {
            pointer.setByte(0, 0)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setByte(-1, 0)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setByte(8, 0)
        }
        assertDoesNotThrow {
            pointer.setShort(0, 0)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setShort(-1, 0)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setShort(8, 0)
        }
        assertDoesNotThrow {
            pointer.setInt(0, 0)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setInt(-1, 0)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setInt(8, 0)
        }
        assertDoesNotThrow {
            pointer.setLong(0, 0)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setLong(-1, 0)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setLong(8, 0)
        }
        assertDoesNotThrow {
            pointer.setFloat(0, 0f)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setFloat(-1, 0f)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setFloat(8, 0f)
        }
        assertDoesNotThrow {
            pointer.setDouble(0, 0.0)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setDouble(-1, 0.0)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setDouble(8, 0.0)
        }

        pointer.free()
    }

    @Test
    fun testByteBulkReadIndexException() {
        val pointer = MemoryPointer.malloc(8L)

        assertDoesNotThrow {
            pointer.getBytes(ByteArray(1), 0, 0, 0)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getBytes(ByteArray(1), -1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getBytes(ByteArray(1), 8, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getBytes(ByteArray(1), 0, -1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getBytes(ByteArray(1), 0, 1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getBytes(ByteArray(1), 0, 0, -1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getBytes(ByteArray(1), 0, 0, 2)
        }

        pointer.free()
    }

    @Test
    fun testShortBulkReadIndexException() {
        val pointer = MemoryPointer.malloc(8L)

        assertDoesNotThrow {
            pointer.getShorts(ShortArray(1), 0, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getShorts(ShortArray(1), -1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getShorts(ShortArray(1), 8, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getShorts(ShortArray(1), 0, -1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getShorts(ShortArray(1), 0, 1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getShorts(ShortArray(1), 0, 0, -1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getShorts(ShortArray(1), 0, 0, 2)
        }

        pointer.free()
    }

    @Test
    fun testIntBulkReadIndexException() {
        val pointer = MemoryPointer.malloc(8L)

        assertDoesNotThrow {
            pointer.getInts(IntArray(1), 0, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getInts(IntArray(1), -1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getInts(IntArray(1), 8, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getInts(IntArray(1), 0, -1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getInts(IntArray(1), 0, 1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getInts(IntArray(1), 0, 0, -1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getInts(IntArray(1), 0, 0, 2)
        }

        pointer.free()
    }

    @Test
    fun testLongBulkReadIndexException() {
        val pointer = MemoryPointer.malloc(8L)

        assertDoesNotThrow {
            pointer.getLongs(LongArray(1), 0, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getLongs(LongArray(1), -1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getLongs(LongArray(1), 8, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getLongs(LongArray(1), 0, -1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getLongs(LongArray(1), 0, 1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getLongs(LongArray(1), 0, 0, -1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getLongs(LongArray(1), 0, 0, 2)
        }

        pointer.free()
    }

    @Test
    fun testFloatBulkReadIndexException() {
        val pointer = MemoryPointer.malloc(8L)

        assertDoesNotThrow {
            pointer.getFloats(FloatArray(1), 0, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getFloats(FloatArray(1), -1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getFloats(FloatArray(1), 8, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getFloats(FloatArray(1), 0, -1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getFloats(FloatArray(1), 0, 1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getFloats(FloatArray(1), 0, 0, -1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getFloats(FloatArray(1), 0, 0, 2)
        }

        pointer.free()
    }

    @Test
    fun testDoubleBulkReadIndexException() {
        val pointer = MemoryPointer.malloc(8L)

        assertDoesNotThrow {
            pointer.getDoubles(DoubleArray(1), 0, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getDoubles(DoubleArray(1), -1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getDoubles(DoubleArray(1), 8, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getDoubles(DoubleArray(1), 0, -1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getDoubles(DoubleArray(1), 0, 1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getDoubles(DoubleArray(1), 0, 0, -1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.getDoubles(DoubleArray(1), 0, 0, 2)
        }

        pointer.free()
    }

    @Test
    fun testByteBulkWriteIndexException() {
        val pointer = MemoryPointer.malloc(8L)

        assertDoesNotThrow {
            pointer.setBytes(ByteArray(1), 0, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setBytes(ByteArray(1), -1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setBytes(ByteArray(1), 1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setBytes(ByteArray(1), 0, -1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setBytes(ByteArray(1), 0, 8, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setBytes(ByteArray(1), 0, 0, -1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setBytes(ByteArray(1), 0, 0, 2)
        }

        pointer.free()
    }

    @Test
    fun testShortBulkWriteIndexException() {
        val pointer = MemoryPointer.malloc(8L)

        assertDoesNotThrow {
            pointer.setShorts(ShortArray(1), 0, 0, 0)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setShorts(ShortArray(1), -1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setShorts(ShortArray(1), 1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setShorts(ShortArray(1), 0, -1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setShorts(ShortArray(1), 0, 8, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setShorts(ShortArray(1), 0, 0, -1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setShorts(ShortArray(1), 0, 0, 2)
        }

        pointer.free()
    }

    @Test
    fun testIntBulkWriteIndexException() {
        val pointer = MemoryPointer.malloc(8L)

        assertDoesNotThrow {
            pointer.setInts(IntArray(1), 0, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setInts(IntArray(1), -1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setInts(IntArray(1), 1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setInts(IntArray(1), 0, -1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setInts(IntArray(1), 0, 8, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setInts(IntArray(1), 0, 0, -1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setInts(IntArray(1), 0, 0, 2)
        }

        pointer.free()
    }

    @Test
    fun testLongBulkWriteIndexException() {
        val pointer = MemoryPointer.malloc(8L)

        assertDoesNotThrow {
            pointer.setLongs(LongArray(1), 0, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setLongs(LongArray(1), -1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setLongs(LongArray(1), 1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setLongs(LongArray(1), 0, -1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setLongs(LongArray(1), 0, 8, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setLongs(LongArray(1), 0, 0, -1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setLongs(LongArray(1), 0, 0, 2)
        }

        pointer.free()
    }

    @Test
    fun testFloatBulkWriteIndexException() {
        val pointer = MemoryPointer.malloc(8L)

        assertDoesNotThrow {
            pointer.setFloats(FloatArray(1), 0, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setFloats(FloatArray(1), -1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setFloats(FloatArray(1), 1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setFloats(FloatArray(1), 0, -1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setFloats(FloatArray(1), 0, 8, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setFloats(FloatArray(1), 0, 0, -1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setFloats(FloatArray(1), 0, 0, 2)
        }

        pointer.free()
    }

    @Test
    fun testDoubleBulkWriteIndexException() {
        val pointer = MemoryPointer.malloc(8L)

        assertDoesNotThrow {
            pointer.setDoubles(DoubleArray(1), 0, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setDoubles(DoubleArray(1), -1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setDoubles(DoubleArray(1), 1, 0, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setDoubles(DoubleArray(1), 0, -1, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setDoubles(DoubleArray(1), 0, 8, 1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setDoubles(DoubleArray(1), 0, 0, -1)
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            pointer.setDoubles(DoubleArray(1), 0, 0, 2)
        }

        pointer.free()
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