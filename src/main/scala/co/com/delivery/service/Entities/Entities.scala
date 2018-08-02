package co.com.delivery.service.Entities

//import com.sun.xml.internal.ws.dump.LoggingDumpTube.Position

case class Drone(id: Int, capacity: Int, position: Position, deliveriesPositions: List[Position])

sealed trait Movement{
  def applyMovement(position: Position): Position
}

//A.apply

  case class A() extends Movement{
    def applyMovement(position: Position): Position ={
      //var positionReturn = Position
      position.orientation match {
        case North() => Position(Coordinate(position.coordinate.x,position.coordinate.y + 1),position.orientation)
        case South() => Position(Coordinate(position.coordinate.x,position.coordinate.y - 1),position.orientation)
        case West() => Position(Coordinate(position.coordinate.x - 1,position.coordinate.y),position.orientation)
        case East() => Position(Coordinate(position.coordinate.x + 1,position.coordinate.y),position.orientation)
        //case _ => throw new Exception(s"Caracter invalido para creacion de instruccion: $c")
      }

    }
  }

  case class I() extends Movement{
    def applyMovement(position: Position): Position ={
      position.orientation match {
        case North() => Position(Coordinate(position.coordinate.x,position.coordinate.y), West())
        case South() => Position(Coordinate(position.coordinate.x,position.coordinate.y),East())
        case West() => Position(Coordinate(position.coordinate.x,position.coordinate.y),South())
        case East() => Position(Coordinate(position.coordinate.x,position.coordinate.y),North())
        //case _ => throw new Exception(s"Caracter invalido para creacion de instruccion: $c")
      }
    }
  }
  case class D() extends Movement{
    def applyMovement(position: Position): Position ={
      position.orientation match {
        case North() => Position(Coordinate(position.coordinate.x,position.coordinate.y),East())
        case South() => Position(Coordinate(position.coordinate.x,position.coordinate.y),West())
        case West() => Position(Coordinate(position.coordinate.x ,position.coordinate.y),North())
        case East() => Position(Coordinate(position.coordinate.x,position.coordinate.y),South())
        //case _ => throw new Exception(s"Caracter invalido para creacion de instruccion: $c")
      }
    }
  }

sealed trait Orientation
  case class North() extends Orientation {
    override def toString = "North"
  }
  case class South() extends Orientation {
    override def toString = "South"
  }
  case class West() extends Orientation {
    override def toString = "West"
  }
  case class East() extends Orientation {
    override def toString = "East"
  }

case class Coordinate(x: Int, y: Int)

case class Position(coordinate: Coordinate, orientation: Orientation)

case class Delivery(direction: List[Movement])

case class Route(deliveries: List[Delivery])

//case class Position(coordinate: Coordinate, orientation: Orientation)  extends


/*case class Persona(dni:String, nombre:String)
case class Asegurado(dni:String, nombre:String)
case class Cobertura(cdgarantia:String, cdsubgarantia:String)
case class Tarifa(prima:Int)*/
