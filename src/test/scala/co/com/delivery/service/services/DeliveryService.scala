package co.com.delivery.service.services

import co.com.delivery.service.Entities._
import co.com.delivery.service.Services.DeliveryServiceInterpretation
import org.scalatest.FunSuite


class DeliveryService extends FunSuite{

  test("Init a delivery"){
    val drone: Drone = Drone(1, 10, Position(Coordinate(0,0), North()),List(Position(Coordinate(0,0), North())))
    var droneRetorno: Drone = DeliveryServiceInterpretation.makeDeliveries(drone, DeliveryServiceInterpretation.readIn("AAAAIAAD"))
    assert(Position(Coordinate(-2,4), North()) == droneRetorno.position)
  }

  test("Continue delivery from previous step"){

    var drone: Drone = Drone(1, 10, Position(Coordinate(-2,4), North()), List(Position(Coordinate(0,0), North())))
    drone = DeliveryServiceInterpretation.makeDeliveries(drone, DeliveryServiceInterpretation.readIn("DDAIAD"))
    assert(Position(Coordinate(-1,3), South()) == drone.position)
  }

  test("Continue delivery from previous step 2"){

    var drone: Drone = Drone(1, 10, Position(Coordinate(-1,3), South()),List(Position(Coordinate(0,0), North())))
    drone = DeliveryServiceInterpretation.makeDeliveries(drone, DeliveryServiceInterpretation.readIn("AAIADAD"))
    assert(Position(Coordinate(0,0), West()) == drone.position)
  }

  /*test("Continue delivery from previous step 3"){

    var posicionFinal: Position = DeliveryServiceInterpretation.makeDeliveries(DeliveryServiceInterpretation.readIn("AAAAIAAD"))
    assert(Position(Coordinate(-2,4), North()) ==  posicionFinal)
  }*/
  test("Full delivery"){

    var drone: Drone = Drone(1, 10, Position(Coordinate(0,0), North()), List(Position(Coordinate(0,0), North())))
    drone = DeliveryServiceInterpretation.makeDeliveries(drone, Route(List(Delivery(List(A(), A(), A(), A(), I(), A(), A(), D())), Delivery(List(D(), D(), A(), I(), A(), D())), Delivery(List(A(), A(), I(), A(), D(), A(), D())))))
    assert(Position(Coordinate(0,0), West()) == drone.position)
  }

  test("Full delivery with txt reading"){

    var drone: Drone = Drone(1, 10, Position(Coordinate(0,0), North()), List(Position(Coordinate(0,0), North())))
    drone = DeliveryServiceInterpretation.makeDeliveries(drone, DeliveryServiceInterpretation.readInTxt("/home/s4n/Documents/in01.txt"))
    assert(Position(Coordinate(-5,2), North()) == drone.position)
  }

  test("Full delivery with n txt reading"){

    val pathsList: List[String] = List("/home/s4n/Documents/in01.txt", "/home/s4n/Documents/in02.txt", "/home/s4n/Documents/in01.txt")
    val dronesList: List[Drone] = DeliveryServiceInterpretation.makeRoutes(DeliveryServiceInterpretation.readAllPaths(pathsList))
    //println(dronesList)
    assert(Position(Coordinate(-5,2), North()) == (dronesList.head).position)
    assert(Position(Coordinate(-5,1), North()) == (dronesList.tail.head).position)
    assert(Position(Coordinate(-5,2), North()) == (dronesList.tail.tail.head).position)
  }
}
