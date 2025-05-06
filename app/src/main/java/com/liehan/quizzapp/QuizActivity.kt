package com.liehan.quizzapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }//end of ViewCompat
        //Code goes here
        //link the elements from the GUI to the backend
        val tvQuestion = findViewById<TextView>(R.id.tvQuizQuestion)
        val rbtngAnswers = findViewById<RadioGroup>(R.id.rbtngQuizAnswers)
        val btnNext = findViewById<Button>(R.id.button)
        val username = intent.getStringExtra("username")
        //Arrays of Questions and Answers
        val scienceQuestions = arrayOf(
            "What is the chemical symbol for water?",
            "Which planet is known as the Red Planet?",
            "What force keeps planets in orbit around the sun?",
            "What gas do plants absorb from the atmosphere?",
            "What is the boiling point of water at sea level?"
        )
        val answerChoices = arrayOf(
            arrayOf("A: H2O", "B: CO2", "C: O2"),
            arrayOf("A: Venus", "B: Mars", "C: Jupiter"),
            arrayOf("A: Magnetism", "B: Gravity", "C: Nuclear Force"),
            arrayOf("A: Oxygen", "B: Carbon Dioxide", "C: Nitrogen"),
            arrayOf("A: 100°C", "B: 0°C", "C: 50°C")
        )
        var userAnswers = arrayOfNulls<String>(5)
        val correctAnswers = arrayOf(
            "A: H2O",
            "B: Mars",
            "B: Gravity",
            "B: Carbon Dioxide",
            "A: 100°C"
        )
        var counter = 0
        //code logic
        // Set the first question before waiting for user interaction
        tvQuestion.text = scienceQuestions[counter]
        for (i in 0 until rbtngAnswers.childCount) {
            val radioButton = rbtngAnswers.getChildAt(i) as RadioButton
            radioButton.text = answerChoices[counter][i]
        }
        btnNext.setOnClickListener {
            if (counter < 5) {
                var selectedOption = rbtngAnswers.checkedRadioButtonId

                if (selectedOption != -1) {
                    val selectedRbtn = findViewById<RadioButton>(selectedOption)
                    userAnswers[counter] = selectedRbtn.text.toString()
                    counter++
                } else {
                    Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener // Stops execution if no answer is selected
                }

                if (counter < 5) {
                    tvQuestion.text = scienceQuestions[counter]
                    for (i in 0 until rbtngAnswers.childCount) {
                        val radioButton = rbtngAnswers.getChildAt(i) as RadioButton
                        radioButton.text = answerChoices[counter][i]
                    }
                    rbtngAnswers.clearCheck()
                } else {
                    val intent = Intent(this, ResultActivity::class.java)
                    var score = 0

                    // Calculate score
                    for (i in userAnswers.indices) {
                        if (userAnswers[i] == correctAnswers[i]) {
                            score++
                        }
                    }

                    intent.putExtra("score", score)
                    intent.putExtra("username", username)

                    startActivity(intent)
                    finish()
                }
            }
        }
    }//end of onCreate
}//end of QuizActivity