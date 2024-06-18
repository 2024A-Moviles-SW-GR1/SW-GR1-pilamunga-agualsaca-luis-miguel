package Controller
import com.Innova.entity.Receta
import com.Innova.service.GenericService

class RecetaController : GenericCrudController<Receta>(GenericService(Receta::class.java)) {

}