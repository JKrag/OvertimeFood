package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Person

object Application extends Controller {

val personForm = Form( "name" -> nonEmptyText )
  
  def index = Action {
    Ok(views.html.index(Person.all(), personForm ))
  }
}