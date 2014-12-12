package gangstead

import akka.pattern.{ ask, pipe }
import akka.actor.ActorSystem
import akka.actor.Props
import akka.util.Timeout
import scala.concurrent.ExecutionContext.Implicits.global  //instead of system.dispatcher from the sample code
import scala.concurrent.duration._ //For the timeout duratino "5 seconds"
import scala.concurrent.Future  //For the `mapTo` function
import scala.language.postfixOps //removes warning on postfix ops like "5 seconds"

final case class Result(x: Int, s: String, d: Double)
case object Request

object main extends App {
	//Create the actors
	val system = ActorSystem("akka-actor-demo")
	val actorA = system.actorOf(Props[actorA], "actorA")
	val actorB = system.actorOf(Props[actorB], "actorB")
	val actorC = system.actorOf(Props[actorC], "actorC")
	val actorD = system.actorOf(Props[actorD], "actorD")

	implicit val timeout = Timeout(5 seconds) // needed for `?` below

	val f: Future[Result] =
		for {
			x <- ask(actorA, Request).mapTo[Int] // call pattern directly
			s <- (actorB ask Request).mapTo[String] // call by implicit conversion
			d <- (actorC ? Request).mapTo[Double] // call by symbolic name
		} yield Result(x, s, d)

	f pipeTo actorD // .. or ..
	//pipe(f) to actorD
	println("End of main execution")
}
