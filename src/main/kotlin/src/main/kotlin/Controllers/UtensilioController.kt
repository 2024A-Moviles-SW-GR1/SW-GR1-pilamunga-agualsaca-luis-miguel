package Controllers

import com.Innova.entity.Utensilio
import com.Innova.service.GenericService

class UtensilioController : GenericCrudController<Utensilio>(GenericService(Utensilio::class.java)) {
}
