import sbt._
import sbt.Keys._
import com.typesafe.sbt.web.Import._

object TypeScript {

  lazy val typescriptCompile = taskKey[Seq[File]]("Compile .tsc files with the typescript compiler")
  lazy val typescriptOptions = settingKey[Seq[String]]("Options to be passed to typescript compiler")
  lazy val typescriptDefinitionsDir = settingKey[File]("Directory that holds the typescript definitions")
  lazy val typescriptDefinitions = settingKey[Seq[File]]("Definitions files that should be included in the compile")


  lazy val settings = Seq(
    typescriptCompile := {
      val log = streams.value.log
      val sourceDir = (sourceDirectory in Assets).value
      val sources   = (sourceDir ** ("*.ts" -- "*.d.ts")).get
      val target    = WebKeys.webTarget.value / "js"
      sources map { path =>
        //val f = path.getName
        val target_fname = target / (path.base + ".js")
        val command = "tsc " + (typescriptOptions.value.mkString(" ")) + " --out " + target_fname + " " + path + " " + typescriptDefinitions.value.mkString(" ")
        log.info(s"Running command [$command]")
        command.!
        target_fname
      }
    },
    typescriptOptions := Seq("-m commonjs"),
    typescriptDefinitionsDir := ((sourceDirectory in Assets).value / "lib"),
    typescriptDefinitions := Seq(typescriptDefinitionsDir.value / "angularjs" / "angular.d.ts")
  )

}
