package models

import java.security.KeyStore.PasswordProtection
import collection.mutable

object TaskListInMemoryModel {
  private val users = mutable.Map[String, String]("aa" -> "pass")
  private val tasks = mutable.Map[String, List[String]]("aa" -> List("qw","as","zx","cde"))

  def validateUser(username: String, password: String): Boolean = {
    users.get(username).map(_ == password).getOrElse(false)
  }

  def createUser(username: String, password: String): Boolean = {
    if (users.contains(username)) false else {
      users(username) = password 
      true
    }
  }
//
  def getTask(username: String): Seq[String] = {
    tasks.get(username).getOrElse(Nil)
  }
//
//  def addTask(username: String, task: String): Unit = {
//
//  }
//
//  def removeTask(username: String, index: Int): Boolean = {
//
//  }
}
