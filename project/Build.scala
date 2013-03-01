import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "food"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      "com.github.twitter" %  "bootstrap"  % "2.0.2",
      "se.radley" %% "play-plugins-salat" % "1.2"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      routesImport += "se.radley.plugin.salat.Binders._",
      templatesImport += "org.bson.types.ObjectId",
      resolvers ++= Seq(
        "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
        "webjars"          at "http://webjars.github.com/m2"
      )
    )

}