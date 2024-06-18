package Controllers

import com.Innova.entity.RecetaUtensilio
import com.Innova.service.GenericService

class RecetaUtensilioController : GenericCrudController<RecetaUtensilio>(GenericService(RecetaUtensilio::class.java))
