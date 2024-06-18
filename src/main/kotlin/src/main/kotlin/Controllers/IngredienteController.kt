package Controllers

import com.Innova.entity.Ingrediente
import com.Innova.service.GenericService

class IngredienteController : GenericCrudController<Ingrediente>(GenericService(Ingrediente::class.java))
