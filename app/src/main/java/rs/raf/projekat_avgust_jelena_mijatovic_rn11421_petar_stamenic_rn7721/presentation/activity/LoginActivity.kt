package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.R

class LoginActivity : AppCompatActivity() {

    private var username: EditText? = null
    private var password: EditText? = null
    private var loginButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()

        initListeners()
    }

    private fun initView() {
        username = findViewById(R.id.activity_login_username)
        password = findViewById(R.id.activity_login_password)
        loginButton = findViewById(R.id.activity_login_button)
    }

    private fun initListeners() {

        var usernameS : String
        var passwordS : String

        loginButton!!.setOnClickListener {

            usernameS = username!!.text.toString()
            passwordS = password!!.text.toString()

            if (usernameS.isEmpty() || passwordS.isEmpty()) {
                Toast.makeText(this, R.string.empty_field, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if (passwordS.length < 5) {
                Toast.makeText(this, R.string.short_password, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if (!passwordS.contains("[A-Z]".toRegex()) || !passwordS.contains("\\d".toRegex())) {
                Toast.makeText(this, R.string.capitaldigit, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if (passwordS.contains("~#\\^|\\\$%&*!".toRegex())) {
                Toast.makeText(this, R.string.specialcharacter, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if (passwordS != getString(R.string.secret)) {
                Toast.makeText(this, R.string.incorrectpassword, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val intent = Intent(this, MainActivity::class.java)

            val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
            sharedPreferences
                .edit()
                .putString("username", usernameS)
                .apply()

            startActivity(intent)
            finish()
        }
    }
}