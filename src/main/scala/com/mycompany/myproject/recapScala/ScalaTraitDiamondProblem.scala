package com.mycompany.myproject.recapScala

object ScalaTraitDiamondProblem extends TraitA with TraitB {

  def main(args: Array[String]): Unit = {

    ScalaTraitDiamondProblem.display()
  }

}


trait TraitParent {

  def display();

}

trait TraitA extends TraitParent{

  override def display(): Unit ={
    println("Printing from Trait A...")
  }

}

trait TraitB extends  TraitParent {

  override def display(): Unit ={
    println("Printing from Trait B...")
  }

}



