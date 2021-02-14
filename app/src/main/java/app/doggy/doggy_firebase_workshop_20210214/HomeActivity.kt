package app.doggy.doggy_firebase_workshop_20210214

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    val TAG = "GET_DATA"

    var users: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

    }

    override fun onResume() {
        super.onResume()

        val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_expandable_list_item_1, users)

        // Access a Cloud Firestore instance from your Activity
        val db = Firebase.firestore

        fab.setOnClickListener {
            val intent = Intent(applicationContext, AddActivity::class.java)
            startActivity(intent)
        }

        readAll(db, adapter)

        listView.adapter = adapter


    }

    fun readAll(db: FirebaseFirestore, adapter: ArrayAdapter<String>) {
        db.collection("users")
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.w(TAG, "Listen failed", error)
                        return@addSnapshotListener
                    }

                    users.clear()
                    for (document in value!!) {
                        Log.d(TAG, "${document.id} => ${document.data.get("name")}")
                        users.add(document.data.get("name").toString())
                    }
                    adapter.notifyDataSetChanged()
                }
    }

}