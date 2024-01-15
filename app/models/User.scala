package models

import play.api.libs.json.Json

case class User(
                 id: String = "ak001",
                 name: String = "Anish",
                 age: Int = 21
               )

object User {
  implicit val userReads = Json.reads[User]
  implicit val userWrites = Json.writes[User]
}
