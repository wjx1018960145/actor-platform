package im.actor.server.mtproto.codecs.primitive

import im.actor.server.mtproto.codecs._
import scodec._
import scodec.bits.BitVector

/**
 * Codec for wrapping result of underlying codec to a byte array for length prefixing
 *
 * @param codec source codec
 * @tparam A codec type
 */
class WrappedCodec[A](codec: Codec[A]) extends Codec[A] {
  def sizeBound = SizeBound.unknown

  def encode(v: A) = codec.encode(v).flatMap(bytes.encode)

  def decode(buf: BitVector) = {
    for {
      t ← bytes.decode(buf)
      res ← codec.decode(t.value)
    } yield DecodeResult(res.value, t.remainder)
  }
}

object WrappedCodec {
  def apply[A](codec: Codec[A]): Codec[A] = new WrappedCodec(codec)
}
