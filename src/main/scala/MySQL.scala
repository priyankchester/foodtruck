package src.main.scala

import java.sql.{PreparedStatement, Connection, DriverManager}
import caseclasses.Truck
import scala.collection.immutable.HashMap
import scala.util.control.NonFatal

/**
 * Created by pdesai on 8/8/15.
 */
object MySQL {

  var connection: Connection = null
  var preparedStmt = HashMap[String, PreparedStatement]()

  def init(): Unit = {
    val driver = "com.mysql.jdbc.Driver"
    val url = ""
    val username = ""
    val password = ""

    // make the connection
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)

    preparedStmt += "insertIntoTable" -> connection.prepareStatement("INSERT INTO food_trucks (truck_name, location, time, day, hood, cuisine, description) VALUES (?, ?, ?, ?, ?, ?, ?)")
    preparedStmt += "updateCuisine" -> connection.prepareStatement("UPDATE food_trucks SET cuisine = ?, description = ? where truck_name = ?")

    val stmt = connection.prepareStatement("TRUNCATE TABLE food_trucks")
    stmt.execute()
  }

  def insertIntoTable(truck: Truck) : Either[String, String]= {
    val func = "insertIntoTable"
    try {
      val stmt = preparedStmt.get("insertIntoTable").get
      stmt.setString(1, s"${truck.truck_name}")
      stmt.setString(2, s"${truck.location}")
      stmt.setString(3, s"${truck.time}")
      stmt.setString(4, s"${truck.day}")
      stmt.setString(5, s"${truck.hood}")
      stmt.setString(6, s"${truck.cuisine}")
      stmt.setString(7, s"${truck.description}")
      stmt.execute()
      Right("Success")
    } catch{
      case NonFatal(exc) =>
        println(s"$func: ${exc.toString}")
        Left(exc.toString)
    }
  }

  def updateCuisine(cuisine: String, desc: String, truck_name: String): Either[String, String] = {
    val func = "insertIntoTable"
    try {
      val stmt = preparedStmt.get("updateCuisine").get
      stmt.setString(1, cuisine)
      stmt.setString(2, desc)
      stmt.setString(3, truck_name)
      stmt.execute()
      Right("Success")
    } catch{
      case NonFatal(exc) =>
        println(s"$func: ${exc.toString}")
        Left(exc.toString)
    }
  }
  
  def close(): Unit = {
    connection.close()
  }
}