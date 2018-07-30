package co.com.delivery.service.services

import co.com.delivery.service.Entities.{Coordinate, North, Position}
import co.com.delivery.service.Services.DeliveryServiceInterpretation
import org.scalatest.FunSuite


class DeliveryService extends FunSuite{

  test("A delivery"){

    var posicionFinal: Position = DeliveryServiceInterpretation.makeDeliveries(DeliveryServiceInterpretation.readIn("AAAAIAAD"))
    assert(Position(Coordinate(-2,4), North()) ==  posicionFinal)
  }
}
