package src.main.scala

import caseclasses._
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import scala.collection.mutable.ListBuffer

/**
 * Created by pdesai on 8/8/15.
 */
object FetchData {
  def getByCuisine(links: List[String]): List[ByCuisine] = {
    var listByCuisines = new ListBuffer[ByCuisine]
    links.foreach(link => {
      val slashes = link.split('/')
      val cuisine = slashes.last

      val doc: Document = Jsoup.connect(link).get()
      val anchorTags = doc.select(".entry-content tr")
      val itr = anchorTags.iterator()

      while (itr.hasNext) {
        val anchorTag = itr.next()
        var name = anchorTag.select("a")
        val link = anchorTag.select("td").last().text()
        listByCuisines += ByCuisine(cuisine, name.text(), link)
      }
    })
    listByCuisines.toList
  }

  def getByDay(links: List[String]): List[ByDay] = {
    var listByDay = new ListBuffer[ByDay]
    links.foreach(link => {
      val slashes = link.split('/')
      val day = slashes.last

      val doc: Document = Jsoup.connect(link).get()
      val anchorTags = doc.select(".entry-content tr")
      val itr = anchorTags.iterator()

      while (itr.hasNext) {
        val anchorTag = itr.next()
        val name = anchorTag.select("a")
        val hood = anchorTag.select("strong")
        val data = anchorTag.select("td").last()
        val newdata = data.text().replaceAll(hood.text(), "")
        val data2 = newdata.split(',')

        listByDay += new ByDay(name.text(), hood.text(), data2.init.mkString(","), data2.last, day)
      }
    })
    listByDay.toList
  }

}
