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
    //"id" -> ignored(new ObjectId),
    "dinner_time" -> date("dd-MM-yy HH:mm"),
    "order_latest_time" -> date("dd-MM-yy HH:mm"),
    "name" -> nonEmptyText,
    "description" -> text,
    "order_link" -> text
  )//(Dinner.apply)(Dinner.unapply)
  ((dinner_time, order_latest_time, name, description, order_link) => Dinner(new ObjectId, dinner_time, order_latest_time, name, description, order_link))
  ((dinner:Dinner) => Some((dinner.dinner_time, dinner.order_latest_time, dinner.name, dinner.description, dinner.order_link)))
)
  
  def index = Action {
    Ok(views.html.index(Dinners.all, dinnerForm ))
  }
  
  def newDinner = Action { implicit request =>
    dinnerForm.bindFromRequest.fold(
    errors => BadRequest(views.html.index(Dinners.all, errors)),
    person => {
      Dinners.create(person)
      Redirect(routes.Application.index)
    }
  )
  }
}