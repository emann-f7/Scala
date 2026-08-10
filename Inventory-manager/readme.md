
# Product Inventory Manager

A console-based inventory management app built in **Scala**, using **Slick** with an **H2 database**. Supports full CRUD (Create, Read, Update, Delete) operations for stock items (Name, Category, Price, Quantity).

## Tech Stack
- Scala 2.13, sbt
- Slick (async database queries)
- H2 in-memory database

## How to Run

sbt run

Then use the menu to Add, View, Update, or Delete items.

## Domain Model
scala
case class Item(
  id: Option[Int],
  name: String,
  category: String,
  price: Double,
  quantity: Int
)
