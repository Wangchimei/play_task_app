package controllers

import javax.inject._
import models.TaskListInMemoryModel
import play.api.mvc._

@Singleton
class taskList1 @Inject()(val controllerComponents: ControllerComponents) extends BaseController{

  def login = Action {implicit request =>
    Ok(views.html.login1())
  }

  def validateLoginGet(username: String, password: String) = Action {
    Ok(s"$username logged in with $password")
  }

  def validateLoginPost = Action {implicit request =>
    // parse out the encoded value
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

  def createUser = Action {implicit request =>
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

  def taskList = Action {implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val tasks = TaskListInMemoryModel.getTask(username)
      Ok(views.html.taskList1(tasks))
    }.getOrElse(Redirect(routes.taskList1.login()))
  }

  def logout = Action {
    Redirect(routes.taskList1.login()).withNewSession
  }
}