package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Dinners
import models.Dinner
import models.Orders
import models.Order
import com.mongodb.casbah.Imports._

object Application extends Controller {
  
  val orderForm = Form(
    mapping(
      "name" -> text,
      "food_number" -> number,
      "comment" -> text
    ) ((name, food_number, comment) => Order(new ObjectId, name, food_number, comment, new ObjectId))
      ((order:Order) => Some((order.name, order.food_number, order.comment)))
  )

  def index = Action {
    Ok(views.html.index(Dinners.all, Orders.all))
  }

}