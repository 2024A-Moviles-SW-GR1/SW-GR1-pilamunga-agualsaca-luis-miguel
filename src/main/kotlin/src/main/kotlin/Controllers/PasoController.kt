package Controllers

import com.Innova.entity.Paso
import com.Innova.service.GenericService

class PasoController : GenericCrudController<Paso>(GenericService(Paso::class.java))
