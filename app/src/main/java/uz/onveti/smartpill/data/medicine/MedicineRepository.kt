package uz.onveti.smartpill.data.medicine

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MedicineRepository(
    private val databaseReference: DatabaseReference,
) {

    private val medicinesReference: DatabaseReference = databaseReference.child(MEDICINES_NODE)
    private val pillStatusReference: DatabaseReference = databaseReference.child(PILL_STATUS_NODE)

    fun observeMedicines(): Flow<List<Medicine>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val medicines = snapshot.children
                    .mapNotNull { child -> child.getValue(Medicine::class.java) }
                    .sortedByDescending { medicine -> medicine.createdAt }

                trySend(medicines)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        medicinesReference.addValueEventListener(listener)
        awaitClose { medicinesReference.removeEventListener(listener) }
    }

    fun observeMedicine(medicineId: String): Flow<Medicine?> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot.getValue(Medicine::class.java))
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        medicinesReference.child(medicineId).addValueEventListener(listener)
        awaitClose { medicinesReference.child(medicineId).removeEventListener(listener) }
    }

    fun observePillStatus(): Flow<String?> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot.value?.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        pillStatusReference.addValueEventListener(listener)
        awaitClose { pillStatusReference.removeEventListener(listener) }
    }

    suspend fun addMedicine(
        name: String,
        morningTime: String,
        afternoonTime: String,
        eveningTime: String,
        mealTiming: MealTiming,
    ): Medicine {
        val id = medicinesReference.push().key ?: error("Firebase id yaratilmadi")
        val medicine = Medicine(
            id = id,
            name = name,
            morningTime = morningTime,
            afternoonTime = afternoonTime,
            eveningTime = eveningTime,
            mealTiming = mealTiming,
            createdAt = System.currentTimeMillis(),
        )

        medicinesReference.child(id).setValueSuspending(medicine)
        return medicine
    }

    suspend fun deleteMedicine(medicineId: String) {
        medicinesReference.child(medicineId).removeValueSuspending()
    }

    private suspend fun DatabaseReference.setValueSuspending(value: Any) {
        suspendCancellableCoroutine { continuation ->
            setValue(value)
                .addOnSuccessListener {
                    if (continuation.isActive) {
                        continuation.resume(Unit)
                    }
                }
                .addOnFailureListener { throwable ->
                    if (continuation.isActive) {
                        continuation.resumeWithException(throwable)
                    }
                }
        }
    }

    private suspend fun DatabaseReference.removeValueSuspending() {
        suspendCancellableCoroutine { continuation ->
            removeValue()
                .addOnSuccessListener {
                    if (continuation.isActive) {
                        continuation.resume(Unit)
                    }
                }
                .addOnFailureListener { throwable ->
                    if (continuation.isActive) {
                        continuation.resumeWithException(throwable)
                    }
                }
        }
    }

    private companion object {
        const val MEDICINES_NODE = "dorilar"
        const val PILL_STATUS_NODE = "pillStatus"
    }
}
