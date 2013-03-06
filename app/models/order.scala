package models
import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import mongoContext._

case class Order(
  id: ObjectId = new ObjectId,
  name: String,
  food_number:  Integer,
  comment: String,
  dinner: ObjectId
)

object Orders {
  val Orders = MongoConnection()("food")("Orders")

  def all = Orders.map(grater[Order].asObject(_)).toList
  def create(Order: Order) {
    Orders += grater[Order].asDBObject(Order)
  }
} 