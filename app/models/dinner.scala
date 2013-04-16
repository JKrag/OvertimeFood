package models
import play.api.Play.current
import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import mongoContext._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import java.util.Date
import se.radley.plugin.salat._
import se.radley.plugin.salat.Binders._

case class Dinner(
  @Key("_id") id: ObjectId,
  dinner_time: Date,
  order_latest_time:  Date,
  name: String,
  description: String,
  restaurant_name: String,
  restaurant_link: String,
  open: Boolean
)

object Dinners extends ModelCompanion[Dinner, ObjectId] {
  def collection = mongoCollection("dinners")
  val dao = new SalatDAO[Dinner, ObjectId](collection) {}
  def all = collection.map(grater[Dinner].asObject(_)).toList
} 