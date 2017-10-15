package com.xiangjuncheng.kotlinbilibili.media.damuku

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.zip.DataFormatException
import java.util.zip.Deflater
import java.util.zip.Inflater

/**
 * Created by xjc on 2017/10/15.
 */
object BiliDanmukuCompressionTools {

    @JvmOverloads fun compress(value: ByteArray, offset: Int = 0, length: Int = value.size, compressionLevel: Int = Deflater.BEST_COMPRESSION): ByteArray {
        val bos = ByteArrayOutputStream(length)
        val compressor = Deflater()
        try {
            // 将当前压缩级别设置为指定值
            compressor.setLevel(compressionLevel)
            compressor.setInput(value, offset, length)
            // 调用时，指示压缩应当以输入缓冲区的当前内容结尾
            compressor.finish()
            val buf = ByteArray(1024)
            while (!compressor.finished()) {
                // 如果已到达压缩数据输出流的结尾，则返回 true。
                val count = compressor.deflate(buf)
                // 使用压缩数据填充指定缓冲区。
                bos.write(buf, 0, count)
            }
        } finally {
            // 关闭解压缩器并放弃所有未处理的输入
            compressor.end()
        }
        return bos.toByteArray()
    }

    @Throws(DataFormatException::class)
    fun decompress(value: ByteArray): ByteArray {
        val bos = ByteArrayOutputStream(value.size)
        val decompressor = Inflater()
        try {
            decompressor.setInput(value)
            val buf = ByteArray(1024)
            while (!decompressor.finished()) {
                val count = decompressor.inflate(buf)
                bos.write(buf, 0, count)
            }
        } finally {
            decompressor.end()
        }
        return bos.toByteArray()
    }

    @Throws(DataFormatException::class)
    internal fun decompressXML(data: ByteArray): ByteArray {
        var data = data
        val dest = ByteArray(data.size + 2)
        System.arraycopy(data, 0, dest, 2, data.size)
        dest[0] = 0x78
        dest[1] = 0x01
        data = dest
        val decompresser = Inflater()
        decompresser.setInput(data)
        val bufferArray = ByteArray(1024)
        val baos = ByteArrayOutputStream(1024)
        try {
            var i = 1
            while (i != 0) {
                i = decompresser.inflate(bufferArray)
                baos.write(bufferArray, 0, i)
            }
            data = baos.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                baos.flush()
                baos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        decompresser.end()
        return data
    }
}// 最佳压缩的压缩级别