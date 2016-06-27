package im.actor.server.mtproto.codecs.primitive

import im.actor.server.mtproto.codecs._
import scodec._
import scodec.bits._

/**
 * Protobuf-compatible boolean Codec
 */
object BooleanCodec extends Codec[Boolean] {

  def sizeBound = SizeBound.unknown

  def encode(b: Boolean) =
    Attempt.successful(if (b) BitVector(1) else BitVector(0))

  def decode(buf: BitVector) = {
    if (!buf.isEmpty) Attempt.successful(DecodeResult(buf.getByte(0) != 0, buf.drop(byteSize)))
    else Attempt.failure(Err("empty buf"))
  }
}
