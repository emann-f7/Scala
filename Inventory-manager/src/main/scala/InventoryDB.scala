import slick.jdbc.H2Profile.api._
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class Items(tag: Tag) extends Table[Item](tag, "ITEMS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")
  def category = column[String]("CATEGORY")
  def price = column[Double]("PRICE")
  def quantity = column[Int]("QUANTITY")

  def * = (id.?, name, category, price, quantity).mapTo[Item]
}

object InventoryDB {
  val db = Database.forConfig("h2mem1")
  val items = TableQuery[Items]

  def createTable(): Unit = {
    val setup = items.schema.createIfNotExists
    Await.result(db.run(setup), 10.seconds)
  }

  // CREATE
  def addItem(item: Item): Future[Int] = {
    db.run(items returning items.map(_.id) += item)
  }

  // READ - all items
  def getAllItems(): Future[Seq[Item]] = {
    db.run(items.result)
  }

  // READ - single item by id
  def getItemById(id: Int): Future[Option[Item]] = {
    db.run(items.filter(_.id === id).result.headOption)
  }

  // UPDATE
  def updateItem(id: Int, updated: Item): Future[Int] = {
    db.run(items.filter(_.id === id).update(updated.copy(id = Some(id))))
  }

  // DELETE
  def deleteItem(id: Int): Future[Int] = {
    db.run(items.filter(_.id === id).delete)
  }

  def close(): Unit = db.close()
}