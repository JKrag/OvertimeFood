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
  
  def index = Action {
    val dinners = Dinners.all
    if(dinners.length == 1){
		Redirect(routes.DinnerController.show(dinners(0).id))
	} else {
		Ok(views.html.index(dinners))
	}
  }

  def admin = Action {
    val dinners = Dinners.all
    Ok(views.html.dinnerAdmin(dinners))
  }
}