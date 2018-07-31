package co.com.delivery.service.Services

import java.util.concurrent.Executors

import co.com.delivery.service.Entities._

import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source


// API Algebra
  sealed trait deliveryServiceAlgebra{
    //def readIn(inTxt: String):Future[Route]
    //def makeDeliveries(routeFuture: Future[Route]): Position
    def readIn(urlIntxt: String):Route
    def readInTxt(urlIntxt: String):Route
    def makeDelivery(position: Position, delivery: Delivery): List[Position]
    def makeDeliveries(drone: Drone, route: Route): Drone
    def writeOut(deliveriesOutputs: List[String]):String
  }


  // Algebra interpretation
  sealed trait DeliveryServiceInterpretation extends deliveryServiceAlgebra{
    //implicit val droneDelivery = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(20))
    //override def readIn(deliveriesInstructions: String):Future[Route] = {
    def readInTxt(urlInTxt: String):Route = {
      //val bufferedSource = Source.fromFile("/home/s4n/Documents/in01.txt")
      val bufferedSource = Source.fromFile(urlInTxt)
      val lines = bufferedSource.getLines()
      val deliveriesList: List[Delivery] = (lines.filter(_.nonEmpty).map(x => x.toList)).map(y =>
        Delivery(y.map(z => z match {
          case 'A' => A()
          case 'I' => I()
          case 'D' => D()
          //case _ => throw new Exception(s"Caracter invalido para creacion de instruccion: $c")
        }
      )))
      /*var deliveriesInstructions: List[String] = List("")
      for (line <- bufferedSource.getLines) {
        deliveriesInstructions = deliveriesInstructions :: line.toUpperCase
      }
*/
      bufferedSource.close

      /*val deliveriesList: List[Delivery] = List(Delivery((deliveriesInstructions.toCharArray.toList).map(x =>
        x match {
          case 'A' => A()
          case 'I' => I()
          case 'D' => D()
          //case _ => throw new Exception(s"Caracter invalido para creacion de instruccion: $c")
        }
      )))*/
      Route(deliveriesList)
    }
    override def readIn(deliveriesInstructions: String):Route = {
      val deliveriesList: List[Delivery] = List(Delivery((deliveriesInstructions.toCharArray.toList).map(x =>
        x match {
          case 'A' => A()
          case 'I' => I()
          case 'D' => D()
          //case _ => throw new Exception(s"Caracter invalido para creacion de instruccion: $c")
        }
      )))
      Route(deliveriesList)
      //Future(Route(deliveriesList))(droneDelivery)
    }

    //override def makeDeliveries(routeFuture: Future[Route]):Position = {
    /*override def makeDeliveries(drone: Drone, route: Route):Drone = {
      //val initialPosition: Position = Position.apply(Coordinate(0,0),North())
      //val id: Int = 1
      //val capacity: Int = 10
      //var drone: Drone(id, capacity, initialPosition, route)
      var dronePosition: Position = drone.position
      println(route)
      println(drone)
      //Future(Poliza(numero, List(Cobertura("1", "1.1"))))
      //routeFuture.map(x =>
        //(x.deliveries).map(y =>}
      (route.deliveries).map(y =>
          (y.direction).map(z => dronePosition= z.applyMovement(dronePosition)))
      /*println(route.map(y =>
          (y.direction).map(z => dronePosition= z.applyMovement(dronePosition)))))*/
      val droneRetorno: Drone = Drone(1,10,dronePosition)
      droneRetorno
    }*/

    override def makeDeliveries(drone: Drone, route: Route): Drone ={
      //val dronePosition: Position = drone.position
      val dronePositionFinal: List[Position] = (route.deliveries).foldLeft(List(drone.position)){(dronePosition, movement)
        => makeDelivery(dronePosition.head, movement)}
      println(dronePositionFinal)
      /*println(route.map(y =>
          (y.direction).map(z => dronePosition= z.applyMovement(dronePosition)))))*/
      val droneRetorno: Drone = Drone(1,10,dronePositionFinal.last)
      droneRetorno
    //makeDeliverie(drone,)
    }

    override def makeDelivery(dronePosition: Position, delivery: Delivery):List[Position] = {
      //val dronePosition: Position = drone.position
      //val id: Int = 1
      //val capacity: Int = 10
      //var drone: Drone(id, capacity, initialPosition, route)
      //var dronePosition: Position = drone.position
      println(delivery)
      println(dronePosition)
      //Future(Poliza(numero, List(Cobertura("1", "1.1"))))
      //routeFuture.map(x =>
      //(x.deliveries).map(y =>}}
      //--------
      /*lista.foldLeft("") { (resultado, item) =>
        cont = cont + 1
        resultado + cont + "." + item + ","
      }*/
      //------
      val dronePositionFinal: List[Position] = List((delivery.direction).foldLeft(dronePosition){ (dronePosition, movement) =>
          movement.applyMovement(dronePosition)})
      println(dronePositionFinal)
      /*println(route.map(y =>
          (y.direction).map(z => dronePosition= z.applyMovement(dronePosition)))))*/
      //val droneRetorno: Drone = Drone(1,10,dronePositionFinal.last)
      //val dronePositionFinal: Position = dronePositionTemp.head
      dronePositionFinal
    }

    override def writeOut(deliveriesOutputs: List[String]):String = {
      //Future(Poliza(numero, List(Cobertura("1", "1.1"))))
      "Hola"
    }
  }


  // Trait Object
  object DeliveryServiceInterpretation extends DeliveryServiceInterpretation


