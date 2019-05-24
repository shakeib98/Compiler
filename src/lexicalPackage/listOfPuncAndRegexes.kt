package lexicalPackage

//Punctuator list

val punctuatorList = hashSetOf(".",",","(",")","{","}","[","]",":",";")

//Regexes

const val patternIdentifier = "(([A-Za-z]+|(_[A-Za-z0-9]))+([A-Za-z0-9]*))"

const val patternInt = "([+-]?[0-9]+)"

const val patternFloat = "([+-]?([0-9]*)?[.][0-9]+)"

//const val patternString = "[\"]+([\\w]+|[\\W&&[^\\\\]]+|(\\\\[\\\\\"nt[\\W&&[^\\\\]]]))*([^\\\\])[\"]+"

const val patternString = "[\"]+([\\w]*|[\\W&&[^\\\\]]*|(\\\\[\\\\\"nt])*|(\\\\[\\\\])*)*[\"]+"