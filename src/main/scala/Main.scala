package src.main.scala

import caseclasses.{ByDay, Truck}

import scala.collection.mutable
/**
 * Created by pdesai on 8/7/15.
 */
object Main extends App {

  var links = new mutable.HashMap[String, List[String]]
  links.put("cuisine", List("http://www.seattlefoodtruck.com/index.php/by-cuisine/asian/",
    "http://www.seattlefoodtruck.com/index.php/by-cuisine/bbq/",
    "http://www.seattlefoodtruck.com/index.php/by-cuisine/burgerssandwiches/",
    "http://www.seattlefoodtruck.com/index.php/by-cuisine/coffee/",
    "http://www.seattlefoodtruck.com/index.php/by-cuisine/hot-dogs/",
    "http://www.seattlefoodtruck.com/index.php/by-cuisine/italian/",
    "http://www.seattlefoodtruck.com/index.php/by-cuisine/mexican/",
    "http://www.seattlefoodtruck.com/index.php/by-cuisine/mediterranean/",
    "http://www.seattlefoodtruck.com/index.php/by-cuisine/sweets/"))

  links.put("day", List("http://www.seattlefoodtruck.com/index.php/by-day/monday/",
    "http://www.seattlefoodtruck.com/index.php/by-day/tuesday/",
    "http://www.seattlefoodtruck.com/index.php/by-day/wednesday/",
    "http://www.seattlefoodtruck.com/index.php/by-day/thursday/",
    "http://www.seattlefoodtruck.com/index.php/by-day/friday/",
    "http://www.seattlefoodtruck.com/index.php/by-day/saturday/",
    "http://www.seattlefoodtruck.com/index.php/by-day/sunday/"))

  links.put("hood", List("http://www.seattlefoodtruck.com/index.php/neighborhoods/chucks-hop-shop/",
    "http://www.seattlefoodtruck.com/index.php/neighborhoods/south-lake-union/",
    "http://www.seattlefoodtruck.com/index.php/neighborhoods/downtown-seattle/",
    "http://www.seattlefoodtruck.com/index.php/neighborhoods/sodo/",
    "http://www.seattlefoodtruck.com/index.php/neighborhoods/ballard/",
    "http://www.seattlefoodtruck.com/index.php/neighborhoods/queen-anne/",
    "http://www.seattlefoodtruck.com/index.php/neighborhoods/eastside/",
    "http://www.seattlefoodtruck.com/index.php/neighborhoods/south-end/",
    "http://www.seattlefoodtruck.com/index.php/neighborhoods/everywhere-else/"))

  val cuisines = FetchData.getByCuisine(links.get("cuisine").get)
  val datas: List[ByDay] = FetchData.getByDay(links.get("day").get)
  //println(x)

  MySQL.init()
  datas.foreach(data =>
    MySQL.insertIntoTable(new Truck(data.name, data.location, data.time, data.day, data.hood, "", ""))
  )

  cuisines.foreach(cuisine =>
    MySQL.updateCuisine(cuisine.cuisine, cuisine.description, cuisine.name)
  )
}
