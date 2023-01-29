package dev.fastmc.memutil

import org.joml.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.Random

class JomlUtilTest {
    @MethodSource("vec2iSrc")
    @ParameterizedTest
    fun testFromPointer(input: Vector2i) {
        val buffer = TestUtils.allocDirectBuffer(8)
        val pointer = MemoryPointer.wrap(buffer)
        input.get(buffer)
        val output = Vector2i()
        output.fromPointer(pointer)

        assert(input == output)
    }


    @MethodSource("vec2iSrc")
    @ParameterizedTest
    fun testToPointer(input: Vector2i) {
        val buffer = TestUtils.allocDirectBuffer(8)
        val pointer = MemoryPointer.malloc(8)

        input.get(buffer)
        input.toPointer(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
    }

    @MethodSource("vec2iSrc")
    @ParameterizedTest
    fun testToArray(input: Vector2i) {
        val buffer = TestUtils.allocDirectBuffer(8)
        val pointer = MemoryArray.malloc(8)

        input.get(buffer)
        input.toArray(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
    }

    @MethodSource("vec3iSrc")
    @ParameterizedTest
    fun testFromPointer(input: Vector3i) {
        val buffer = TestUtils.allocDirectBuffer(12)
        val pointer = MemoryPointer.wrap(buffer)
        input.get(buffer)
        val output = Vector3i()
        output.fromPointer(pointer)

        assert(input == output)
    }

    @MethodSource("vec3iSrc")
    @ParameterizedTest
    fun testToPointer(input: Vector3i) {
        val buffer = TestUtils.allocDirectBuffer(12)
        val pointer = MemoryPointer.malloc(12)

        input.get(buffer)
        input.toPointer(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
        assert(buffer.getInt(8) == pointer.getInt(8))
    }

    @MethodSource("vec3iSrc")
    @ParameterizedTest
    fun testToArray(input: Vector3i) {
        val buffer = TestUtils.allocDirectBuffer(12)
        val pointer = MemoryArray.malloc(12)

        input.get(buffer)
        input.toArray(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
        assert(buffer.getInt(8) == pointer.getInt(8))
    }

    @MethodSource("vec4iSrc")
    @ParameterizedTest
    fun testFromPointer(input: Vector4i) {
        val buffer = TestUtils.allocDirectBuffer(16)
        val pointer = MemoryPointer.wrap(buffer)
        input.get(buffer)
        val output = Vector4i()
        output.fromPointer(pointer)

        assert(input == output)
    }

    @MethodSource("vec4iSrc")
    @ParameterizedTest
    fun testToPointer(input: Vector4i) {
        val buffer = TestUtils.allocDirectBuffer(16)
        val pointer = MemoryPointer.malloc(16)

        input.get(buffer)
        input.toPointer(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
        assert(buffer.getInt(8) == pointer.getInt(8))
        assert(buffer.getInt(12) == pointer.getInt(12))
    }

    @MethodSource("vec4iSrc")
    @ParameterizedTest
    fun testToArray(input: Vector4i) {
        val buffer = TestUtils.allocDirectBuffer(16)
        val pointer = MemoryArray.malloc(16)

        input.get(buffer)
        input.toArray(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
        assert(buffer.getInt(8) == pointer.getInt(8))
        assert(buffer.getInt(12) == pointer.getInt(12))
    }

    @MethodSource("vec2fSrc")
    @ParameterizedTest
    fun testFromPointer(input: Vector2f) {
        val buffer = TestUtils.allocDirectBuffer(8)
        val pointer = MemoryPointer.wrap(buffer)
        input.get(buffer)
        val output = Vector2f()
        output.fromPointer(pointer)

        assert(input == output)
    }

    @MethodSource("vec2fSrc")
    @ParameterizedTest
    fun testToPointer(input: Vector2f) {
        val buffer = TestUtils.allocDirectBuffer(8)
        val pointer = MemoryPointer.malloc(8)

        input.get(buffer)
        input.toPointer(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
    }

    @MethodSource("vec2fSrc")
    @ParameterizedTest
    fun testToArray(input: Vector2f) {
        val buffer = TestUtils.allocDirectBuffer(8)
        val pointer = MemoryArray.malloc(8)

        input.get(buffer)
        input.toArray(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
    }

    @MethodSource("vec3fSrc")
    @ParameterizedTest
    fun testFromPointer(input: Vector3f) {
        val buffer = TestUtils.allocDirectBuffer(12)
        val pointer = MemoryPointer.wrap(buffer)
        input.get(buffer)
        val output = Vector3f()
        output.fromPointer(pointer)

        assert(input == output)
    }

    @MethodSource("vec3fSrc")
    @ParameterizedTest
    fun testToPointer(input: Vector3f) {
        val buffer = TestUtils.allocDirectBuffer(12)
        val pointer = MemoryPointer.malloc(12)

        input.get(buffer)
        input.toPointer(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
        assert(buffer.getInt(8) == pointer.getInt(8))
    }

    @MethodSource("vec3fSrc")
    @ParameterizedTest
    fun testToArray(input: Vector3f) {
        val buffer = TestUtils.allocDirectBuffer(12)
        val pointer = MemoryArray.malloc(12)

        input.get(buffer)
        input.toArray(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
        assert(buffer.getInt(8) == pointer.getInt(8))
    }

    @MethodSource("vec4fSrc")
    @ParameterizedTest
    fun testFromPointer(input: Vector4f) {
        val buffer = TestUtils.allocDirectBuffer(16)
        val pointer = MemoryPointer.wrap(buffer)
        input.get(buffer)
        val output = Vector4f()
        output.fromPointer(pointer)

        assert(input == output)
    }

    @MethodSource("vec4fSrc")
    @ParameterizedTest
    fun testToPointer(input: Vector4f) {
        val buffer = TestUtils.allocDirectBuffer(16)
        val pointer = MemoryPointer.malloc(16)

        input.get(buffer)
        input.toPointer(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
        assert(buffer.getInt(8) == pointer.getInt(8))
        assert(buffer.getInt(12) == pointer.getInt(12))
    }

    @MethodSource("vec4fSrc")
    @ParameterizedTest
    fun testToArray(input: Vector4f) {
        val buffer = TestUtils.allocDirectBuffer(16)
        val pointer = MemoryArray.malloc(16)

        input.get(buffer)
        input.toArray(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
        assert(buffer.getInt(8) == pointer.getInt(8))
        assert(buffer.getInt(12) == pointer.getInt(12))
    }

    @MethodSource("mat2fSrc")
    @ParameterizedTest
    fun testFromPointer(input: Matrix2f) {
        val buffer = TestUtils.allocDirectBuffer(16)
        val pointer = MemoryPointer.wrap(buffer)
        input.get(buffer)
        val output = Matrix2f()
        output.fromPointer(pointer)

        assert(input == output)
    }

    @MethodSource("mat2fSrc")
    @ParameterizedTest
    fun testToPointer(input: Matrix2f) {
        val buffer = TestUtils.allocDirectBuffer(16)
        val pointer = MemoryPointer.malloc(16)

        input.get(buffer)
        input.toPointer(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
        assert(buffer.getInt(8) == pointer.getInt(8))
        assert(buffer.getInt(12) == pointer.getInt(12))
    }

    @MethodSource("mat2fSrc")
    @ParameterizedTest
    fun testToArray(input: Matrix2f) {
        val buffer = TestUtils.allocDirectBuffer(16)
        val pointer = MemoryArray.malloc(16)

        input.get(buffer)
        input.toArray(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
        assert(buffer.getInt(8) == pointer.getInt(8))
        assert(buffer.getInt(12) == pointer.getInt(12))
    }

    @MethodSource("mat3fSrc")
    @ParameterizedTest
    fun testFromPointer(input: Matrix3f) {
        val buffer = TestUtils.allocDirectBuffer(36)
        val pointer = MemoryPointer.wrap(buffer)
        input.get(buffer)
        val output = Matrix3f()
        output.fromPointer(pointer)

        assert(input == output)
    }

    @MethodSource("mat3fSrc")
    @ParameterizedTest
    fun testToPointer(input: Matrix3f) {
        val buffer = TestUtils.allocDirectBuffer(36)
        val pointer = MemoryPointer.malloc(36)

        input.get(buffer)
        input.toPointer(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
        assert(buffer.getInt(8) == pointer.getInt(8))
        assert(buffer.getInt(12) == pointer.getInt(12))
        assert(buffer.getInt(16) == pointer.getInt(16))
        assert(buffer.getInt(20) == pointer.getInt(20))
        assert(buffer.getInt(24) == pointer.getInt(24))
        assert(buffer.getInt(28) == pointer.getInt(28))
        assert(buffer.getInt(32) == pointer.getInt(32))
    }

    @MethodSource("mat3fSrc")
    @ParameterizedTest
    fun testToArray(input: Matrix3f) {
        val buffer = TestUtils.allocDirectBuffer(36)
        val pointer = MemoryArray.malloc(36)

        input.get(buffer)
        input.toArray(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
        assert(buffer.getInt(8) == pointer.getInt(8))
        assert(buffer.getInt(12) == pointer.getInt(12))
        assert(buffer.getInt(16) == pointer.getInt(16))
        assert(buffer.getInt(20) == pointer.getInt(20))
        assert(buffer.getInt(24) == pointer.getInt(24))
        assert(buffer.getInt(28) == pointer.getInt(28))
        assert(buffer.getInt(32) == pointer.getInt(32))
    }

    @MethodSource("mat4fSrc")
    @ParameterizedTest
    fun testFromPointer(input: Matrix4f) {
        val buffer = TestUtils.allocDirectBuffer(64)
        val pointer = MemoryPointer.wrap(buffer)
        input.get(buffer)
        val output = Matrix4f()
        output.fromPointer(pointer)

        assert(input == output)
    }

    @MethodSource("mat4fSrc")
    @ParameterizedTest
    fun testToPointer(input: Matrix4f) {
        val buffer = TestUtils.allocDirectBuffer(64)
        val pointer = MemoryPointer.malloc(64)

        input.get(buffer)
        input.toPointer(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
        assert(buffer.getInt(8) == pointer.getInt(8))
        assert(buffer.getInt(12) == pointer.getInt(12))
        assert(buffer.getInt(16) == pointer.getInt(16))
        assert(buffer.getInt(20) == pointer.getInt(20))
        assert(buffer.getInt(24) == pointer.getInt(24))
        assert(buffer.getInt(28) == pointer.getInt(28))
        assert(buffer.getInt(32) == pointer.getInt(32))
        assert(buffer.getInt(36) == pointer.getInt(36))
        assert(buffer.getInt(40) == pointer.getInt(40))
        assert(buffer.getInt(44) == pointer.getInt(44))
        assert(buffer.getInt(48) == pointer.getInt(48))
        assert(buffer.getInt(52) == pointer.getInt(52))
        assert(buffer.getInt(56) == pointer.getInt(56))
        assert(buffer.getInt(60) == pointer.getInt(60))
    }

    @MethodSource("mat4fSrc")
    @ParameterizedTest
    fun testToArray(input: Matrix4f) {
        val buffer = TestUtils.allocDirectBuffer(64)
        val pointer = MemoryArray.malloc(64)

        input.get(buffer)
        input.toArray(pointer)

        assert(buffer.getInt(0) == pointer.getInt(0))
        assert(buffer.getInt(4) == pointer.getInt(4))
        assert(buffer.getInt(8) == pointer.getInt(8))
        assert(buffer.getInt(12) == pointer.getInt(12))
        assert(buffer.getInt(16) == pointer.getInt(16))
        assert(buffer.getInt(20) == pointer.getInt(20))
        assert(buffer.getInt(24) == pointer.getInt(24))
        assert(buffer.getInt(28) == pointer.getInt(28))
        assert(buffer.getInt(32) == pointer.getInt(32))
        assert(buffer.getInt(36) == pointer.getInt(36))
        assert(buffer.getInt(40) == pointer.getInt(40))
        assert(buffer.getInt(44) == pointer.getInt(44))
        assert(buffer.getInt(48) == pointer.getInt(48))
        assert(buffer.getInt(52) == pointer.getInt(52))
        assert(buffer.getInt(56) == pointer.getInt(56))
        assert(buffer.getInt(60) == pointer.getInt(60))
    }

    companion object {
        private const val TEST_DATA_SIZE = 10
        private const val RANDOM_SEED = -3295345234810232135L

        @JvmStatic
        @BeforeAll
        fun init() {
            System.setProperty("joml.format", "false")
        }

        @JvmStatic
        fun <R> randomList(i: Int, generator: (Random) -> R): List<R> {
            val random = Random(RANDOM_SEED * i)
            return List(TEST_DATA_SIZE) { generator(random) }
        }

        @JvmStatic
        fun vec2iSrc() = listOf(
            Vector2i(0, 0),
            Vector2i(0, 1),
            Vector2i(-1, 0),
            Vector2i(-1, 1),
            Vector2i(Int.MAX_VALUE, Int.MIN_VALUE)
        ) + randomList(1) { Vector2i(it.nextInt(), it.nextInt()) }

        @JvmStatic
        fun vec3iSrc() = listOf(
            Vector3i(0, 0, 0),
            Vector3i(-1, 1, 0),
            Vector3i(-1, 0, 1),
            Vector3i(1, -1, 0),
            Vector3i(Int.MAX_VALUE, Int.MIN_VALUE, -1),
            Vector3i(Int.MIN_VALUE, Int.MIN_VALUE, Int.MAX_VALUE)
        ) + randomList(2) { Vector3i(it.nextInt(), it.nextInt(), it.nextInt()) }

        @JvmStatic
        fun vec4iSrc() = listOf(
            Vector4i(0, 0, 0, 0),
            Vector4i(-1, 1, 0, 1),
            Vector4i(-1, 0, 1, 0),
            Vector4i(1, -1, 0, -1),
            Vector4i(Int.MAX_VALUE, Int.MIN_VALUE, -1, Int.MIN_VALUE),
            Vector4i(Int.MIN_VALUE, -1, Int.MAX_VALUE, Int.MAX_VALUE)
        ) + randomList(3) { Vector4i(it.nextInt(), it.nextInt(), it.nextInt(), it.nextInt()) }

        @JvmStatic
        fun vec2fSrc() = listOf(
            Vector2f(0f, 0f),
            Vector2f(0f, 1f),
            Vector2f(-1f, 0f),
            Vector2f(-1f, 1f),
            Vector2f(Float.NaN, Float.NEGATIVE_INFINITY),
            Vector2f(Float.POSITIVE_INFINITY, Float.MAX_VALUE),
            Vector2f(Float.MAX_VALUE, Float.MIN_VALUE)
        ) + randomList(4) { Vector2f(it.nextFloat(), it.nextFloat()) }

        @JvmStatic
        fun vec3fSrc() = listOf(
            Vector3f(0f, 0f, 0f),
            Vector3f(-1f, 1f, 0f),
            Vector3f(-1f, 0f, 1f),
            Vector3f(1f, -1f, 0f),
            Vector3f(Float.NaN, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY),
            Vector3f(Float.MAX_VALUE, Float.MIN_VALUE, Float.MAX_VALUE),
            Vector3f(Float.MIN_VALUE, Float.MAX_VALUE, Float.MIN_VALUE)
        ) + randomList(5) { Vector3f(it.nextFloat(), it.nextFloat(), it.nextFloat()) }

        @JvmStatic
        fun vec4fSrc() = listOf(
            Vector4f(0f, 0f, 0f, 0f),
            Vector4f(-1f, 1f, 0f, 1f),
            Vector4f(-1f, 0f, 1f, 0f),
            Vector4f(1f, -1f, 0f, -1f),
            Vector4f(Float.NaN, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, Float.MAX_VALUE),
            Vector4f(Float.MAX_VALUE, Float.NaN, Float.MAX_VALUE, Float.POSITIVE_INFINITY),
            Vector4f(Float.NEGATIVE_INFINITY, Float.MAX_VALUE, Float.NaN, Float.MAX_VALUE)
        ) + randomList(6) { Vector4f(it.nextFloat(), it.nextFloat(), it.nextFloat(), it.nextFloat()) }

        @JvmStatic
        fun mat2fSrc() = listOf(
            Matrix2f().identity(),
            Matrix2f().rotate(-1.0f),
            Matrix2f().rotate(1.0f),
            Matrix2f(Float.NaN, Float.MAX_VALUE, Float.MIN_VALUE, Float.NEGATIVE_INFINITY),
            Matrix2f(Float.POSITIVE_INFINITY, Float.MAX_VALUE, Float.MIN_VALUE, Float.NEGATIVE_INFINITY)
        ) + randomList(7) { Matrix2f(it.nextFloat(), it.nextFloat(), it.nextFloat(), it.nextFloat()) }

        @JvmStatic
        fun mat3fSrc() = listOf(
            Matrix3f().identity(),
            Matrix3f().rotate(1.0f, -1.0f, 0.0f, 1.0f),
            Matrix3f().rotate(-1.0f, 1.0f, 0.0f, -1.0f),
            Matrix3f(
                Float.NaN,
                Float.MAX_VALUE,
                Float.MIN_VALUE,
                Float.NEGATIVE_INFINITY,
                Float.POSITIVE_INFINITY,
                Float.MAX_VALUE,
                Float.MIN_VALUE,
                Float.NaN,
                Float.POSITIVE_INFINITY
            ),
        ) + randomList(8) {
            Matrix3f(
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat()
            )
        }

        @JvmStatic
        fun mat4fSrc() = listOf(
            Matrix4f().identity(),
            Matrix4f().rotate(1.0f, -1.0f, 0.0f, 1.0f),
            Matrix4f().rotate(-1.0f, 1.0f, 0.0f, -1.0f),
            Matrix4f().translate(1.0f, -1.0f, 0.0f),
            Matrix4f().translate(-1.0f, 1.0f, 1.0f),
            Matrix4f().translate(1.0f, -1.0f, 0.0f).rotate(1.0f, -1.0f, 0.0f, 1.0f),
            Matrix4f().translate(-1.0f, 1.0f, 1.0f).rotate(-1.0f, 1.0f, 0.0f, -1.0f),
            Matrix4f(
                Float.NaN,
                Float.MAX_VALUE,
                Float.MIN_VALUE,
                Float.NEGATIVE_INFINITY,
                Float.POSITIVE_INFINITY,
                Float.MAX_VALUE,
                Float.MIN_VALUE,
                Float.NaN,
                Float.POSITIVE_INFINITY,
                Float.MAX_VALUE,
                Float.MIN_VALUE,
                Float.NEGATIVE_INFINITY,
                Float.POSITIVE_INFINITY,
                Float.MAX_VALUE,
                Float.MIN_VALUE,
                Float.NaN
            ),
        ) + randomList(9) {
            Matrix4f(
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat(),
                it.nextFloat()
            )
        }
    }
}