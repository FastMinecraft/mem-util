package dev.fastmc.memutil

import java.util.*

class MemoryStack private constructor(initCapacity: Long): AutoCloseable {
    private val base = MemoryPointer.malloc(initCapacity)
    private var baseOffset = 0L

    private val framesPool = ArrayDeque<Frame>()
    private val frameStack = ArrayDeque<Frame>()

    private fun newFrame(): Frame {
        return framesPool.pollLast() ?: Frame(WrappedMemoryArray(base))
    }

    private fun freeFrame(frame: Frame) {
        framesPool.offerLast(frame)
    }

    fun malloc(size: Long): Frame {
        val newOffset = baseOffset + size
        base.ensureCapacity(newOffset)
        val frame = newFrame()
        frameStack.offerLast(frame)
        frame.delegated.offset = baseOffset
        frame.delegated.length = size
        baseOffset = newOffset
        return frame
    }

    fun calloc(size: Long): Frame {
        val frame = malloc(size)
        UNSAFE.setMemory(frame.address, size, 0)
        return frame
    }

    fun init() {
        require(frameStack.isEmpty()) { "Memory stack is not empty" }
        assert(baseOffset == 0L)
    }

    override fun close() {
        var frame = frameStack.pollLast()
        while (frame != null) {
            freeFrame(frame)
            baseOffset -= frame.length
            frame = frameStack.pollLast()
        }
        assert(baseOffset == 0L)
    }

    inner class Frame internal constructor(internal val delegated: WrappedMemoryArray) : MemoryArray by delegated, AutoCloseable {
        override fun close() {
            check(frameStack.pollLast() === this) { "Frame stack is corrupted" }
            baseOffset -= length
            freeFrame(this)
        }
    }

    companion object {
        private val threadLocal = ThreadLocal.withInitial { MemoryStack(1024L * 1024L) }

        fun get(): MemoryStack {
            return threadLocal.get()
        }

        inline operator fun <T> invoke(crossinline block: MemoryStack.() -> T): T {
            return get().use {
                it.init()
                block(it)
            }
        }
    }
}