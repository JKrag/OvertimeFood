package models
import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import com.novus.salat.annotations._
import java.util.Date

case class Dinner(
  @Key("_id") id: ObjectId,
  dinner_time: Date,
  order_latest_time:  Date,
  name: String,
  description: String,
  order_link: String
)

object Dinners {
  val Dinners = MongoConnection()("food")("Dinners")
  def all = Dinners.map(grater[Dinner].asObject(_)).toList
  def create(Dinner: Dinner) {
    Dinners += grater[Dinner].asDBObject(Dinner)
  }
} 