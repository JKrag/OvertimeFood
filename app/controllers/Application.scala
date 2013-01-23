package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Persons

object Application extends Controller {

val personForm = Form( "name" -> nonEmptyText )
  
  def index = Action {
    Ok(views.html.index(Persons.all, personForm ))
  }
  def newPerson() = Action { implicit request =>
    Ok("sdf")	
  }
}