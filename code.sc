//-=-=-=-=-=-=-=-==-==-==-==-=-=-=-=-=-=-
//Subtype
//-=-=-=-=-=-=-=-==-==-==-==-=-=-=-=-=-=-
trait Food {
  final val isEdible = true

  def name: String

  def minToPrep: Int
}

class Taco extends Food {
  val name: String = "taco"

  def minToPrep: Int = 6
}

class Cereal extends Food {
  val name: String = "cereal"

  def minToPrep: Int = 1
}

val t = new Taco
val c = new Cereal

t.isEdible //true
c.isEdible //true
t.minToPrep //6
c.minToPrep //1


//-=-=-=-=-=-=-=-==-==-==-==-=-=-=-=-=-=-
//Parametric
//-=-=-=-=-=-=-=-==-==-==-==-=-=-=-=-=-=-
trait List[+A]

object Nil extends List[Nothing]

class Cons[A](val head: A, val tail: List[A]) extends List[A]

val elem: List[Int] = new Cons(1, Nil)
//val elem: List[Int] = Nil

elem match {
  case n: Nil.type => println("empty")
  case c: Cons[Int] => println(c.head)
}

val intList = new Cons(2, new Cons(1, Nil))
val strList = new Cons("two", new Cons("one", Nil))
val foodList = new Cons(new Taco, new Cons(new Cereal, Nil))
foodList.head.name // taco


//-=-=-=-=-=-=-=-==-==-==-==-=-=-=-=-=-=-
//Ad-hoc
//-=-=-=-=-=-=-=-==-==-==-==-=-=-=-=-=-=-
def printTexture[T](t: T)(implicit o: Texture[T]): Unit = {
  println(o.getTexture(t))
}

class Silk

trait Texture[T] {
  def getTexture(t: T): String
}

object Texture {

  implicit object TacoTexture extends Texture[Taco] {
    override def getTexture(t: Taco) = "crunchy"
  }

  object WetTacoTexture extends Texture[Taco] {
    override def getTexture(t: Taco) = "soggy"
  }

  implicit object CerealTexture extends Texture[Cereal] {
    override def getTexture(c: Cereal) = "liquidy"
  }

  implicit object SilkTexture extends Texture[Silk] {
    override def getTexture(s: Silk) = "soft"
  }

}


import Texture._
val taco = new Taco
printTexture(taco)
val wetTaco = new Taco
printTexture(taco)(WetTacoTexture)
val cereal = new Cereal
printTexture(cereal)
val silk = new Silk
printTexture(silk)

//def printTexture[A: Texture](t: A): Unit = {
//  val o = implicitly[Texture[A]]
//  println(o.getTexture(t))
//}
