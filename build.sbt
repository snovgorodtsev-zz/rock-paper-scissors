name := "RockPaperScissors"

version := "1.0"

scalaVersion := "2.13.1"

val zioVersion = "1.0.0-RC17"
val zio = "dev.zio" %% "zio" % zioVersion
val zioTest = "dev.zio" %% "zio-test" % zioVersion % "test"
val zioTestSbt = "dev.zio" %% "zio-test-sbt" % zioVersion % "test"

libraryDependencies ++= Seq(zio, zioTest, zioTestSbt)

testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))