package co.com.delivery.service.Services

import java.util.concurrent.Executors

import co.com.delivery.service.Entities._

import scala.concurrent.{ExecutionContext, Future}


// API Algebra
  sealed trait deliveryServiceAlgebra{
    def readIn(inTxt: String):Future[Route]
    def makeDeliveries(routeFuture: Future[Route]): Position
    def writeOut(deliveriesOutputs: List[String]):String
  }


  // Algebra interpretation
  sealed trait DeliveryServiceInterpretation extends deliveryServiceAlgebra{
    implicit val droneDelivery = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(20))
    override def readIn(deliveriesInstructions: String):Future[Route] = {
      val deliveriesList: List[Delivery] = List(Delivery((deliveriesInstructions.toCharArray.toList).map(x =>
        x match {
          case 'A' => A()
          case 'I' => I()
          case 'D' => D()
          //case _ => throw new Exception(s"Caracter invalido para creacion de instruccion: $c")
        }
      )))

      Future(Route(deliveriesList))(droneDelivery)
    }

    override def makeDeliveries(routeFuture: Future[Route]):Position = {
      val initialPosition: Position = Position.apply(Coordinate(0,0),North())
      var dronePosition: Position = initialPosition
      println(routeFuture)
      //Future(Poliza(numero, List(Cobertura("1", "1.1"))))
      routeFuture.map(x =>
        (x.deliveries).map(y =>
          (y.direction).map(z => dronePosition= z.applyMovement(dronePosition))))
      dronePosition
    }

    override def writeOut(deliveriesOutputs: List[String]):String = {
      //Future(Poliza(numero, List(Cobertura("1", "1.1"))))
      "Hola"
    }
  }


  // Trait Object
  object DeliveryServiceInterpretation extends DeliveryServiceInterpretation


