import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "food"
    val appVersion      = "1.0-SNAPSHOT"

	val appDependencies = Seq(
	  "com.github.twitter" %  "bootstrap"  % "2.0.2",
	  "com.novus" %% "salat" % "1.9.2-SNAPSHOT"
	)
    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
       resolvers ++= Seq(
       	"webjars" at "http://webjars.github.com/m2",
       	"Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
    )

}
