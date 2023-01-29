@file:JvmName("JomlUtil")
package dev.fastmc.memutil

import org.joml.*

private fun getOffset(clazz: Class<*>, name: String): Long {
    return UNSAFE.objectFieldOffset(clazz.getDeclaredField(name))
}

private val VECTOR2I_OFFSET = getOffset(Vector2i::class.java, "x")
private val VECTOR3I_OFFSET = getOffset(Vector3i::class.java, "x")
private val VECTOR4I_OFFSET = getOffset(Vector4i::class.java, "x")

private val VECTOR2F_OFFSET = getOffset(Vector2f::class.java, "x")
private val VECTOR3F_OFFSET = getOffset(Vector3f::class.java, "x")
private val VECTOR4F_OFFSET = getOffset(Vector4f::class.java, "x")

private val MATRIX2F_OFFSET = getOffset(Matrix2f::class.java, "m00")
private val MATRIX3F_OFFSET = getOffset(Matrix3f::class.java, "m00")
private val MATRIX4F_OFFSET = getOffset(Matrix4f::class.java, "m00")

fun Vector2i.fromPointer(p: MemoryPointer, offset: Long = 0L) {
    UNSAFE.putLong(this, VECTOR2I_OFFSET, p.getLong(offset))
}

fun Vector2i.toPointer(p: MemoryPointer, offset: Long = 0L) {
    p.setLong(offset, UNSAFE.getLong(this, VECTOR2I_OFFSET))
}

fun Vector2i.toArray(a: MemoryArray) {
    a.pushLong(UNSAFE.getLong(this, VECTOR2I_OFFSET))
}

fun Vector3i.fromPointer(p: MemoryPointer, offset: Long = 0L) {
    UNSAFE.putLong(this, VECTOR3I_OFFSET, p.getLong(offset))
    UNSAFE.putInt(this, VECTOR3I_OFFSET + 8, p.getInt(offset + 8))
}

fun Vector3i.toPointer(p: MemoryPointer, offset: Long = 0L) {
    p.setLong(offset, UNSAFE.getLong(this, VECTOR3I_OFFSET))
    p.setInt(offset + 8, UNSAFE.getInt(this, VECTOR3I_OFFSET + 8))
}

fun Vector3i.toArray(a: MemoryArray) {
    a.pushLong(UNSAFE.getLong(this, VECTOR3I_OFFSET))
    a.pushInt(UNSAFE.getInt(this, VECTOR3I_OFFSET + 8))
}

fun Vector4i.fromPointer(p: MemoryPointer, offset: Long = 0L) {
    UNSAFE.putLong(this, VECTOR4I_OFFSET, p.getLong(offset))
    UNSAFE.putLong(this, VECTOR4I_OFFSET + 8, p.getLong(offset + 8))
}

fun Vector4i.toPointer(p: MemoryPointer, offset: Long = 0L) {
    p.setLong(offset, UNSAFE.getLong(this, VECTOR4I_OFFSET))
    p.setLong(offset + 8, UNSAFE.getLong(this, VECTOR4I_OFFSET + 8))
}

fun Vector4i.toArray(a: MemoryArray) {
    a.pushLong(UNSAFE.getLong(this, VECTOR4I_OFFSET))
    a.pushLong(UNSAFE.getLong(this, VECTOR4I_OFFSET + 8))
}

fun Vector2f.fromPointer(p: MemoryPointer, offset: Long = 0L) {
    UNSAFE.putLong(this, VECTOR2F_OFFSET, p.getLong(offset))
}

fun Vector2f.toPointer(p: MemoryPointer, offset: Long = 0L) {
    p.setLong(offset, UNSAFE.getLong(this, VECTOR2F_OFFSET))
}

fun Vector2f.toArray(a: MemoryArray) {
    a.pushLong(UNSAFE.getLong(this, VECTOR2F_OFFSET))
}

fun Vector3f.fromPointer(p: MemoryPointer, offset: Long = 0L) {
    UNSAFE.putLong(this, VECTOR3F_OFFSET, p.getLong(offset))
    UNSAFE.putInt(this, VECTOR3F_OFFSET + 8, p.getInt(offset + 8))
}

fun Vector3f.toPointer(p: MemoryPointer, offset: Long = 0L) {
    p.setLong(offset, UNSAFE.getLong(this, VECTOR3F_OFFSET))
    p.setInt(offset + 8, UNSAFE.getInt(this, VECTOR3F_OFFSET + 8))
}

fun Vector3f.toArray(a: MemoryArray) {
    a.pushLong(UNSAFE.getLong(this, VECTOR3F_OFFSET))
    a.pushInt(UNSAFE.getInt(this, VECTOR3F_OFFSET + 8))
}

fun Vector4f.fromPointer(p: MemoryPointer, offset: Long = 0L) {
    UNSAFE.putLong(this, VECTOR4F_OFFSET, p.getLong(offset))
    UNSAFE.putLong(this, VECTOR4F_OFFSET + 8, p.getLong(offset + 8))
}

fun Vector4f.toPointer(p: MemoryPointer, offset: Long = 0L) {
    p.setLong(offset, UNSAFE.getLong(this, VECTOR4F_OFFSET))
    p.setLong(offset + 8, UNSAFE.getLong(this, VECTOR4F_OFFSET + 8))
}

fun Vector4f.toArray(a: MemoryArray) {
    a.pushLong(UNSAFE.getLong(this, VECTOR4F_OFFSET))
    a.pushLong(UNSAFE.getLong(this, VECTOR4F_OFFSET + 8))
}

fun Matrix2f.fromPointer(p: MemoryPointer, offset: Long = 0L) {
    UNSAFE.putLong(this, MATRIX2F_OFFSET, p.getLong(offset))
    UNSAFE.putLong(this, MATRIX2F_OFFSET + 8, p.getLong(offset + 8))
}

fun Matrix2f.toPointer(p: MemoryPointer, offset: Long = 0L) {
    p.setLong(offset, UNSAFE.getLong(this, MATRIX2F_OFFSET))
    p.setLong(offset + 8, UNSAFE.getLong(this, MATRIX2F_OFFSET + 8))
}

fun Matrix2f.toArray(a: MemoryArray) {
    a.pushLong(UNSAFE.getLong(this, MATRIX2F_OFFSET))
    a.pushLong(UNSAFE.getLong(this, MATRIX2F_OFFSET + 8))
}

fun Matrix3f.fromPointer(p: MemoryPointer, offset: Long = 0L) {
    UNSAFE.putLong(this, MATRIX3F_OFFSET, p.getLong(offset))
    UNSAFE.putLong(this, MATRIX3F_OFFSET + 8, p.getLong(offset + 8))
    UNSAFE.putLong(this, MATRIX3F_OFFSET + 16, p.getLong(offset + 16))
    UNSAFE.putLong(this, MATRIX3F_OFFSET + 24, p.getLong(offset + 24))
    UNSAFE.putInt(this, MATRIX3F_OFFSET + 32, p.getInt(offset + 32))
}

fun Matrix3f.toPointer(p: MemoryPointer, offset: Long = 0L) {
    p.setLong(offset, UNSAFE.getLong(this, MATRIX3F_OFFSET))
    p.setLong(offset + 8, UNSAFE.getLong(this, MATRIX3F_OFFSET + 8))
    p.setLong(offset + 16, UNSAFE.getLong(this, MATRIX3F_OFFSET + 16))
    p.setLong(offset + 24, UNSAFE.getLong(this, MATRIX3F_OFFSET + 24))
    p.setInt(offset + 32, UNSAFE.getInt(this, MATRIX3F_OFFSET + 32))
}

fun Matrix3f.toArray(a: MemoryArray) {
    a.pushLong(UNSAFE.getLong(this, MATRIX3F_OFFSET))
    a.pushLong(UNSAFE.getLong(this, MATRIX3F_OFFSET + 8))
    a.pushLong(UNSAFE.getLong(this, MATRIX3F_OFFSET + 16))
    a.pushLong(UNSAFE.getLong(this, MATRIX3F_OFFSET + 24))
    a.pushInt(UNSAFE.getInt(this, MATRIX3F_OFFSET + 32))
}

fun Matrix4f.fromPointer(p: MemoryPointer, offset: Long = 0L) {
    UNSAFE.putLong(this, MATRIX4F_OFFSET, p.getLong(offset))
    UNSAFE.putLong(this, MATRIX4F_OFFSET + 8, p.getLong(offset + 8))
    UNSAFE.putLong(this, MATRIX4F_OFFSET + 16, p.getLong(offset + 16))
    UNSAFE.putLong(this, MATRIX4F_OFFSET + 24, p.getLong(offset + 24))
    UNSAFE.putLong(this, MATRIX4F_OFFSET + 32, p.getLong(offset + 32))
    UNSAFE.putLong(this, MATRIX4F_OFFSET + 40, p.getLong(offset + 40))
    UNSAFE.putLong(this, MATRIX4F_OFFSET + 48, p.getLong(offset + 48))
    UNSAFE.putLong(this, MATRIX4F_OFFSET + 56, p.getLong(offset + 56))
}

fun Matrix4f.toPointer(p: MemoryPointer, offset: Long = 0L) {
    p.setLong(offset, UNSAFE.getLong(this, MATRIX4F_OFFSET))
    p.setLong(offset + 8, UNSAFE.getLong(this, MATRIX4F_OFFSET + 8))
    p.setLong(offset + 16, UNSAFE.getLong(this, MATRIX4F_OFFSET + 16))
    p.setLong(offset + 24, UNSAFE.getLong(this, MATRIX4F_OFFSET + 24))
    p.setLong(offset + 32, UNSAFE.getLong(this, MATRIX4F_OFFSET + 32))
    p.setLong(offset + 40, UNSAFE.getLong(this, MATRIX4F_OFFSET + 40))
    p.setLong(offset + 48, UNSAFE.getLong(this, MATRIX4F_OFFSET + 48))
    p.setLong(offset + 56, UNSAFE.getLong(this, MATRIX4F_OFFSET + 56))
}

fun Matrix4f.toArray(a: MemoryArray) {
    a.pushLong(UNSAFE.getLong(this, MATRIX4F_OFFSET))
    a.pushLong(UNSAFE.getLong(this, MATRIX4F_OFFSET + 8))
    a.pushLong(UNSAFE.getLong(this, MATRIX4F_OFFSET + 16))
    a.pushLong(UNSAFE.getLong(this, MATRIX4F_OFFSET + 24))
    a.pushLong(UNSAFE.getLong(this, MATRIX4F_OFFSET + 32))
    a.pushLong(UNSAFE.getLong(this, MATRIX4F_OFFSET + 40))
    a.pushLong(UNSAFE.getLong(this, MATRIX4F_OFFSET + 48))
    a.pushLong(UNSAFE.getLong(this, MATRIX4F_OFFSET + 56))
}