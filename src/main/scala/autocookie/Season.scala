package autocookie

enum Season:
  case Easter, BusinessDay, Valentines, Halloween, Christmas

object Season:
  def fromValue(value: String): Option[Season] = value match
    case "" => None
    case "easter" => Some(Season.Easter)
    case "fools" => Some(Season.BusinessDay)
    case "valentines" => Some(Season.Valentines)
    case "halloween" => Some(Season.Halloween)
    case "christmas" => Some(Season.Christmas)
    case _ => throw new IllegalArgumentException(s"Could not find season for $value")

