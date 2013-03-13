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
    val dinners = Dinners.all
    Ok(views.html.index(dinners, dinners map {d => (d.id, Orders.findByDinner(d.id))} toMap, orderForm))
  }

  def addOrder(dinnerId: ObjectId) = Action { implicit request =>
    orderForm.bindFromRequest.fold(
      errors => {
        val dinners = Dinners.all
        BadRequest(views.html.index(dinners, dinners map {d => (d.id, Orders.findByDinner(d.id))} toMap, errors))
      },
      order => {
          Orders.create(Order(order.id, order.name, order.food_number, order.comment, dinnerId))
          Redirect(routes.Application.index)
      }
      )
  }
}