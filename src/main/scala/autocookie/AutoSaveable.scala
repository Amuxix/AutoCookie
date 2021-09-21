package autocookie

import autocookie.Logger.error
import cookieclicker.Saveable
import cookieclicker.global.{decode, encode}

trait AutoSaveable extends Saveable {
  val version: Float

  def autoSave: String
  def autoLoad(string: String, version: Float): Unit

  lazy val name = getClass.getSimpleName.split("\\$").last

  override def save: String = encode(Seq(
    version,
    autoSave,
    name + "End",
  ).mkString("|"))

  override def load(string: String): Unit =
    val split = decode(string).split("\\|")
    split.headOption.flatMap(_.toFloatOption).fold(error("Failed to load: Could not find version")) {
      case version if version > this.version => error("Trying to load code from future version")
      case version => autoLoad(split.slice(1, split.size - 1).mkString("|"), version) //Join again with same seperator
    }
}
