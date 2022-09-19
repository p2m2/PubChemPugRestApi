package com.github.p2m2.api
import utest._


object PubChemPugRestAPITest extends TestSuite {

  val tests = Tests {

    test("Test get") {
      val r = PubChemPugRestAPI.get("compound/name/glucose/cids/TXT")
      assert(r == "5793")
    }

    test("name compound") {
      assert(scala.xml.XML.loadString(PubChemPugRestAPI.get("compound/name/aspirin/synonyms/XML"))
        == PubChemPugRestAPI.compound.name.id("aspirin").synonyms.XML)

      assert((PubChemPugRestAPI.get("compound/name/aspirin/synonyms/ASNT"))
        == PubChemPugRestAPI.compound.name.id("aspirin").synonyms.ASNT)

      assert((PubChemPugRestAPI.get("compound/name/aspirin/synonyms/ASNB"))
        == PubChemPugRestAPI.compound.name.id("aspirin").synonyms.ASNB)

      assert((PubChemPugRestAPI.get("compound/name/aspirin/synonyms/JSON"))
        == PubChemPugRestAPI.compound.name.id("aspirin").synonyms.JSON)
/*
 -- Post request --
      assert((PubChemPugRestAPI.get("compound/name/aspirin/synonyms/SDF"))
        == PubChemPugRestAPI.compound.name.id("aspirin").synonyms.SDF)

      assert((PubChemPugRestAPI.get("compound/name/aspirin/synonyms/CSV"))
        == PubChemPugRestAPI.compound.name.id("aspirin").synonyms.CSV)

      assert((PubChemPugRestAPI.get("compound/name/aspirin/synonyms/PNG"))
        == PubChemPugRestAPI.compound.name.id("aspirin").synonyms.PNG)
*/
      assert((PubChemPugRestAPI.get("compound/name/aspirin/synonyms/TXT"))
        == PubChemPugRestAPI.compound.name.id("aspirin").synonyms.TXT)

    }
    test("getCompoundsWithFormula") {
      PubChemRequest.getCompoundsWithFormula("C10H17O9NS2")
    }

    test("getCompoundsWithName") {
      PubChemRequest.getCompoundsWithName("Glucoalyssin")
    }

  }
}
