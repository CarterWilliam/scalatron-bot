import sbt._
import sbt.Keys._

object Build extends sbt.Build {
  import ScalatronPlugin._

  lazy val `scalatron-bot` = Project (
    id = "scalatron-bot",
    base = file("."),
    settings = botSettings
  )

  lazy val botSettings = Defaults.coreDefaultSettings ++ ScalatronPlugin.settings ++ Seq (
    name := "scalatron-bot",
    version := "1.0.0",
    scalaVersion := "2.9.1",

    localBotDirectory in Scalatron := "/path/to/your/local/scalatron/bot/directory/Bot"
  )

}

object ScalatronPlugin {
  lazy val Scalatron = config("scalatron")
  lazy val localBotDirectory = SettingKey[String]("bot-directory")
  lazy val `publish-local` = taskKey[Unit]("Package bot jar and copy to a local directory")
  lazy val `copy-bot-jar` = taskKey[Unit]("Copy a packaged bot jar to a local directory")

  lazy val settings: Seq[Setting[_]] = Seq(
    localBotDirectory in Scalatron := "/tmp/scalatron-bots/",
    (`copy-bot-jar` in Scalatron) := {
      val jarDirectory = (Keys.`package` in Compile).value
      val botDirectory = (localBotDirectory in Scalatron).value
      val outputDirectory = s"$botDirectory/ScalatronBot.jar"
      s"mkdir -p $botDirectory".!
      s"cp $jarDirectory $outputDirectory".!
      println(s"Scalatron Bot published to $outputDirectory")
    },
    (`publish-local` in Scalatron) <<= (`copy-bot-jar` in Scalatron)
  )
}
