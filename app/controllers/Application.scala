package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Persons
import models.Person

object Application extends Controller {

val personForm = Form(
  mapping(
    "username" -> nonEmptyText,
    "password" -> nonEmptyText,
    "confirm" -> nonEmptyText,
    "realName" -> text
  )(Person.apply)(Person.unapply)
    verifying("Passwords must match", fields => fields match {
      case Person(_, password, confirmation, _) => password.equals(confirmation)
    })
)
  
  def index = Action {
    Ok(views.html.index(Persons.all, personForm ))
  }
  
  def newPerson = Action { implicit request =>
    personForm.bindFromRequest.fold(
    errors => BadRequest(views.html.index(Persons.all, errors)),
    person => {
      Persons.create(person)
      Redirect(routes.Application.index)
    }
  )
  }
}