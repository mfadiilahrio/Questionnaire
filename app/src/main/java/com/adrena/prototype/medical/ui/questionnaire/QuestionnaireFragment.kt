package com.adrena.prototype.medical.ui.questionnaire

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrena.prototype.medical.Constants.Companion.DATA
import com.adrena.prototype.medical.R
import com.adrena.prototype.medical.data.model.Question
import com.adrena.prototype.medical.data.model.Questionnaire

class QuestionnaireFragment : Fragment(), ItemListener, QuestionActionSheet.Listener {

    private lateinit var listing: RecyclerView
    private lateinit var adapter: ListAdapter

    private lateinit var questionnaire: Questionnaire

    private var mQuestionActionSheet: QuestionActionSheet? = null

    var listener: Listener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        questionnaire = arguments?.getParcelable(DATA) ?: throw Throwable("Questionnaire cannot be null")

        adapter = ListAdapter(
            questionnaire.questions
        )

        adapter.listener = this

        (activity as AppCompatActivity).supportActionBar?.title = questionnaire.name

        retainInstance = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_done -> {
            val notCompleted = adapter.list.any { q ->
                (q.options.isNotEmpty() && q.options.none { it.checked }) || (q.options.isEmpty() && q.answer.isNullOrEmpty())
            }

            if (notCompleted) {
                context?.let {
                    AlertDialog.Builder(it).apply {
                        setTitle(getString(R.string.information))
                        setMessage(getString(R.string.complete_all_questionnaire))
                        setPositiveButton(getString(R.string.ok)) { _, _ ->

                        }
                    }.show()
                }
            } else {
                listener?.onStopQuestioner(Questionnaire(questionnaire.name, adapter.list))
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.done_option_menu, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        listing = view?.findViewById(R.id.list_recycler_view) ?: throw Throwable("Listing is null")

        setHasOptionsMenu(true)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listing.layoutManager = LinearLayoutManager(activity)
        listing.adapter = adapter
    }

    companion object {
        fun newInstance(
            questionnaire: Questionnaire
        ): QuestionnaireFragment {
            val fragment = QuestionnaireFragment()

            val args = Bundle()

            args.putParcelable(DATA, questionnaire)

            fragment.arguments = args

            return fragment
        }
    }

    interface Listener {
        fun onStopQuestioner(questionnaire: Questionnaire)
    }

    override fun onClick(question: Question) {
        mQuestionActionSheet = QuestionActionSheet.newInstance(
            question
        )
        mQuestionActionSheet?.let { actionSheet ->
            actionSheet.listener = this
            actionSheet.show(childFragmentManager, actionSheet.toString())
        }
    }

    override fun onOptionClicked(question: Question, selectedOption: Question.Option) {
        adapter.list.firstOrNull { it.id == question.id }?.let { q ->
            q.options.map { option ->
                option.checked = option.answer == selectedOption.answer
            }
        }

        adapter.notifyDataSetChanged()
    }

    override fun onAnswerDone(question: Question) {
        adapter.list.firstOrNull { it.id == question.id }?.let { q ->
            q.answer = question.answer
        }

        adapter.notifyDataSetChanged()
    }
}