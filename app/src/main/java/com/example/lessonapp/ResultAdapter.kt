import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lessonapp.Question
import com.example.lessonapp.R

class ResultAdapter(
    private val questions: List<Question>,
    private val userAnswers: IntArray,
    private val userComments: Array<String?>
) : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionText: TextView = view.findViewById(R.id.question_text)
        val option1: TextView = view.findViewById(R.id.option_show_1)
        val comment: TextView = view.findViewById(R.id.comment_show)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_item, parent, false)
        return ResultViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val question = questions[position]
        val userAnswerIndex = userAnswers[position]
        holder.questionText.text = question.questionText
        holder.option1.text = questions[position].options[userAnswerIndex]


        if (userAnswerIndex >= 0 && userAnswerIndex < question.options.size) {
            holder.option1.text = question.options[userAnswerIndex]
        } else {
            holder.option1.text = "No answer selected"
        }

        val userComment = userComments[position]
        if (!userComment.isNullOrEmpty()) {
            holder.comment.text = userComment
            holder.comment.visibility = View.VISIBLE
        } else {
            holder.comment.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int = questions.size
}
