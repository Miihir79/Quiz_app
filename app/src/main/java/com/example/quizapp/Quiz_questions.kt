package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class Quiz_questions : AppCompatActivity() , View.OnClickListener {

    private var mCurrentPos : Int  =1
    private var mQuestionsList: ArrayList<Questions>?= null
    private var mSelectedop : Int =0
    private var mCorrect : Int =0
    private var mUsername : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)
        supportActionBar?.hide()

        mUsername= intent.getStringExtra(Constants.username)

        mQuestionsList= Constants.getQuestion()
        Log.i("Questions size:","${mQuestionsList!!.size}")

        setQuestion()

        Op1.setOnClickListener(this)
        Op2.setOnClickListener(this)
        Op3.setOnClickListener(this)
        Op4.setOnClickListener(this)

        Submit.setOnClickListener(this)
    }
    private fun setQuestion(){

        val question = mQuestionsList!![mCurrentPos-1]
        defaultOp()
        if(mCurrentPos == mQuestionsList!!.size){
            Submit.text ="Finish"
        }else{
            Submit.text="Submit"
        }
        progress_horizontal.progress =mCurrentPos
        textView_progress.text="$mCurrentPos "+ "/" +progress_horizontal.max
        Question_text.text = question!!.question
        Flag.setImageResource(question.Image)
        Op1.text= question.op1
        Op2.text = question.op2
        Op3.text = question.op3
        Op4.text = question.op4
    }

    private fun defaultOp(){
        val options = ArrayList<TextView>()
        options.add(0,Op1)
        options.add(1,Op2)
        options.add(2,Op3)
        options.add(3,Op4)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface= Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this,R.drawable.option_design)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.Op1->{
                selectedOptionsView(Op1,1)
            }
            R.id.Op2->{
                selectedOptionsView(Op2,2)
            }
            R.id.Op3->{
                selectedOptionsView(Op3,3)
            }
            R.id.Op4->{
                selectedOptionsView(Op4,4)
            }
            R.id.Submit ->{
                if(mSelectedop == 0){
                    mCurrentPos ++

                    when{
                        mCurrentPos<= mQuestionsList!!.size ->{
                            setQuestion()
                        }
                        else->{
                            val intent = Intent(this,Result::class.java)
                            intent.putExtra(Constants.username, mUsername)
                            intent.putExtra(Constants.Total_Questions,mQuestionsList!!.size)
                            intent.putExtra(Constants.correct_Questions,mCorrect)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                else{
                    val question = mQuestionsList?.get(mCurrentPos -1 )
                    if (question!!.correct != mSelectedop){
                        answerView(mSelectedop,R.drawable.option_wrong)
                    }else{
                        mCorrect++
                    }
                    answerView(question.correct,R.drawable.option_correct)

                    if(mCurrentPos == mQuestionsList!!.size){
                        Submit.text = "Finish"
                    }else{
                        Submit.text="Go To The Next Question"
                    }
                    mSelectedop =0
                }

            }
        }

    }

    private fun answerView(answer: Int, drawableview: Int) {
        when(answer){
            1->{
                Op1.background= ContextCompat.getDrawable(this,drawableview)
            }
            2->{
                Op2.background= ContextCompat.getDrawable(this,drawableview)
            }
            3->{
                Op3.background= ContextCompat.getDrawable(this,drawableview)
            }
            4->{
                Op4.background= ContextCompat.getDrawable(this,drawableview)
            }
        }

    }

    private fun selectedOptionsView(tv : TextView,selectedOpNum: Int){
        defaultOp()
        mSelectedop = selectedOpNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,R.drawable.option_design_selected)

    }
}