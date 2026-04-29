package uz.onveti.smartpill.screens.medicines.state

internal sealed interface MedicinesAction {

    data object NavigateBack : MedicinesAction

    data object AddMedicineClicked : MedicinesAction

    data class MedicineClicked(val medicineId: String) : MedicinesAction

    data class DeleteMedicineClicked(val medicineId: String) : MedicinesAction

}
