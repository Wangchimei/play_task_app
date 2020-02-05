package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class taskList1 @Inject()(val controllerComponents: ControllerComponents) extends BaseController{
  def login = Action {
    Ok(views.html.login1())
  }

  def valdiateLoginGet(username: String, password: String) = Action {
    Ok(s"$username logged in with $password")
  }

  def valdiateLoginPost = Action {  request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      Redirect(routes.taskList1.taskList())
    }.getOrElse(Redirect(routes.taskList1.login()))
  }

  def taskList = Action {
    val tasks = List("task 1", "task 2", "task 3")
    Ok(views.html.taskList1(tasks))
  }
}