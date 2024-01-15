package controllers

import models.User
import play.api._
import play.api.libs.json._
import play.api.mvc._

import javax.inject._


@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  // users() -> list of users
  // User() -> case class for what a user looks like in models
  // user() -> a specific user instance

  def users() = Action { implicit request: Request[AnyContent] =>
    val users = List(User()) // default instance of user
    Ok(Json.toJson(users)).as("application/json")
  }

  def user(userId: String) = Action { implicit request: Request[AnyContent] =>
    val user = User(id = userId) // single instance ; overidding the default id
    Ok(Json.toJson(user)).as("application/json")
  }

  // in Action we need tp parse json
  // JsValue -

  def add() = Action(parse.json) { implicit request: Request[JsValue] =>
    val userResult = request.body.validate[User] // validation
    userResult.fold(
      errors => {
        // Handle the case when there are validation errors
        BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(errors)))
      },
      user => {
        // Handle the case when the validation is successful
        Ok(Json.toJson(user)).as("application/json")
      }
    )
  }

  def index() = Action {
    Ok(views.html.index())
  }
}


