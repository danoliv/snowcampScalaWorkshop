enablePlugins(ScalaJSPlugin, WorkbenchPlugin)

name := "playWithScala"

version := "0.1"

scalaVersion := "2.12.10"

scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.7",
  //"org.querki" %%% "jquery-facade" % "1.2",
  "com.lihaoyi" %%% "utest" % "0.7.1" % "test"
)

skip in packageJSDependencies := false
//jsDependencies += "org.webjars" % "jquery" % "2.2.1" / "jquery.js" minified "jquery.min.js"

jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()

testFrameworks += new TestFramework("utest.runner.Framework")