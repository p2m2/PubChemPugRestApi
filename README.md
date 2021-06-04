# PubChemPugRestApi

simple interface to reach https://pubchem.ncbi.nlm.nih.gov/rest/pug

## interface

```scala 
def getCompoundsWithFormula(formula: String) : Seq[CompoundPubChem]  
def getCompoundsWithName(name: String) : Seq[CompoundPubChem]
```

### low level example
only compound management is implemented.

```scala 
PubChemPugRestAPI.compound.listkey.id(ListKey).property(listProperty).JSON
```

## class

```scala 
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
```
