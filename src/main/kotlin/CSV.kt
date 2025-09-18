import java.nio.file.Files
import java.nio.file.Path
import java.io.File
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter

data class Player(val player_id: Int, val nickname: String, val
fullName: String, val riego: Int, val altura: Double)

fun main() {
    val entradaCSV = Path.of("documentos/mis_plantas.csv")
    val salidaCSV = Path.of("documentos/mis_plantas2.csv")
    val datos: List<Player>
    datos = leerDatosInicialesCSV(entradaCSV)
    for (dato in datos) {

        println(" - ID: ${dato.player_id}, Nombre común: ${dato.nickname}, Nombre científico: ${dato.fullName}, Frecuencia de riego: ${dato.riego} días, Altura: ${dato.altura} metros")
    }
    escribirDatosCSV(salidaCSV, datos)
}

fun leerDatosInicialesCSV(ruta: Path): List<Player>
{
    var players: List<Player> =emptyList()
// Comprobar si el fichero es legible antes de intentar procesarlo.
    if (!Files.isReadable(ruta)) {
        println("Error: No se puede leer el fichero en la ruta: $ruta")
    } else{
// Configuramos el lector de CSV con el delimitador
        val reader = csvReader {
            delimiter = ';'
        }
        /* Leemos TODO el fichero CSV.
        El resultado es una lista de listas de Strings (`List<List<String>>`),
        donde cada lista interna representa una fila del fichero.*/
        val filas: List<List<String>> = reader.readAll(ruta.toFile())
        /* Convertir la lista de texto plano en una lista de objetos 'Planta'.
        `mapNotNull` funciona como un `map` y descartando todos los `null` de la
        lista final. Si una fila del CSV es inválida, devolvemos `null`
        y `mapNotNull` se encarga de ignorarla. */
        players = filas.mapNotNull { columnas ->
// Validar si La fila tiene al menos 4 columnas.
            if (columnas.size >= 5) {
                try {
                    val id_planta = columnas[0].toInt()
                    val nombre_comun = columnas[1]
                    val nombre_cientifico = columnas[2]
                    val riego = columnas[3].toInt()
                    val altura = columnas[4].toDouble()
                    Player(id_planta,nombre_comun, nombre_cientifico, riego, altura)
//crear el objeto Planta
                } catch (e: Exception) {
                    /* Si ocurre un error en la conversión (ej: NumberFormatException),
                    capturamos la excepción, imprimimos un aviso (opcional)
                    y devolvemos `null` para que `mapNotNull` descarte esta fila. */
                    println("Fila inválida ignorada: $columnas -> Error: ${e.message}")
                    null
                }
            } else {
// La fila no tiene suficientes columnas, es inválida. Devolver null.
                println("Fila con formato incorrecto ignorada: $columnas")

                null
            }
        }
    }
    return players
}

fun escribirDatosCSV(ruta: Path, players: List<Player>){
    try {
        val fichero: File = ruta.toFile()
        csvWriter {
            delimiter = ';'
        }.writeAll(
            players.map { planta ->
                listOf(planta.player_id.toString(),
                    planta.nickname,
                    planta.fullName,
                    planta.riego.toString(),
                    planta.altura.toString())
            },
            fichero
        )
        println("\nInformación guardada en: $fichero")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}