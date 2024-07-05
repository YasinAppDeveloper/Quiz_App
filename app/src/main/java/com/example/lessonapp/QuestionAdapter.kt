import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lessonapp.Question
import com.example.lessonapp.R
import com.google.android.material.radiobutton.MaterialRadioButton

class QuestionAdapter(
    private val questions: List<Question>,
    private val userAnswers: IntArray,
    private val userComments: Array<String?>
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionText: TextView = view.findViewById(R.id.question_text)
        val option1: RadioButton = view.findViewById(R.id.option1)
        val option2: RadioButton = view.findViewById(R.id.option2)
        val option3: RadioButton = view.findViewById(R.id.option3)
        val option4: RadioButton = view.findViewById(R.id.option4)
        val comment: EditText = view.findViewById(R.id.comment)
        val numberIndex:TextView = view.findViewById(R.id.index_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.data_item_layout, parent, false)
        return QuestionViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.questionText.text = question.questionText
        holder.option1.text = question.options[0]
        holder.option2.text = question.options[1]
        holder.option3.text = question.options[2]
        holder.option4.text = question.options[3]
        holder.numberIndex.text = "${position + 1}. "

        val options = listOf(holder.option1,holder.option2,holder.option3,holder.option4)

        options.forEachIndexed { index, checkBox ->
            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = userAnswers[position] == index
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    userAnswers[position] = index
                    options.filterNot {
                        it == checkBox
                    }.forEach {
                        it.isChecked = false
                    }
                } else if (userAnswers[position] == index) {
                    userAnswers[position] = -1
                }
            }
        }

//        holder.comment.setText(userComments[position])
//        holder.comment.setOnFocusChangeListener { _, _ ->
//            userComments[position] = holder.comment.text.toString()
//        }
        holder.comment.setText(userComments[position] ?: "")
        holder.comment.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                userComments[position] = holder.comment.text.toString()
            }
        }
    }

    override fun getItemCount(): Int = questions.size
}
