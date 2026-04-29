package uz.onveti.smartpill.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module
import uz.onveti.smartpill.data.ai.OpenAiRepository
import uz.onveti.smartpill.data.medicine.MedicineRepository

val dataModule = module {
    single<FirebaseFirestore> { FirebaseFirestore.getInstance() }
    single { FirebaseDatabase.getInstance().reference }
    single { MedicineRepository(get()) }
    single { OpenAiRepository(get(), get()) }
}
