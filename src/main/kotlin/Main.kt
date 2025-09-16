package org.example
import java.nio.file.Files
import java.nio.file.Path
import java.util.Arrays.stream

fun main() {
    val carpetaPersonajes = Path.of("Players")
    val carpetaTecnicas = Path.of("Hissatsus")

    val ficheroNombres = Path.of("Players/Player_Names.txt")

    try {
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