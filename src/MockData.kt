import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

val vornamen = arrayOf("Hans", "Friedrich-Wilhelm", "Lisbeth", "Marie", "Peter", "Annika", "Otto von", "Viktor", "Albert", "Sabine", "Frauke")
val namen = arrayOf("Müller", "Meier", "Fischer", "Bäcker", "Schmidt", "Schneider", "Weber", "Wagner", "Hoffmann", "Schäfer", "Koch")
val divIds = (1..3)
const val numKunden = 20
const val numWorkers = 10
const val numBestellungen = 300
const val numPositions = 8
const val numProducts = 10


fun main() {
    try {
        val con: Connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/prüfungsvorbereitung", "sa", "sa")
        //mockDivs(con)
        //mockKunden(con)
        //mockWorkers(con)
        //mockOrders(con)
        //mockPositions(con)
        con.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun mockPositions(con: Connection) {
    val orderMap: MutableMap<Int, Set<Pair<Int,Int>>> = mutableMapOf()
    (1..numBestellungen).forEach {
        val items: MutableSet<Pair<Int,Int>> = mutableSetOf()
        (1..numPositions).forEach {
            items.add( (1..numProducts).random() to (1..20).random() )
        }
        orderMap[it] = items
    }

    orderMap.forEach { entry ->
        val stmt: Statement = con.createStatement()
        entry.value.distinctBy { it.first }.forEach {
            stmt.executeUpdate("insert into bestellpositionen (best_id, produkt_id, menge) values ('${entry.key}', '${it.first}', '${it.second}')")
        }
    }
}

fun mockDivs(con: Connection) {
    val divNames = listOf("Personal", "Finanzen", "Vertrieb")
    divNames.forEach {
        val stmt: Statement = con.createStatement()
        stmt.executeUpdate("insert into abteilungen (name) values ('$it')")
    }
}

fun mockKunden(con: Connection) {
    val combinations = mutableListOf<Pair<String,String>>()
    repeat(numKunden) {
        val rvname = vornamen.random()
        val rname = namen.random()
        combinations.add(rvname to rname)
    }

    combinations.forEach {
        val stmt: Statement = con.createStatement()
        stmt.executeUpdate("insert into kunden (name, vname) values ('${it.second}', '${it.first}')")
    }
}

fun mockWorkers(con: Connection) {
    val combinations = mutableListOf<Triple<String,String,Int>>()
    repeat(numWorkers) {
        val rvname = vornamen.random()
        val rname = namen.random()
        val rid = divIds.random()
        combinations.add(Triple(rvname, rname, rid))
    }

    combinations.forEach {
        val stmt: Statement = con.createStatement()
        stmt.executeUpdate("insert into angestellte (name, vname, abt_id) values ('${it.second}', '${it.first}', '${it.third}')")
    }
}

fun mockOrders(con: Connection) {
    val combinations = Array(numBestellungen) { Pair((1..numKunden).random(), (1..numWorkers).random()) }
    combinations.forEach {
        val stmt: Statement = con.createStatement()
        stmt.executeUpdate("insert into bestellungen (kunde_id, worker_id) VALUES (${it.first}, ${it.second})")
    }
}