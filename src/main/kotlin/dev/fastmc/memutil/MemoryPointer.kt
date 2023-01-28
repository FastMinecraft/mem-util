package dev.fastmc.memutil

import kotlin.math.min

interface MemoryPointer : AutoCloseable {
    var length: Long
    var address: Long

    fun reallocate(newLength: Long) {
        require(newLength >= 0) { "Length must be positive or zero" }
        address = if (address == 0L) UNSAFE.allocateMemory(newLength) else UNSAFE.reallocateMemory(address, newLength)
    }

    fun free() {
        if (address != 0L) {
            UNSAFE.freeMemory(address)
            address = 0L
        }
    }

    override fun close() {
        free()
    }

    // Read access
    // Unsafe single read access
    fun getByteUnsafe(offset: Long): Byte {
        return UNSAFE.getByte(address + offset)
    }

    fun getByteUnsafe(offset: Int): Byte {
        return UNSAFE.getByte(address + offset)
    }

    fun getShortUnsafe(offset: Long): Short {
        return UNSAFE.getShort(address + offset)
    }

    fun getShortUnsafe(offset: Int): Short {
        return UNSAFE.getShort(address + offset)
    }

    fun getIntUnsafe(offset: Long): Int {
        return UNSAFE.getInt(address + offset)
    }

    fun getIntUnsafe(offset: Int): Int {
        return UNSAFE.getInt(address + offset)
    }

    fun getLongUnsafe(offset: Long): Long {
        return UNSAFE.getLong(address + offset)
    }

    fun getLongUnsafe(offset: Int): Long {
        return UNSAFE.getLong(address + offset)
    }

    fun getFloatUnsafe(offset: Long): Float {
        return UNSAFE.getFloat(address + offset)
    }

    fun getFloatUnsafe(offset: Int): Float {
        return UNSAFE.getFloat(address + offset)
    }

    fun getDoubleUnsafe(offset: Long): Double {
        return UNSAFE.getDouble(address + offset)
    }

    fun getDoubleUnsafe(offset: Int): Double {
        return UNSAFE.getDouble(address + offset)
    }

    // Safe single read access
    fun getByte(offset: Long): Byte {
        checkOffset(offset, 1)
        return getByteUnsafe(offset)
    }

    fun getShort(offset: Long): Short {
        checkOffset(offset, 2)
        return getShortUnsafe(offset)
    }

    fun getInt(offset: Long): Int {
        checkOffset(offset, 4)
        return getIntUnsafe(offset)
    }

    fun getLong(offset: Long): Long {
        checkOffset(offset, 8)
        return getLongUnsafe(offset)
    }

    fun getFloat(offset: Long): Float {
        checkOffset(offset, 4)
        return getFloatUnsafe(offset)
    }

    fun getDouble(offset: Long): Double {
        checkOffset(offset, 8)
        return getDoubleUnsafe(offset)
    }

    // Unsafe bulk read access
    fun getBytesUnsafe(
        a: ByteArray,
        srcByteOffset: Long = 0L,
        dstByteOffset: Int = 0,
        length: Int = a.size
    ): ByteArray {
        UNSAFE.copyMemory(
            null,
            address + srcByteOffset,
            a,
            (BYTE_ARRAY_OFFSET + dstByteOffset).toLong(),
            length.toLong()
        )
        return a
    }

    fun getBytesUnsafe(srcByteOffset: Long = 0L, length: Int = this.length.toInt()): ByteArray {
        return getBytesUnsafe(ByteArray(length), srcByteOffset, 0, length)
    }

    fun getShortsUnsafe(
        a: ShortArray,
        srcByteOffset: Long = 0L,
        dstByteOffset: Int = 0,
        length: Int = a.size
    ): ShortArray {
        UNSAFE.copyMemory(
            null,
            address + srcByteOffset,
            a,
            (SHORT_ARRAY_OFFSET + dstByteOffset).toLong(),
            (length * 2).toLong()
        )
        return a
    }

    fun getShortsUnsafe(srcByteOffset: Long = 0L, length: Int = (this.length / 2L).toInt()): ShortArray {
        return getShortsUnsafe(ShortArray(length), srcByteOffset, 0, length)
    }

    fun getIntsUnsafe(a: IntArray, srcByteOffset: Long = 0L, dstByteOffset: Int = 0, length: Int = a.size): IntArray {
        UNSAFE.copyMemory(
            null,
            address + srcByteOffset,
            a,
            (INT_ARRAY_OFFSET + dstByteOffset).toLong(),
            (length * 4).toLong()
        )
        return a
    }

    fun getIntsUnsafe(srcByteOffset: Long = 0L, length: Int = (this.length / 4L).toInt()): IntArray {
        return getIntsUnsafe(IntArray(length), srcByteOffset, 0, length)
    }

    fun getLongsUnsafe(
        a: LongArray,
        srcByteOffset: Long = 0L,
        dstByteOffset: Int = 0,
        length: Int = a.size
    ): LongArray {
        UNSAFE.copyMemory(
            null,
            address + srcByteOffset,
            a,
            (LONG_ARRAY_OFFSET + dstByteOffset).toLong(),
            (length * 8).toLong()
        )
        return a
    }

    fun getLongsUnsafe(srcByteOffset: Long = 0L, length: Int = (this.length / 8L).toInt()): LongArray {
        return getLongsUnsafe(LongArray(length), srcByteOffset, 0, length)
    }

    fun getFloatsUnsafe(
        a: FloatArray,
        srcByteOffset: Long = 0L,
        dstByteOffset: Int = 0,
        length: Int = a.size
    ): FloatArray {
        UNSAFE.copyMemory(
            null,
            address + srcByteOffset,
            a,
            (FLOAT_ARRAY_OFFSET + dstByteOffset).toLong(),
            (length * 4).toLong()
        )
        return a
    }

    fun getFloatsUnsafe(srcByteOffset: Long = 0L, length: Int = (this.length / 4L).toInt()): FloatArray {
        return getFloatsUnsafe(FloatArray(length), srcByteOffset, 0, length)
    }

    fun getDoublesUnsafe(
        a: DoubleArray,
        srcByteOffset: Long = 0L,
        dstByteOffset: Int = 0,
        length: Int = a.size
    ): DoubleArray {
        UNSAFE.copyMemory(
            null,
            address + srcByteOffset,
            a,
            (DOUBLE_ARRAY_OFFSET + dstByteOffset).toLong(),
            (length * 8).toLong()
        )
        return a
    }

    fun getDoublesUnsafe(srcByteOffset: Long = 0L, length: Int = (this.length / 8L).toInt()): DoubleArray {
        return getDoublesUnsafe(DoubleArray(length), srcByteOffset, 0, length)
    }

    // Safe bulk read access
    fun getBytes(a: ByteArray, srcByteOffset: Long = 0L, dstOffset: Int = 0, length: Int = a.size): ByteArray {
        checkSrcByteIndexRange(srcByteOffset, length)
        checkDstIndexRange(dstOffset, length, a.size)
        return getBytesUnsafe(a, srcByteOffset, dstOffset, length)
    }

    fun getBytes(srcByteOffset: Long = 0L, length: Int = this.length.toInt()): ByteArray {
        checkSrcByteIndexRange(srcByteOffset, length)
        return getBytesUnsafe(srcByteOffset, length)
    }

    fun getShorts(a: ShortArray, srcByteOffset: Long = 0L, dstOffset: Int = 0, length: Int = a.size): ShortArray {
        checkSrcByteIndexRange(srcByteOffset, length * 2)
        checkDstIndexRange(dstOffset, length, a.size)
        return getShortsUnsafe(a, srcByteOffset, dstOffset, length)
    }

    fun getShorts(srcByteOffset: Long = 0L, length: Int = (this.length / 2L).toInt()): ShortArray {
        checkSrcByteIndexRange(srcByteOffset, length * 2)
        return getShortsUnsafe(srcByteOffset, length)
    }

    fun getInts(a: IntArray, srcByteOffset: Long = 0L, dstOffset: Int = 0, length: Int = a.size): IntArray {
        checkSrcByteIndexRange(srcByteOffset, length * 4)
        checkDstIndexRange(dstOffset, length, a.size)
        return getIntsUnsafe(a, srcByteOffset, dstOffset, length)
    }

    fun getInts(srcByteOffset: Long = 0L, length: Int = (this.length / 4L).toInt()): IntArray {
        checkSrcByteIndexRange(srcByteOffset, length * 4)
        return getIntsUnsafe(srcByteOffset, length)
    }

    fun getLongs(a: LongArray, srcByteOffset: Long = 0L, dstOffset: Int = 0, length: Int = a.size): LongArray {
        checkSrcByteIndexRange(srcByteOffset, length * 8)
        checkDstIndexRange(dstOffset, length, a.size)
        return getLongsUnsafe(a, srcByteOffset, dstOffset, length)
    }

    fun getLongs(srcByteOffset: Long = 0L, length: Int = (this.length / 8L).toInt()): LongArray {
        checkSrcByteIndexRange(srcByteOffset, length * 8)
        return getLongsUnsafe(srcByteOffset, length)
    }

    fun getFloats(a: FloatArray, srcByteOffset: Long = 0L, dstOffset: Int = 0, length: Int = a.size): FloatArray {
        checkSrcByteIndexRange(srcByteOffset, length * 4)
        checkDstIndexRange(dstOffset, length, a.size)
        return getFloatsUnsafe(a, srcByteOffset, dstOffset, length)
    }

    fun getFloats(srcByteOffset: Long = 0L, length: Int = (this.length / 4L).toInt()): FloatArray {
        checkSrcByteIndexRange(srcByteOffset, length * 4)
        return getFloatsUnsafe(srcByteOffset, length)
    }

    fun getDoubles(a: DoubleArray, srcByteOffset: Long = 0L, dstOffset: Int = 0, length: Int = a.size): DoubleArray {
        checkSrcByteIndexRange(srcByteOffset, length * 8)
        checkDstIndexRange(dstOffset, length, a.size)
        return getDoublesUnsafe(a, srcByteOffset, dstOffset, length)
    }

    fun getDoubles(srcByteOffset: Long = 0L, length: Int = (this.length / 8L).toInt()): DoubleArray {
        checkSrcByteIndexRange(srcByteOffset, length * 8)
        return getDoublesUnsafe(srcByteOffset, length)
    }

    // Write access
    // Unsafe single write access
    fun setByteUnsafe(offset: Long, value: Byte) {
        UNSAFE.putByte(address + offset, value)
    }

    fun setShortUnsafe(offset: Long, value: Short) {
        UNSAFE.putShort(address + offset, value)
    }

    fun setIntUnsafe(offset: Long, value: Int) {
        UNSAFE.putInt(address + offset, value)
    }

    fun setLongUnsafe(offset: Long, value: Long) {
        UNSAFE.putLong(address + offset, value)
    }

    fun setFloatUnsafe(offset: Long, value: Float) {
        UNSAFE.putFloat(address + offset, value)
    }

    fun setDoubleUnsafe(offset: Long, value: Double) {
        UNSAFE.putDouble(address + offset, value)
    }

    // Safe single write access
    fun setByte(offset: Long, value: Byte) {
        checkOffset(offset, 1)
        setByteUnsafe(offset, value)
    }

    fun setShort(offset: Long, value: Short) {
        checkOffset(offset, 2)
        setShortUnsafe(offset, value)
    }

    fun setInt(offset: Long, value: Int) {
        checkOffset(offset, 4)
        setIntUnsafe(offset, value)
    }

    fun setLong(offset: Long, value: Long) {
        checkOffset(offset, 8)
        setLongUnsafe(offset, value)
    }

    fun setFloat(offset: Long, value: Float) {
        checkOffset(offset, 4)
        setFloatUnsafe(offset, value)
    }

    fun setDouble(offset: Long, value: Double) {
        checkOffset(offset, 8)
        setDoubleUnsafe(offset, value)
    }

    // Unsafe bulk write access
    fun setBytesUnsafe(a: ByteArray, srcOffset: Int = 0, dstByteOffset: Long = 0L, length: Int = a.size) {
        UNSAFE.copyMemory(a, (BYTE_ARRAY_OFFSET + srcOffset).toLong(), null, address + dstByteOffset, length.toLong())
    }

    fun setShortsUnsafe(a: ShortArray, srcOffset: Int = 0, dstByteOffset: Long = 0L, length: Int = a.size) {
        UNSAFE.copyMemory(
            a,
            (SHORT_ARRAY_OFFSET + srcOffset).toLong(),
            null,
            address + dstByteOffset,
            (length * 2).toLong()
        )
    }

    fun setIntsUnsafe(a: IntArray, srcOffset: Int = 0, dstByteOffset: Long = 0L, length: Int = a.size) {
        UNSAFE.copyMemory(
            a,
            (INT_ARRAY_OFFSET + srcOffset).toLong(),
            null,
            address + dstByteOffset,
            (length * 4).toLong()
        )
    }

    fun setLongsUnsafe(a: LongArray, srcOffset: Int = 0, dstByteOffset: Long = 0L, length: Int = a.size) {
        UNSAFE.copyMemory(
            a,
            (LONG_ARRAY_OFFSET + srcOffset).toLong(),
            null,
            address + dstByteOffset,
            (length * 8).toLong()
        )
    }

    fun setFloatsUnsafe(a: FloatArray, srcOffset: Int = 0, dstByteOffset: Long = 0L, length: Int = a.size) {
        UNSAFE.copyMemory(
            a,
            (FLOAT_ARRAY_OFFSET + srcOffset).toLong(),
            null,
            address + dstByteOffset,
            (length * 4).toLong()
        )
    }

    fun setDoublesUnsafe(a: DoubleArray, srcOffset: Int = 0, dstByteOffset: Long = 0L, length: Int = a.size) {
        UNSAFE.copyMemory(
            a,
            (DOUBLE_ARRAY_OFFSET + srcOffset).toLong(),
            null,
            address + dstByteOffset,
            (length * 8).toLong()
        )
    }

    // Safe bulk write access
    fun setBytes(a: ByteArray, srcOffset: Int = 0, dstByteOffset: Long = 0L, length: Int = a.size): ByteArray {
        checkSrcIndexRange(srcOffset, length, a.size)
        checkDstByteIndexRange(dstByteOffset, length)
        setBytesUnsafe(a, srcOffset, dstByteOffset, length)
        return a
    }

    fun setShorts(a: ShortArray, srcOffset: Int = 0, dstByteOffset: Long = 0L, length: Int = a.size): ShortArray {
        checkSrcIndexRange(srcOffset, length, a.size)
        checkDstByteIndexRange(dstByteOffset, length * 2)
        setShortsUnsafe(a, srcOffset, dstByteOffset, length)
        return a
    }

    fun setInts(a: IntArray, srcOffset: Int = 0, dstByteOffset: Long = 0L, length: Int = a.size): IntArray {
        checkSrcIndexRange(srcOffset, length, a.size)
        checkDstByteIndexRange(dstByteOffset, length * 4)
        setIntsUnsafe(a, srcOffset, dstByteOffset, length)
        return a
    }

    fun setLongs(a: LongArray, srcOffset: Int = 0, dstByteOffset: Long = 0L, length: Int = a.size): LongArray {
        checkSrcIndexRange(srcOffset, length, a.size)
        checkDstByteIndexRange(dstByteOffset, length * 8)
        setLongsUnsafe(a, srcOffset, dstByteOffset, length)
        return a
    }

    fun setFloats(a: FloatArray, srcOffset: Int = 0, dstByteOffset: Long = 0L, length: Int = a.size): FloatArray {
        checkSrcIndexRange(srcOffset, length, a.size)
        checkDstByteIndexRange(dstByteOffset, length * 4)
        setFloatsUnsafe(a, srcOffset, dstByteOffset, length)
        return a
    }

    fun setDoubles(a: DoubleArray, srcOffset: Int = 0, dstByteOffset: Long = 0L, length: Int = a.size): DoubleArray {
        checkSrcIndexRange(srcOffset, length, a.size)
        checkDstByteIndexRange(dstByteOffset, length * 8)
        setDoublesUnsafe(a, srcOffset, dstByteOffset, length)
        return a
    }

    fun subList(offset: Long = 0, length: Long = this.length): MemoryPointer {
        if (offset < 0L || offset + length > this.length) {
            throw IndexOutOfBoundsException("offset $offset is out of bounds for length $length")
        }
        return MemoryArray.wrap(this, offset, length)
    }

    fun ensureCapacity(capacity: Long) {
        require(capacity >= 0) { "Capacity must be positive or zero" }
        if (capacity > this.length) {
            reallocate(min(capacity, length * 2L))
        }
    }

    fun trim(size: Long) {
        require(size >= 0) { "Size must be positive or zero" }
        if (size < this.length) {
            reallocate(size)
        }
    }

    private fun checkOffset(offset: Long, unitSize: Int) {
        if (offset < 0 || offset + unitSize > length) {
            throw IndexOutOfBoundsException("Offset $offset is out of bounds for length $length")
        }
    }

    private fun checkSrcIndexRange(srcOffset: Int, length: Int, maxLength: Int) {
        if (srcOffset < 0 || srcOffset > maxLength) {
            throw IndexOutOfBoundsException("srcOffset $srcOffset is out of bounds for length $maxLength")
        }
        if (length < 0 || srcOffset + length > maxLength) {
            throw IndexOutOfBoundsException("length $length is out of bounds for length $maxLength")
        }
    }

    private fun checkSrcByteIndexRange(srcByteOffset: Long, length: Int) {
        if (srcByteOffset < 0 || srcByteOffset > this.length) {
            throw IndexOutOfBoundsException("srcByteOffset $srcByteOffset is out of bounds for length ${this.length}")
        }
        if (length < 0 || srcByteOffset + length > this.length) {
            throw IndexOutOfBoundsException("length $length is out of bounds for length ${this.length}")
        }
    }

    private fun checkDstIndexRange(dstOffset: Int, length: Int, maxLength: Int) {
        if (dstOffset < 0 || dstOffset > maxLength) {
            throw IndexOutOfBoundsException("dstOffset $dstOffset is out of bounds for length $maxLength")
        }
        if (length < 0 || dstOffset + length > maxLength) {
            throw IndexOutOfBoundsException("length $length is out of bounds for length $maxLength")
        }
    }

    private fun checkDstByteIndexRange(dstByteOffset: Long, length: Int) {
        if (dstByteOffset < 0 || dstByteOffset > this.length) {
            throw IndexOutOfBoundsException("dstByteOffset $dstByteOffset is out of bounds for length ${this.length}")
        }
        if (length < 0 || dstByteOffset + length > this.length) {
            throw IndexOutOfBoundsException("length $length is out of bounds for length ${this.length}")
        }
    }

    companion object {
        @JvmStatic
        fun wrap(address: Long, length: Long): MemoryPointer {
            require(address >= 0) { "Address must be positive or zero" }
            require(length >= 0) { "Length must be positive or zero" }
            return WrappedPointer(address, length)
        }

        @JvmStatic
        fun malloc(length: Long): MemoryPointer {
            require(length >= 0) { "Length must be positive or zero" }
            return MemoryPointerImpl(if (length == 0L) 0L else UNSAFE.allocateMemory(length), length)
        }

        @JvmStatic
        fun calloc(length: Long): MemoryPointer {
            val pointer = malloc(length)
            if (length != 0L) UNSAFE.setMemory(pointer.address, length, 0)
            return pointer
        }
    }
}

internal class WrappedPointer(address: Long, length: Long) : MemoryPointer {
    override var address = address
        set(_) {
            throw UnsupportedOperationException("Cannot change address of a wrapped pointer")
        }
    override var length = length
        set(_) {
            throw UnsupportedOperationException("Cannot change length of a wrapped pointer")
        }

    override fun reallocate(newLength: Long) {
        throw UnsupportedOperationException("Cannot reallocate a wrapped pointer")
    }

    override fun free() {
        throw UnsupportedOperationException("Cannot free a wrapped pointer")
    }
}

internal class MemoryPointerImpl(address: Long, length: Long) : MemoryPointer {
    override var address = address
        set(value) {
            require(value >= 0) { "Address must be positive or zero" }
            field = value
        }
    override var length = length
        set(value) {
            require(value >= 0) { "Length must be positive or zero" }
            field = value
        }
}

inline fun MemoryPointer.forEachByteUnsafe(
    byteOffset: Long = 0L,
    length: Int = this.length.toInt(),
    block: (Byte) -> Unit
) {
    for (i in 0 until length) {
        block(getByte(byteOffset + i))
    }
}

inline fun MemoryPointer.forEachByteIndexedUnsafe(
    byteOffset: Long = 0L,
    length: Int = this.length.toInt(),
    block: (Int, Byte) -> Unit
) {
    for (i in 0 until length) {
        block(i, getByte(byteOffset + i))
    }
}

inline fun MemoryPointer.forEachShortUnsafe(
    byteOffset: Long = 0L,
    length: Int = (this.length / 2L).toInt(),
    block: (Short) -> Unit
) {
    for (i in 0 until length) {
        block(getShort(byteOffset + i * 2L))
    }
}

inline fun MemoryPointer.forEachShortIndexedUnsafe(
    byteOffset: Long = 0L,
    length: Int = (this.length / 2L).toInt(),
    block: (Int, Short) -> Unit
) {
    for (i in 0 until length) {
        block(i, getShort(byteOffset + i * 2L))
    }
}

inline fun MemoryPointer.forEachIntUnsafe(
    byteOffset: Long = 0L,
    length: Int = (this.length / 4L).toInt(),
    block: (Int) -> Unit
) {
    for (i in 0 until length) {
        block(getInt(byteOffset + i * 4L))
    }
}

inline fun MemoryPointer.forEachIntIndexedUnsafe(
    byteOffset: Long = 0L,
    length: Int = (this.length / 4L).toInt(),
    block: (Int, Int) -> Unit
) {
    for (i in 0 until length) {
        block(i, getInt(byteOffset + i * 4L))
    }
}

inline fun MemoryPointer.forEachLongUnsafe(
    byteOffset: Long = 0L,
    length: Int = (this.length / 8L).toInt(),
    block: (Long) -> Unit
) {
    for (i in 0 until length) {
        block(getLong(byteOffset + i * 8L))
    }
}

inline fun MemoryPointer.forEachLongIndexedUnsafe(
    byteOffset: Long = 0L,
    length: Int = (this.length / 8L).toInt(),
    block: (Int, Long) -> Unit
) {
    for (i in 0 until length) {
        block(i, getLong(byteOffset + i * 8L))
    }
}

inline fun MemoryPointer.forEachFloatUnsafe(
    byteOffset: Long = 0L,
    length: Int = (this.length / 4L).toInt(),
    block: (Float) -> Unit
) {
    for (i in 0 until length) {
        block(getFloat(byteOffset + i * 4L))
    }
}

inline fun MemoryPointer.forEachFloatIndexedUnsafe(
    byteOffset: Long = 0L,
    length: Int = (this.length / 4L).toInt(),
    block: (Int, Float) -> Unit
) {
    for (i in 0 until length) {
        block(i, getFloat(byteOffset + i * 4L))
    }
}

inline fun MemoryPointer.forEachDoubleUnsafe(
    byteOffset: Long = 0L,
    length: Int = (this.length / 8L).toInt(),
    block: (Double) -> Unit
) {
    for (i in 0 until length) {
        block(getDouble(byteOffset + i * 8L))
    }
}

inline fun MemoryPointer.forEachDoubleIndexedUnsafe(
    byteOffset: Long = 0L,
    length: Int = (this.length / 8L).toInt(),
    block: (Int, Double) -> Unit
) {
    for (i in 0 until length) {
        block(i, getDouble(byteOffset + i * 8L))
    }
}


inline fun MemoryPointer.forEachByte(
    byteOffset: Long = 0L,
    length: Int = this.length.toInt(),
    block: (Byte) -> Unit
) {
    checkForeachIndexRange(byteOffset, length , 1)
    forEachByteUnsafe(byteOffset, length, block)
}

inline fun MemoryPointer.forEachByteIndexed(
    byteOffset: Long = 0L,
    length: Int = this.length.toInt(),
    block: (Int, Byte) -> Unit
) {
    checkForeachIndexRange(byteOffset, length , 1)
    forEachByteIndexedUnsafe(byteOffset, length, block)
}

inline fun MemoryPointer.forEachShort(
    byteOffset: Long = 0L,
    length: Int = (this.length / 2L).toInt(),
    block: (Short) -> Unit
) {
    checkForeachIndexRange(byteOffset, length , 2)
    forEachShortUnsafe(byteOffset, length, block)
}

inline fun MemoryPointer.forEachShortIndexed(
    byteOffset: Long = 0L,
    length: Int = (this.length / 2L).toInt(),
    block: (Int, Short) -> Unit
) {
    checkForeachIndexRange(byteOffset, length , 2)
    forEachShortIndexedUnsafe(byteOffset, length, block)
}

inline fun MemoryPointer.forEachInt(
    byteOffset: Long = 0L,
    length: Int = (this.length / 4L).toInt(),
    block: (Int) -> Unit
) {
    checkForeachIndexRange(byteOffset, length , 4)
    forEachIntUnsafe(byteOffset, length, block)
}

inline fun MemoryPointer.forEachIntIndexed(
    byteOffset: Long = 0L,
    length: Int = (this.length / 4L).toInt(),
    block: (Int, Int) -> Unit
) {
    checkForeachIndexRange(byteOffset, length , 4)
    forEachIntIndexedUnsafe(byteOffset, length, block)
}

inline fun MemoryPointer.forEachLong(
    byteOffset: Long = 0L,
    length: Int = (this.length / 8L).toInt(),
    block: (Long) -> Unit
) {
    checkForeachIndexRange(byteOffset, length , 8)
    forEachLongUnsafe(byteOffset, length, block)
}

inline fun MemoryPointer.forEachLongIndexed(
    byteOffset: Long = 0L,
    length: Int = (this.length / 8L).toInt(),
    block: (Int, Long) -> Unit
) {
    checkForeachIndexRange(byteOffset, length , 8)
    forEachLongIndexedUnsafe(byteOffset, length, block)
}

inline fun MemoryPointer.forEachFloat(
    byteOffset: Long = 0L,
    length: Int = (this.length / 4L).toInt(),
    block: (Float) -> Unit
) {
    checkForeachIndexRange(byteOffset, length , 4)
    forEachFloatUnsafe(byteOffset, length, block)
}

inline fun MemoryPointer.forEachFloatIndexed(
    byteOffset: Long = 0L,
    length: Int = (this.length / 4L).toInt(),
    block: (Int, Float) -> Unit
) {
    checkForeachIndexRange(byteOffset, length , 4)
    forEachFloatIndexedUnsafe(byteOffset, length, block)
}

inline fun MemoryPointer.forEachDouble(
    byteOffset: Long = 0L,
    length: Int = (this.length / 8L).toInt(),
    block: (Double) -> Unit
) {
    checkForeachIndexRange(byteOffset, length , 8)
    forEachDoubleUnsafe(byteOffset, length, block)
}

inline fun MemoryPointer.forEachDoubleIndexed(
    byteOffset: Long = 0L,
    length: Int = (this.length / 8L).toInt(),
    block: (Int, Double) -> Unit
) {
    checkForeachIndexRange(byteOffset, length , 8)
    forEachDoubleIndexedUnsafe(byteOffset, length, block)
}


fun MemoryPointer.checkForeachIndexRange(byteOffset: Long, length: Int, unitSize: Int) {
    if (byteOffset < 0L || byteOffset > this.length) {
        throw IndexOutOfBoundsException("offset $byteOffset is out of bounds for length ${this.length}")
    }
    if (length < 0 || byteOffset + length * unitSize > this.length) {
        throw IndexOutOfBoundsException("length $length is out of bounds for offset ${this.length}")
    }
}