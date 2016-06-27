package im.actor.server.mtproto.codecs.primitive

import im.actor.server.mtproto.codecs._
import scodec._
import scodec.bits._

/**
 * Protobuf-compatible UTF-8 string codec
 */
object StringCodec extends Codec[String] {
  def sizeBound = SizeBound.unknown

  def encode(str: String) = {
    val strBytes = str.getBytes
    for {
      length ← varint.encode(strBytes.length.toLong)
    } yield length ++ BitVector(strBytes)
  }

  def decode(buf: BitVector) = {
    for { t ← varint.decode(buf) } yield {
      val length = t.value * byteSize
      DecodeResult(new String(t.remainder.take(length).toByteArray, "UTF-8"), t.remainder.drop(length))
    }
  }
}
