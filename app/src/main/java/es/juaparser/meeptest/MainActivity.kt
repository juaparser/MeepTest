package es.juaparser.meeptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Clase principal de la aplicación. Se delega la funcionalidad a MapsFragment
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}