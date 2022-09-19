package com.github.p2m2.api

import scala.util.{Failure, Success, Try}


case object PubChemPugRestAPI {
  val baseUrl : String = "https://pubchem.ncbi.nlm.nih.gov/rest/pug"

  def send(request: String): String = scala.io.Source.fromURL(PubChemPugRestAPI.baseUrl + "/" + request).mkString.trim
  def get(request: String, trySend: Int=5): String =
    Try(send(request)) match {
      case Success(r) => r
      case Failure(_) if trySend >= 0 => get(request,trySend-1)
      case Failure(e) => throw e
  }
  def compound =  CompoundDomainNamespacePubChemPugRest("compound")
}

sealed abstract class PubChemPugRestAPIRequest(request : String = "") {
}

/**
 * doc : https://pubchemdocs.ncbi.nlm.nih.gov/pug-rest
 */

case class CompoundDomainNamespacePubChemPugRest(
                                                  request : String
                                                  /*
                                              cid : String = "",
                                              name : String = "",
                                              smiles : String = "",
                                              inchi : String = "",
                                              sdf : String = "",
                                              inchikey : String = "",
                                              formula : String = "",*/
                                                  // <structureSearch> | <xref> | listkey : Seq(String = Seq() )| <fast search>) {
                                            ) extends PubChemPugRestAPIRequest(request)
{
  def cid = IdentifierCompoundDomainPubChemPugRest(request+"/cid")
  def name = IdentifierCompoundDomainPubChemPugRest(request+"/name")
  def smiles = IdentifierCompoundDomainPubChemPugRest(request+"/smiles")
  def inchi = IdentifierCompoundDomainPubChemPugRest(request+"/inchi")
  def sdf = IdentifierCompoundDomainPubChemPugRest(request+"/sdf")
  def inchikey = IdentifierCompoundDomainPubChemPugRest(request+"/inchikey")
  def formula = IdentifierCompoundDomainPubChemPugRest(request+"/formula")

  // <structure search>
  def substructure = StructureSearchPubChemPugRest(request+"/substructure")
  def superstructure = StructureSearchPubChemPugRest(request+"/superstructure")
  def similarity = StructureSearchPubChemPugRest(request+"/similarity")
  def identity = StructureSearchPubChemPugRest(request+"/identity")

  def listkey = IdentifierCompoundDomainPubChemPugRest(request+"/listkey")

  def xref  = XrefPubChemPugRest(request+"/xref")
  // <fast search>
  def fastidentity = FastSearchPubChemPugRest(request+"/fastidentity")
  def fastsimilarity_2d = FastSearchPubChemPugRest(request+"/fastsimilarity_2d")
  def fastsimilarity_3d = FastSearchPubChemPugRest(request+"/fastsimilarity_3d")
  def fastsubstructure = FastSearchPubChemPugRest(request+"/fastsubstructure")
  def fastsuperstructure = FastSearchPubChemPugRest(request+"/fastsuperstructure")

  def fastformula = IdentifierCompoundDomainPubChemPugRest(request+"/fastformula")
}

case class StructureSearchPubChemPugRest(request : String) extends PubChemPugRestAPIRequest(request) {
  def cid = IdentifierCompoundDomainPubChemPugRest(request+"/cid")
  def smiles = IdentifierCompoundDomainPubChemPugRest(request+"/smiles")
  def inchi = IdentifierCompoundDomainPubChemPugRest(request+"/inchi")
  def sdf = IdentifierCompoundDomainPubChemPugRest(request+"/sdf")
}

case class FastSearchPubChemPugRest(request : String) extends PubChemPugRestAPIRequest(request) {
  def cid = IdentifierCompoundDomainPubChemPugRest(request+"/cid")
  def smiles = IdentifierCompoundDomainPubChemPugRest(request+"/smiles")
  def smarts = IdentifierCompoundDomainPubChemPugRest(request+"/smarts")
  def inchi = IdentifierCompoundDomainPubChemPugRest(request+"/inchi")
  def sdf = IdentifierCompoundDomainPubChemPugRest(request+"/sdf")
}

case class XrefPubChemPugRest(request : String) extends PubChemPugRestAPIRequest(request) {
  def RegistryID = IdentifierCompoundDomainPubChemPugRest(request+"/RegistryID")
  def RN = IdentifierCompoundDomainPubChemPugRest(request+"/RN")
  def PubMedID = IdentifierCompoundDomainPubChemPugRest(request+"/PubMedID")
  def MMDBID = IdentifierCompoundDomainPubChemPugRest(request+"/MMDBID")
  def ProteinGI = IdentifierCompoundDomainPubChemPugRest(request+"/ProteinGI")
  def NucleotideGI = IdentifierCompoundDomainPubChemPugRest(request+"/NucleotideGI")
  def TaxonomyID = IdentifierCompoundDomainPubChemPugRest(request+"/TaxonomyID")
  def MIMID = IdentifierCompoundDomainPubChemPugRest(request+"/MIMID")
  def GeneID = IdentifierCompoundDomainPubChemPugRest(request+"/GeneID")
  def ProbeID = IdentifierCompoundDomainPubChemPugRest(request+"/ProbeID")
  def PatentID = IdentifierCompoundDomainPubChemPugRest(request+"/PatentID")

}

case class IdentifierCompoundDomainPubChemPugRest(request : String) extends PubChemPugRestAPIRequest(request) {
  def id(identifier : String) = OperationCompoundDomainPubChemPugRest(request+"/"+identifier)
}

case class OperationCompoundDomainPubChemPugRest(request : String) extends PubChemPugRestAPIRequest(request) {
  def record = OutputPubChemPugRest(request+"/record")
  def synonyms = OutputPubChemPugRest(request+"/synonyms")
  def sids = OutputPubChemPugRest(request+"/sids")
  def cids = OutputPubChemPugRest(request+"/cids")
  def aids = OutputPubChemPugRest(request+"/aids")
  def assaysummary = OutputPubChemPugRest(request+"/assaysummary")
  def classification = OutputPubChemPugRest(request+"/classification")
  def description = OutputPubChemPugRest(request+"/description")
  def conformers = OutputPubChemPugRest(request+"/conformers")

  def property(propertyTags : List[String]) = OutputPubChemPugRest(request+"/property/"+propertyTags.mkString(","))
  def xrefs(xrefTags : List[String]) = OutputPubChemPugRest(request+"/xrefs/"+xrefTags.mkString(","))
}


case class OutputPubChemPugRest(request : String) extends PubChemPugRestAPIRequest(request) {
  def XML : scala.xml.Elem = scala.xml.XML.loadString(PubChemPugRestAPI.get(request + "/XML"))
  def ASNT : String = PubChemPugRestAPI.get(request + "/ASNT")
  def ASNB : String = PubChemPugRestAPI.get(request + "/ASNB")
  def JSON : String = PubChemPugRestAPI.get(request + "/JSON")
 // def JSONP [ ?callback=<callback name> ]  : String = PubChemPugRestAPI.get(request + "/JSONP")
  def SDF : String = PubChemPugRestAPI.get(request + "/SDF")
  def CSV : String = PubChemPugRestAPI.get(request + "/CSV")
  def PNG : String = PubChemPugRestAPI.get(request + "/PNG")
  def TXT: String = PubChemPugRestAPI.get(request + "/TXT")
}
/*
case class InputCompoundDomainPubChemPugRest(


 */