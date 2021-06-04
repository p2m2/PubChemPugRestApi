package com.github.p2m2.api
import upickle.default.{macroRW, ReadWriter => RW}

import scala.util.{Failure, Success}

object ResultsPubChemPugRestAPI{
  implicit val rw: RW[ResultsPubChemPugRestAPI] = macroRW
}
case class ResultsPubChemPugRestAPI(PropertyTable : PropertyTablePubChemPugRestAPI)

object PropertyTablePubChemPugRestAPI{
  implicit val rw: RW[PropertyTablePubChemPugRestAPI] = macroRW
}
case class PropertyTablePubChemPugRestAPI( Properties : Seq[CompoundPubChem])


case class CompoundPubChem(
                          CID : Int,
                          Title : String,
                          MolecularFormula : String ,
                          CanonicalSMILES : String ,
                          IsomericSMILES : String ,
                          InChI : String ,
                          InChIKey : String ,
                          IUPACName : String ,
                          ExactMass : Double,
                          MonoisotopicMass : Double,
                          Charge: Int)
object CompoundPubChem{
  implicit val rw: RW[CompoundPubChem] = macroRW
}

case object PubChemRequest {
  //https://pubchemdocs.ncbi.nlm.nih.gov/pug-rest$_Toc494865567
  val listProperty = List(
    "Title","MolecularFormula","CanonicalSMILES","IsomericSMILES","InChI","InChIKey","IUPACName","ExactMass","MonoisotopicMass","Charge"
  )

  def buildCompoundsWithJson(data: ujson.Value): ResultsPubChemPugRestAPI = {
    util.Try(upickle.default.read[ResultsPubChemPugRestAPI](data))
    match {
      case Success(v) => v
      case Failure(e) => {
        println(e)
        ResultsPubChemPugRestAPI(PropertyTablePubChemPugRestAPI(Seq()))
      }
    }
  }

  def getResults(ListKey : String, test : Int = 5) : ResultsPubChemPugRestAPI = {
    println(s" -- getResults -- test $test")
    Thread.sleep(100)
    val data = PubChemPugRestAPI.compound.listkey.id(ListKey).property(listProperty).JSON
    util.Try(upickle.default.read[ResultsPubChemPugRestAPI](data))
    match {
      case Success(v) => println("success");v
      case Failure(e) => {
        if ( test > 0 )
          getResults(ListKey,test-1)
        else
          ResultsPubChemPugRestAPI(PropertyTablePubChemPugRestAPI(Seq()))
      }
    }
  }

  def getCompoundsWithFormula(formula: String) : Seq[CompoundPubChem] = {
    println(s"getCompoundWithFormula($formula)")
    val data = ujson.read(PubChemPugRestAPI.compound.formula.id(formula).description.JSON)
    val ListKey = data("Waiting")("ListKey").str
    val message = data("Waiting")("Message").str

    val results : ResultsPubChemPugRestAPI = getResults(ListKey)
    results.PropertyTable.Properties
  }

  def getCompoundsWithName(name: String) : Seq[CompoundPubChem] = {
    println(s"getCompoundsWithName($name)")
    val data = ujson.read(PubChemPugRestAPI.compound.name.id(name).property(listProperty).JSON)
    val results : ResultsPubChemPugRestAPI = buildCompoundsWithJson(data)
    results.PropertyTable.Properties
  }
}
