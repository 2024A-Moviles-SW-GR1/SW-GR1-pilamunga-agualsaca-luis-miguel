package Main

import Controller.*
import com.Innova.entity.*
import java.util.*

fun main() {
    // Initialize the controllers
    val categoriaController = CategoriaController()
    val ingredienteController = IngredienteController()
    val pasoController = PasoController()
    val recetaIngredienteController = RecetaIngredienteController()
    val recetaUtensilioController = RecetaUtensilioController()
    val recetaController = RecetaController()
    val utensilioController = UtensilioController()

    val scanner = Scanner(System.`in`)

    // Helper function to get user input
    fun getUserInput(prompt: String): String {
        print("$prompt: ")
        return scanner.nextLine().trim()
    }

    // Function to select or create a category
    fun selectOrCreateCategoria(): Categoria {
        val categorias = categoriaController.listAll()
        if (categorias.isNotEmpty()) {
            println("Seleccione una categoría existente o presione Enter para crear una nueva:")
            categorias.forEachIndexed { index, categoria -> println("${index + 1}. ${categoria.nombre}") }
        } else {
            println("No hay categorías disponibles. Crearemos una nueva.")
        }

        val input = getUserInput("Ingrese el número de categoría o presione Enter para crear una nueva")
        return if (input.isNotEmpty()) {
            categorias[input.toInt() - 1]
        } else {
            val nombre = getUserInput("Ingrese el nombre de la categoría")
            val descripcion = getUserInput("Ingrese la descripción de la categoría")
            val categoria = Categoria(id = categorias.size + 1, nombre = nombre, descripcion = descripcion)
            categoriaController.save(categoria)
            categoria
        }
    }

    // Function to select or create an ingredient
    fun selectOrCreateIngrediente(): Ingrediente {
        val ingredientes = ingredienteController.listAll()
        if (ingredientes.isNotEmpty()) {
            println("Seleccione un ingrediente existente o presione Enter para crear uno nuevo:")
            ingredientes.forEachIndexed { index, ingrediente -> println("${index + 1}. ${ingrediente.nombre}") }
        } else {
            println("No hay ingredientes disponibles. Crearemos uno nuevo.")
        }

        val input = getUserInput("Ingrese el número de ingrediente o presione Enter para crear uno nuevo")
        return if (input.isNotEmpty()) {
            ingredientes[input.toInt() - 1]
        } else {
            val nombre = getUserInput("Ingrese el nombre del ingrediente")
            val cantidad = getUserInput("Ingrese la cantidad del ingrediente").toDouble()
            val unidadMedida = getUserInput("Ingrese la unidad de medida del ingrediente")
            val ingrediente = Ingrediente(id = ingredientes.size + 1, nombre = nombre, cantidad = cantidad, unidadMedida = unidadMedida)
            ingredienteController.save(ingrediente)
            ingrediente
        }
    }

    // Function to select or create a utensil
    fun selectOrCreateUtensilio(): Utensilio {
        val utensilios = utensilioController.listAll()
        if (utensilios.isNotEmpty()) {
            println("Seleccione un utensilio existente o presione Enter para crear uno nuevo:")
            utensilios.forEachIndexed { index, utensilio -> println("${index + 1}. ${utensilio.nombre}") }
        } else {
            println("No hay utensilios disponibles. Crearemos uno nuevo.")
        }

        val input = getUserInput("Ingrese el número de utensilio o presione Enter para crear uno nuevo")
        return if (input.isNotEmpty()) {
            utensilios[input.toInt() - 1]
        } else {
            val nombre = getUserInput("Ingrese el nombre del utensilio")
            val descripcion = getUserInput("Ingrese la descripción del utensilio")
            val enlaceCompra = getUserInput("Ingrese el enlace de compra del utensilio")
            val utensilio = Utensilio(id = utensilios.size + 1, nombre = nombre, descripcion = descripcion, enlaceCompra = enlaceCompra)
            utensilioController.save(utensilio)
            utensilio
        }
    }

    // Function to create steps
    fun createSteps(receta: Receta): List<Paso> {
        val pasos = mutableListOf<Paso>()
        var continuar = true
        while (continuar) {
            val numeroPaso = pasos.size + 1
            val descripcion = getUserInput("Ingrese la descripción del paso $numeroPaso")
            val paso = Paso(id = numeroPaso, numeroPaso = numeroPaso, descripcion = descripcion, receta = receta)
            pasoController.save(paso)
            pasos.add(paso)
            continuar = getUserInput("¿Desea agregar otro paso? (s/n)").lowercase() == "s"
        }
        return pasos
    }

    // Main loop to register a recipe
    while (true) {
        println("Registro de Receta")

        // Select or create Categoria
        val categoria = selectOrCreateCategoria()

        // Select or create Ingredientes
        val ingredientes = mutableListOf<RecetaIngrediente>()
        var agregarIngrediente = true
        while (agregarIngrediente) {
            val ingrediente = selectOrCreateIngrediente()
            val cantidad = getUserInput("Ingrese la cantidad de ${ingrediente.nombre}").toDouble()
            val unidadMedida = getUserInput("Ingrese la unidad de medida de ${ingrediente.nombre}")
            val recetaIngrediente = RecetaIngrediente(id = ingredientes.size + 1, receta = Receta(0, "", "", 0, 0, "", categoria, "", emptyList(), emptyList(), emptyList()), ingrediente = ingrediente, cantidad = cantidad, unidadMedida = unidadMedida)
            recetaIngredienteController.save(recetaIngrediente)
            ingredientes.add(recetaIngrediente)
            agregarIngrediente = getUserInput("¿Desea agregar otro ingrediente? (s/n)").lowercase() == "s"
        }

        // Select or create Utensilios
        val utensilios = mutableListOf<RecetaUtensilio>()
        var agregarUtensilio = true
        while (agregarUtensilio) {
            val utensilio = selectOrCreateUtensilio()
            val recetaUtensilio = RecetaUtensilio(id = utensilios.size + 1, receta = Receta(0, "", "", 0, 0, "", categoria, "", emptyList(), emptyList(), emptyList()), utensilio = utensilio)
            recetaUtensilioController.save(recetaUtensilio)
            utensilios.add(recetaUtensilio)
            agregarUtensilio = getUserInput("¿Desea agregar otro utensilio? (s/n)").lowercase() == "s"
        }

        // Create Pasos
        val receta = Receta(
            id = 0,
            nombre = getUserInput("Ingrese el nombre de la receta"),
            descripcion = getUserInput("Ingrese la descripción de la receta"),
            tiempoPreparacion = getUserInput("Ingrese el tiempo de preparación (en minutos)").toInt(),
            porciones = getUserInput("Ingrese el número de porciones").toInt(),
            dificultad = getUserInput("Ingrese la dificultad de la receta"),
            categoria = categoria,
            fechaActualizacion = getUserInput("Ingrese la fecha de actualización (YYYY-MM-DD)"),
            ingredientes = ingredientes,
            pasos = emptyList(),
            utensilios = utensilios.map { it.utensilio }
        )

        val pasos = createSteps(receta)

        // Save the complete Receta
        val recetaCompleta = receta.copy(id = recetaController.listAll().size + 1, pasos = pasos)
        println(recetaController.save(recetaCompleta))

        if (getUserInput("¿Desea registrar otra receta? (s/n)").lowercase() != "s") {
            break
        }
    }

    println("Aplicación finalizada.")
}
