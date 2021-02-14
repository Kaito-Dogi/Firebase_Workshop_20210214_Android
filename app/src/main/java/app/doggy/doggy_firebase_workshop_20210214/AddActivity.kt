package app.doggy.doggy_firebase_workshop_20210214

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Access a Cloud Firestore instance from your Activity
        val db = Firebase.firestore

        //追加ボタンの処理
        addButton.setOnClickListener {

            if (nameText.text.toString() == ""
                && nickNameText.text.toString() == ""
                && termText.text.toString() == ""
                && courseText.text.toString() == ""
                && selfIntroductionText.text.toString() == "") {

                Toast.makeText(applicationContext, "情報を入力して下さい", Toast.LENGTH_LONG).show()

            } else {

                // Create a new user with a first and last name
                val user = hashMapOf(
                    "name" to nameText.text.toString(),
                    "nickname" to nickNameText.text.toString(),
                    "term" to termText.text.toString(),
                    "course" to courseText.text.toString(),
                    "self_introduction" to selfIntroductionText.text.toString()
                )

                // Add a new document with a generated ID
                db.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d("ADD_DATA", "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w("ADD_DATA", "Error adding document", e)
                    }

                Toast.makeText(applicationContext, "データを追加しました", Toast.LENGTH_LONG).show()

                finish()

            }
        }
    }
}