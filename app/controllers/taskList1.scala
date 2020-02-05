package controllers

import javax.inject._
import models.TaskListInMemoryModel
import play.api.mvc._

@Singleton
class taskList1 @Inject()(val controllerComponents: ControllerComponents) extends BaseController{
  def login = Action {
    Ok(views.html.login1())
  }

  def validateLoginGet(username: String, password: String) = Action {
    Ok(s"$username logged in with $password")
  }

  def validateLoginPost = Action {  request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.validateUser(username, password)){
        Redirect(routes.taskList1.taskList()).withSession("username" -> username)
      } else {
        Redirect(routes.taskList1.login())
      }
    }.getOrElse(Redirect(routes.taskList1.login()))
  }

  def createUser = Action {  request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.createUser(username, password)) {
        Redirect(routes.taskList1.taskList()).withSession("username" -> username)
      } else {
        Redirect(routes.taskList1.login())
      }
    }.getOrElse(Redirect(routes.taskList1.login()))
  }

  def taskList = Action { request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val tasks = TaskListInMemoryModel.getTask(username)
      Ok(views.html.taskList1(tasks))
    }.getOrElse(Redirect(routes.taskList1.login()))
  }
}