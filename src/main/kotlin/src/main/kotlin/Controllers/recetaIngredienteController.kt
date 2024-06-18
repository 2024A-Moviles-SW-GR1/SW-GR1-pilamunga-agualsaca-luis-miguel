package Controllers

import com.Innova.entity.RecetaIngrediente
import com.Innova.service.GenericService

class RecetaIngredienteController : GenericCrudController<RecetaIngrediente>(GenericService(RecetaIngrediente::class.java)) {
}
