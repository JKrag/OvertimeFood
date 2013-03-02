package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Dinners
import models.Dinner
import com.mongodb.casbah.Imports._

object Application extends Controller {

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
  
  def index = Action {
    Ok(views.html.index(Dinners.all))
  }

  def newDinner() = Action { 
    Ok(views.html.dinner(dinnerForm))  
  }
  
  def addDinner = Action { implicit request =>
    dinnerForm.bindFromRequest.fold(
    errors => BadRequest(views.html.dinner(errors)),
    person => {
      Dinners.create(person)
      Redirect(routes.Application.index)
    }
  )
  }
}