package models
import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import mongoContext._
import com.novus.salat.dao.{ SalatDAO, ModelCompanion }
import com.novus.salat.annotations._

case class Order(
  @Key("_id") id: ObjectId = new ObjectId,
  name: String,
  food_name:  String,
  comment: String,
  dinnerId: ObjectId
)

object Orders extends ModelCompanion[Order, ObjectId] {
  val collection = MongoConnection()("food")("Orders")
  val dao = new SalatDAO[Order, ObjectId](collection = collection) {}

  def findByDinner(dinnerId: ObjectId) = dao.find(MongoDBObject("dinnerId" -> dinnerId)).toList
} 