akka-actor-demo
===============

Ask send-and-receive demo from http://doc.akka.io/docs/akka/snapshot/scala/actors.html#Ask__Send-And-Receive-Future

During a recent meetup of the [Dallas Scala Enthusiast](http://www.meetup.com/Dallas-Scala-Enthusiasts/) (12/11/2014) there was much discussion about this example Actor code from the Akka docs:
```scala
import akka.pattern.{ ask, pipe }
import system.dispatcher // The ExecutionContext that will be used
final case class Result(x: Int, s: String, d: Double)
case object Request

implicit val timeout = Timeout(5 seconds) // needed for `?` below

val f: Future[Result] =
	for {
		x <- ask(actorA, Request).mapTo[Int] // call pattern directly
		s <- (actorB ask Request).mapTo[String] // call by implicit conversion
		d <- (actorC ? Request).mapTo[Double] // call by symbolic name
	} yield Result(x, s, d)

	f pipeTo actorD // .. or ..
	pipe(f) to actorD
```

The goal of this project is to run that code with as little changes as possible so Dallas Scala Enthusiasts can explore and see how it works.  I've added some choice delays and println statements to illustrate that code is running asynchronously.

### Running the project
Note: this uses the latest version of Scala (at the time of writing) `2.11.4`.  You can change the scala version in `build.sbt`

To run the project:

```sh
> sbt run
```

### Generating Eclipse project files
If you want to edit the project in Scala-IDE/Eclipse the project setup includes the [sbteclipse](https://github.com/typesafehub/sbteclipse) plugin and will generate Eclipse project files thusly:

```sh
> sbt eclipse
```


### Changes made to demo code

- Add some imports
 - These could be simplified to `import akka._` and `import scala.concurrent._`
- Put demo code inside main object: `object main extends App { ...`
- Define an actor system and create actors
- Create simple implementations of actors A-D
- Create sbt project

The actual meat of the code isn't changed, I just put in some of the more boiler-plate code that was omitted to make the sample brief.
