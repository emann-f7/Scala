import scala.io.StdIn._
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {

  InventoryDB.createTable()

  var running = true

  while (running) {
    println("\n===== Product Inventory Manager =====")
    println("1. Add Item")
    println("2. View All Items")
    println("3. Update Item")
    println("4. Delete Item")
    println("5. Exit")
    print("Choose an option: ")

    readLine().trim match {
      case "1" => addItem()
      case "2" => viewItems()
      case "3" => updateItem()
      case "4" => deleteItem()
      case "5" =>
        running = false
        InventoryDB.close()
        println("Goodbye!")
      case _ => println("Invalid option, try again.")
    }
  }

  def addItem(): Unit = {
    print("Name: ")
    val name = readLine()
    print("Category: ")
    val category = readLine()
    print("Price: ")
    val price = readDouble()
    print("Quantity: ")
    val quantity = readInt()

    val newItem = Item(None, name, category, price, quantity)
    val result = Await.result(InventoryDB.addItem(newItem), 10.seconds)
    println(s"Item added with ID: $result")
  }

  def viewItems(): Unit = {
    val allItems = Await.result(InventoryDB.getAllItems(), 10.seconds)
    if (allItems.isEmpty) println("No items found.")
    else allItems.foreach { item =>
      println(s"ID: ${item.id.getOrElse(-1)} | Name: ${item.name} | Category: ${item.category} | Price: ${item.price} | Qty: ${item.quantity}")
    }
  }

  def updateItem(): Unit = {
    print("Enter ID of item to update: ")
    val id = readInt()

    val existing = Await.result(InventoryDB.getItemById(id), 10.seconds)
    existing match {
      case Some(_) =>
        print("New Name: ")
        val name = readLine()
        print("New Category: ")
        val category = readLine()
        print("New Price: ")
        val price = readDouble()
        print("New Quantity: ")
        val quantity = readInt()

        val updated = Item(Some(id), name, category, price, quantity)
        val rows = Await.result(InventoryDB.updateItem(id, updated), 10.seconds)
        if (rows > 0) println("Item updated successfully.")
        else println("Update failed.")
      case None =>
        println("Item not found.")
    }
  }

  def deleteItem(): Unit = {
    print("Enter ID of item to delete: ")
    val id = readInt()
    val rows = Await.result(InventoryDB.deleteItem(id), 10.seconds)
    if (rows > 0) println("Item deleted successfully.")
    else println("Item not found.")
  }
}