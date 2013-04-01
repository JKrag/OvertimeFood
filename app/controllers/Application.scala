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
      "food_name" -> text,
      "comment" -> text
    ) ((name, food_name, comment) => Order(new ObjectId, name, food_name, comment, new ObjectId))
      ((order:Order) => Some((order.name, order.food_name, order.comment)))
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
          Orders.create(Order(order.id, order.name, order.food_name, order.comment, dinnerId))
          Redirect(routes.Application.index)
      }
      )
  }

  def deleteOrder(orderId: ObjectId) = Action { implicit request =>
    Orders.delete(orderId)
    Redirect(routes.Application.index)
  }
}