package models

import play.api.Play.current
import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import mongoContext._
import com.novus.salat.dao.{ SalatDAO, ModelCompanion }
import com.novus.salat.annotations._
import se.radley.plugin.salat._
import se.radley.plugin.salat.Binders._

case class Order(
  @Key("_id") id: ObjectId = new ObjectId,
  name: String,
  food_name:  String,
  comment: String,
  dinnerId: ObjectId
)

object Orders extends ModelCompanion[Order, ObjectId] {
  def collection = mongoCollection("Orders")
  val dao = new SalatDAO[Order, ObjectId](collection) {}
  def findByDinner(dinnerId: ObjectId) = dao.find(MongoDBObject("dinnerId" -> dinnerId)).toList
} 