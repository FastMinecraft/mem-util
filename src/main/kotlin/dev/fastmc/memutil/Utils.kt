package dev.fastmc.memutil

import sun.misc.Unsafe

internal val UNSAFE = run {
    val field = Unsafe::class.java.getDeclaredField("theUnsafe")
    field.isAccessible = true
    field.get(null) as Unsafe
}

internal val BYTE_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(ByteArray::class.java)
internal val SHORT_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(ShortArray::class.java)
internal val INT_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(IntArray::class.java)
internal val LONG_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(LongArray::class.java)
internal val FLOAT_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(FloatArray::class.java)
internal val DOUBLE_ARRAY_OFFSET = UNSAFE.arrayBaseOffset(DoubleArray::class.java)