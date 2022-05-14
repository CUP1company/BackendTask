// Code by: Tikhon Lyamin, 16 years old

import java.lang.Exception
import kotlin.properties.Delegates.notNull


private var balance by notNull<Int>()
private var line by notNull<String>()

private var allBooks = ArrayList<ArrayList<String>>()
private var myBooks = ArrayList<String>()



fun main() {
    line = readLine().toString()
    balance = line.substring(9,line.indexOf(",")).toInt()
    infBooks(line)

    line = readLine().toString()
    while (line != "exit"){
        val comands: MutableMap<String,Any> = linkedMapOf(
            "buy" to true,
            "print balance" to balance(),
            "show books in stock" to showBooks(),
            "show bought books" to showBoughtBooks(),
        )

        println(
            if (line.length>3){
                if (comands[line.substring(0,3)] == true){
                    buy()
                }else {comands.getOrDefault(line,
                    "I don't understand")}
            }else{"I don't understand"}
        )
        line = readLine().toString()
    }
}

fun showBooks(): String {
    val showbooks = ArrayList<String>()
    for (i in allBooks){
        if (i[1].toInt()==0){
            continue
        }else{
            val returnOutp = "\"${i[2]}\", ${i[1]} шт., ${i[0]} руб."
            showbooks.add(returnOutp)
        }
    }
    var outputString = ""
    for(i in showbooks){ outputString+="$i\n"}
    if(outputString == ""){
        outputString = "All books sold out."
    }
    return outputString.trim()
}

fun showBoughtBooks(): String {
    var returnValue = ""

    if (myBooks.isEmpty()){
        returnValue = "There are no books here."
    }else{ for(i in myBooks){ returnValue+="$i\n"} }

    return returnValue.trim()
}

fun balance(): Any {
    return "balance: $balance"
}

fun infBooks(boks:String) {
    var all = boks.substring(boks.indexOf(",")+9, boks.length)

    all = all
        .replace("[","")
        .replace("]","")
        .replace("(","")

    all = all.substring(0,all.length-1)
    val arr: List<String> = all.split("), ")
    for (i in arr){
        val firstBook = ArrayList<String>()
        var str = i.substring(1,i.length)
        val nameOfBook = str.substring(0,str.indexOf("\""))

        str = str.substring(str.indexOf("\"")+3,str.length)

        val howManyBooks = str.substring(0,str.indexOf(","))
        val cost = str.substring(str.indexOf(",")+2,str.length)

        firstBook.add(cost)
        firstBook.add(howManyBooks)
        firstBook.add(nameOfBook)

        allBooks.add(firstBook)
    }
}

fun buy(): String {
    var buyAccept = "I don't understand"
    line = line.substring(5,line.length)
    try {
        for (i in allBooks){
            if (i[2] == line.substring(0,line.indexOf("\""))){
                val numOfBooks = line.substring(line.indexOf("\"")+2,line.length).toInt()
                val numOfShopBooks = i[1].toInt()

                if (numOfShopBooks >= numOfBooks){
                    val costAll = i[0].toInt() * numOfBooks

                    if (costAll<=balance){
                        balance -= costAll
                        i[1] = (i[1].toInt() - numOfBooks).toString()
                        myBooks.add("\"${i[2]}\", $numOfBooks шт.")

                        buyAccept = "deal"

                    }else{buyAccept = "no deal"}
                }else{buyAccept = "no deal"}
            }
        }

    }catch (e: Exception) {
        buyAccept = "I don't understand"
    }

    return buyAccept
}




