name := "Validering"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  //javaJdbc,
  javaEbean,
  javaJpa,
  cache
)

libraryDependencies += "org.hibernate" % "hibernate-entitymanager" % "4.2.8.Final"

libraryDependencies += "mysql" % "mysql-connector-java" % "3.+"

//libraryDependencies += "com.google.code.gson" % "gson" % "2.2.4"

play.Project.playJavaSettings
