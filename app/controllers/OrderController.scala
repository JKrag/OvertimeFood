package controllers

import play.api._
import play.api.mvc._
import play.api.data.Forms._
import play.api.data._
import com.mongodb.casbah.Imports._
import models.Order
import models.Orders
import models.Dinners

object OrderController extends Controller {

	def orderForm(dinnerId:ObjectId = new ObjectId, id:ObjectId = new ObjectId) = Form(
	    mapping(
	    	"id" -> ignored(id),
	    	"name" -> text,
	    	"food_name" -> text,
	    	"comment" -> text,
	    	"dinnerId" -> ignored(dinnerId)
	    )(Order.apply)(Order.unapply)
  	)

	// form for creating new order
  	def create(dinnerId:ObjectId) = Action { implicit request =>
  		Dinners.findOneById(dinnerId).map{ dinner =>
  			Ok(views.html.newOrder(dinner, orderForm(dinnerId)))
  		}.getOrElse(Redirect(routes.Application.index))
  	}

  	// handle create form submit
	def add(dinnerId: ObjectId) = Action { implicit request =>
	    Dinners.findOneById(dinnerId).map { dinner =>
	      orderForm(dinnerId).bindFromRequest.fold(
	        errors => {
	          BadRequest(views.html.showDinner(dinner, Orders.findByDinner(dinner.id)))
	          },
	          order => {
	            Orders.insert(Order(order.id, order.name, order.food_name, order.comment, dinner.id))
	            Redirect(routes.DinnerController.show(dinner.id))
	          })
	    }.getOrElse(Redirect(routes.Application.index))
	 }

	def delete(id: ObjectId) = Action { implicit request =>
		Orders.findOneById(id).map { order =>
			Orders.remove(MongoDBObject("_id" -> order.id))
			Redirect(routes.DinnerController.show(order.dinnerId))
		}.getOrElse(Redirect(routes.Application.index))
	}
}