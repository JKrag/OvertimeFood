package models
import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._

case class Person(
  username: String,
  password: String,
  confirm:  String,
  realName: String
)

object Persons {
  val Persons = MongoConnection()("food")("Persons")

  def all = Persons.map(grater[Person].asObject(_)).toList
  def create(Person: Person) {
    Persons += grater[Person].asDBObject(Person)
  }
}