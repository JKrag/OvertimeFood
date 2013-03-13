package controllers

import play.api._
import play.api.mvc._
import play.api.data.Forms._
import play.api.data._
import models.Dinners
import models.Dinner
import com.mongodb.casbah.Imports._
/** Uncomment the following lines as needed **/
/**
import play.api.Play.current
import play.api.libs._
import play.api.libs.iteratee._
import play.api.libs.concurrent._
import java.util.concurrent._
import scala.concurrent.stm._
import akka.util.duration._
import play.api.cache._
import play.api.libs.json._
**/

object DinnerController extends Controller {
	
    val dinnerForm = Form(
    	mapping(
    			"dinner_time" -> date("dd-MM-yy HH:mm"),
      		"order_latest_time" -> date("dd-MM-yy HH:mm"),
      		"name" -> nonEmptyText,
      		"description" -> text,
      		"restaurant_name" -> text,
      		"restaurant_link" -> text,
      		"open" -> checked("Is open")
      	)((dinner_time, order_latest_time, name, description, restaurant_name, restaurant_link, open) => Dinner(new ObjectId, dinner_time, order_latest_time, name, description, restaurant_name, restaurant_link, open))
      	((dinner:Dinner) => Some((dinner.dinner_time, dinner.order_latest_time, dinner.name, dinner.description, dinner.restaurant_name, dinner.restaurant_link, dinner.open)))
    	)

    	def newDinner() = Action {
    	Ok(views.html.dinner(dinnerForm))  
    }
  
  	def addDinner = Action { implicit request =>
    	dinnerForm.bindFromRequest.fold(
    	errors => BadRequest(views.html.dinner(errors)),
    	dinner => {
      		Dinners.create(dinner)
      		Redirect(routes.Application.index)
    	}
  		)
  	}
}