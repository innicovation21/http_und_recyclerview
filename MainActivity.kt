package com.kotlinproject.http_example


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlinproject.http_example.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    //region Variablen
    lateinit var binding: ActivityMainBinding
    // Variable für unseren Adapter
    private val myAdapter = MyRecyclerViewAdapter()
    //endregion
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Funktion zum Erstellen der RecyclerView aufrufen
        setupRecyclerView()
        // Funktion zum abrufen der Posts über die API
        getPosts()
    }

    //region Funktion zum Aufsetzen der RecyclerView
    private fun setupRecyclerView() {
    // unser Adapter muss der RecyclerView im Layout zugewiesen werden
    binding.myRecView.adapter = myAdapter
    // Ausrichtung der Recyclerview (horizontal oder vertical)
    binding.myRecView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
    //endregion

    //region Funktion zum senden einer Anfrage und Empfangen / Verarbeiten der Antwort
    private fun getPosts() {

        // ZielAdresse: https://jsonplaceholder.typicode.com/posts <- posts wird für
        // getData() in MyInterface.kt benutzt
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyInterface::class.java)

        //Variable für die gewünschte Liste von PostItems
        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object: Callback<List<MyPostItem>?> {

            // Funktion zum Verarbeiten der Antwort (erhaltener Datensatz)
            override fun onResponse(
                call: Call<List<MyPostItem>?>,
                response: Response<List<MyPostItem>?>
            ) {

                /* Code für einfache Darstellung in TextView

                  // Variable für den Datensatz
                                val responseBody = response.body()!!

                                // Variable zum Erstellen eines Strings
                                val myStringBuilder = StringBuilder()

                                for (e in responseBody){
                                    myStringBuilder.append(e.title)
                                    myStringBuilder.append("\n")
                                }
                                // Einsetzen unseres "gebauten" Strings in die TextView der activity_main.xml
                                //binding.myTextView.text = myStringBuilder
                                */

                // Ausführen der setList-Funktion unseres Adapters
                // um die erhaltenen Daten aus dem response.body() in die leere Liste unseres Adapters einzusetzen
                myAdapter.setList(response.body() as List<MyPostItem> )
            }

            // Funktion für Fehler-Info
            override fun onFailure(call: Call<List<MyPostItem>?>, t: Throwable) {
                //Ausgabe der Fehlermeldung
                Log.e("RETROFIT_ERROR", "Folgender Fehler: ${t.message.toString()}")

                // Hinweis an den Nutzer mit zeitlich begrenztem Hinweisfenster am unteren Rand des Screens
                Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
        )


    }
    //endregion



}
