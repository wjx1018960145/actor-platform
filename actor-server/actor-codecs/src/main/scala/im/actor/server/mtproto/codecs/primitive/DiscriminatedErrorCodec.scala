package im.actor.server.mtproto.codecs.primitive

import scodec._
import scodec.bits.BitVector

/**
 * Error Codec
 *
 * @param codecName name of source codec
 * @tparam T type of result
 */
class DiscriminatedErrorCodec[T](codecName: String) extends Codec[T] {
  def sizeBound = SizeBound.unknown

  def encode(a: T) = Attempt.failure(Err(s"$codecName.header is unknown for ${a.getClass.getCanonicalName}"))

  def decode(buf: BitVector) = Attempt.failure(Err(s"$codecName.header is unknown. Body: ${buf.toHex}"))
}

object DiscriminatedErrorCodec {
  def apply[T](codecName: String) = new DiscriminatedErrorCodec[T](codecName)
}
