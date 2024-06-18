package Controllers

import com.Innova.entity.Categoria
import com.Innova.service.GenericService

class CategoriaController : GenericCrudController<Categoria>(GenericService(Categoria::class.java))
