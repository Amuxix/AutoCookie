package cookieclicker

trait Saveable {
  def save: String
  def load(string: String): Unit
}
