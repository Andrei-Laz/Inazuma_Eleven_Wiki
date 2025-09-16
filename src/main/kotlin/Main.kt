package org.example
import java.nio.file.Files
import java.nio.file.Path
import java.util.Arrays.stream

fun main() {
    val carpetaPrincipal = Path.of("./")
    val carpetaPersonajes = Path.of("Players")
    val carpetaTecnicas = Path.of("Hissatsus")

    var checkPlayers = false
    var checkHissatsus= false

    val ficheroNombres = Path.of("Players/Player_Names.txt")

    try {
        Files.list(carpetaPrincipal).use {stream ->
            stream.sorted().forEach { carpetas ->

                if (carpetas.fileName.toString().equals(carpetaPersonajes.fileName.toString())) {
                    checkPlayers = true
                }
                if (carpetas.fileName.toString().equals(carpetaTecnicas.fileName.toString())) {
                    checkHissatsus = true
                }
            }
        }

        println(checkPlayers)
        println(checkHissatsus)

        if (Files.notExists(carpetaTecnicas)) {
            Files.createDirectories(carpetaTecnicas)
        }
        if (Files.notExists(carpetaPersonajes)) {
            Files.createDirectories(carpetaPersonajes)
        }

        Files.list(carpetaPersonajes).use {stream ->
            stream.sorted().forEach {item ->

            }
        }

        if (Files.notExists(ficheroNombres)) {
            println("No se ha encontrado el fichero con el nombre: " + ficheroNombres.fileName.toString())
        }
        else {
            println("Encontrado el fichero con el nombre: " + ficheroNombres.fileName)
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }


}