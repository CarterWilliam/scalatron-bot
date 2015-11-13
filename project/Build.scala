import sbt._
import Keys._

object Build extends Build {

  val `scalatron-bot` = Project(
    id = "scalatron-bot",
    base = file("."),
    settings = Project.defaultSettings ++ botSettings)

  val botSettings = Seq[Setting[_]](
    organization := "will",
    name := "scalatron-bot",
    version := "1.0.0",
    scalaVersion := "2.9.1"
  )

}
