package co.com.delivery.service.Services

import java.io.{File, PrintWriter}
import java.util.concurrent.Executors

import co.com.delivery.service.Entities._

import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source


// API Algebra
  sealed trait deliveryServiceAlgebra{
    def readAllPaths(pathsList: List[String]):List[Route]
    def readIn(urlIntxt: String):Route
    def readInTxt(urlIntxt: String):Route
    def makeRoutes(routesList: List[Route]):List[Drone]
    def makeDelivery(drone: Drone, delivery: Delivery): List[Drone]
    def makeDeliveries(drone: Drone, route: Route): Drone
    def writeOut(droneWithdeliveriesOutputs: Drone)
  }


  // Algebra interpretation
  sealed trait DeliveryServiceInterpretation extends deliveryServiceAlgebra{
    override def readAllPaths(pathsList: List[String]):List[Route]={
      val routesList: List[Route] = pathsList.map(x =>  readInTxt(x))
      routesList
    }


    override def readInTxt(urlInTxt: String):Route = {
      val bufferedSource = Source.fromFile(urlInTxt)
      val lines = bufferedSource.getLines()
      val deliveriesList: List[Delivery] = (lines.filter(_.nonEmpty).map(x => x.toList)).toList.map(y =>
        Delivery(y.map(z => z match {
          case 'A' => A()
          case 'I' => I()
          case 'D' => D()
        })
      ))
      bufferedSource.close
      Route(deliveriesList)
    }

    override def readIn(deliveriesInstructions: String):Route = {
      val deliveriesList: List[Delivery] = List(Delivery((deliveriesInstructions.toCharArray.toList).map(x =>
        x match {
          case 'A' => A()
          case 'I' => I()
          case 'D' => D()
        }
      )))
      Route(deliveriesList)
    }

    override def makeRoutes(routesList: List[Route]):List[Drone]={
      println(routesList.size)
      val droneIdentificators: List[Int] = Range(1,routesList.size + 1).toList
      val routeListZipDroneIdentificators: List[(Route, Int)] = routesList.zip(droneIdentificators)
      //println(routeListZipDroneIdentificators)
      val dronesList: List[Drone] = routeListZipDroneIdentificators.map(routeIdentified => makeDeliveries(Drone(routeIdentified._2, 10, Position(Coordinate(0,0), North()), List(Position(Coordinate(0,0), North()))),routeIdentified._1))
      val dronesListToReturn: List[Drone] = dronesList
      dronesList.map(x => writeOut(x))
      dronesListToReturn
      }


    override def makeDeliveries(drone: Drone, route: Route): Drone ={
      val dronePositionFinal: List[Drone] = (route.deliveries).foldLeft(List(drone)){(dronePosition, movement)
        => makeDelivery(dronePosition.head, movement)}
      println(dronePositionFinal)
      val droneReturn: Drone = Drone(drone.id,drone.capacity, (dronePositionFinal.head).position, (dronePositionFinal.head).deliveriesPositions)
      droneReturn
    }

    override def makeDelivery(drone: Drone, delivery: Delivery):List[Drone] = {
      val dronePositionFinal: List[Position] = List((delivery.direction).foldLeft(drone.position){ (dronePosition, movement) =>
        movement.applyMovement(dronePosition)})
      //println(dronePositionFinal)
      val dronePositionUpdated: List[Drone] = List(Drone(drone.id, drone.capacity, dronePositionFinal.head, (drone.deliveriesPositions):+ dronePositionFinal.head))
      dronePositionUpdated
    }

    override def writeOut(droneWithdeliveriesOutputs: Drone) {
        val printWriter= new PrintWriter(new File(s"out${droneWithdeliveriesOutputs.id}.txt"))
        println(droneWithdeliveriesOutputs)
        printWriter.write("==Delivery Report==")
      (droneWithdeliveriesOutputs.deliveriesPositions).tail.map(y=>printWriter.write(s"\n(${y.coordinate.x},${y.coordinate.y}) Orientation ${y.orientation}"))
        printWriter.close()

    }
  }


  // Trait Object
  object DeliveryServiceInterpretation extends DeliveryServiceInterpretation


