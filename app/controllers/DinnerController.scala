package controllers

import play.api._
import play.api.mvc._
import play.api.data.Forms._
import play.api.data._
import models.Dinners
import models.Dinner
import models.Order
import models.Orders
import com.mongodb.casbah.Imports._

object DinnerController extends Controller {

  val orderForm = Form(
    mapping(
      "name" -> text,
      "food_name" -> text,
      "comment" -> text
    ) ((name, food_name, comment) => Order(new ObjectId, name, food_name, comment, new ObjectId))
      ((order:Order) => Some((order.name, order.food_name, order.comment)))
  )
	
  def dinnerForm(id: ObjectId = new ObjectId) = Form(
  	mapping(
        "id" -> ignored(id),
  			"dinner_time" -> date("dd-MM-yy HH:mm"),
    		"order_latest_time" -> date("dd-MM-yy HH:mm"),
    		"name" -> nonEmptyText,
    		"description" -> text,
    		"restaurant_name" -> text,
    		"restaurant_link" -> text,
    		"open" -> boolean
    	)(Dinner.apply)(Dinner.unapply)
  	)

  // show create form
	def create = Action {
  	Ok(views.html.newDinner(dinnerForm()))  
  }

  // handle submit from create form
	def add = Action { implicit request =>
  	dinnerForm().bindFromRequest.fold(
    	errors => BadRequest(views.html.newDinner(errors)),
    	dinner => {
      		Dinners.insert(dinner)
      		Redirect(routes.Application.index)
    	}
		)
	}

  // handle delete button action
  def delete(id:ObjectId) = Action { implicit request =>
    Dinners.remove(MongoDBObject("_id" -> id));
    Redirect(routes.Application.index)
  }

  // show edit form
  def edit(id:ObjectId) = Action { implicit request =>
    Dinners.findOneById(id).map { dinner =>
      Ok(views.html.editDinner(id,dinnerForm(id).fill(dinner)))
    }.getOrElse(NotFound)
  }

  // handle submit from edit form
  def update(id:ObjectId) = Action { implicit request =>
    dinnerForm(id).bindFromRequest.fold(
      errors => BadRequest(views.html.editDinner(id, errors)),
      dinner => {
        Dinners.save(dinner.copy(id = id))
        Redirect(routes.Application.index)
      }
    )
  }

  def show(id:ObjectId) = Action { implicit request =>
    Dinners.findOneById(id).map { dinner =>
      Ok (views.html.showDinner(dinner, Orders.findByDinner(dinner.id), orderForm))  
    }.getOrElse(Redirect(routes.Application.index))
    
  }

  def addOrder(dinnerId: ObjectId) = Action { implicit request =>
    Dinners.findOneById(dinnerId).map { dinner =>
      orderForm.bindFromRequest.fold(
        errors => {
          BadRequest(views.html.showDinner(dinner, Orders.findByDinner(dinner.id), errors))
          },
          order => {
            Orders.create(Order(order.id, order.name, order.food_name, order.comment, dinnerId))
            Redirect(routes.DinnerController.show(dinner.id))
          })
    }.getOrElse(Redirect(routes.Application.index))
  }

  def deleteOrder(orderId: ObjectId) = Action { implicit request =>
    Orders.delete(orderId)
    Redirect(routes.Application.index)
  }
}