import sbt._
import sbt.Keys._

object Build extends sbt.Build {

  lazy val `scalatron-bot` = Project (
    id = "scalatron-bot",
    base = file("."),
    settings = botSettings
  )

  lazy val botSettings = Defaults.coreDefaultSettings ++ ScalatronPlugin.settings ++ Seq (
    name := "scalatron-bot",
    version := "1.0.0",
    scalaVersion := "2.9.1"
  )

}

object ScalatronPlugin {
  lazy val Scalatron = config("scalatron")
  lazy val localBotDirectory = SettingKey[File]("bot-directory")
  lazy val `publish-local` = taskKey[Unit]("Package bot jar and copy to a local directory")
  lazy val `copy-bot-jar` = taskKey[Unit]("Copy a packaged bot jar to a local directory")

  lazy val settings: Seq[Setting[_]] = Seq(
    localBotDirectory in Scalatron := file("/tmp/scalatron-bots/"),
    (`copy-bot-jar` in Scalatron) := {
      val jarDirectory = (Keys.`package` in Compile).value
      val botDirectory = (localBotDirectory in Scalatron).value
      s"cp $jarDirectory $botDirectory".!
      println(s"Scalatron Bot published to ${botDirectory.toString}")
    },
    (`publish-local` in Scalatron) <<= (`copy-bot-jar` in Scalatron)
  )
}
