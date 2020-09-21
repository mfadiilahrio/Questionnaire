package com.adrena.prototype.medical.ui.questionnaire

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrena.prototype.medical.Constants.Companion.DATA
import com.adrena.prototype.medical.R
import com.adrena.prototype.medical.data.model.Question
import com.adrena.prototype.medical.data.model.Questionnaire

class QuestionnaireFragment : Fragment(), ItemListener {

    private lateinit var listing: RecyclerView
    private lateinit var adapter: ListAdapter

    private lateinit var questionnaire: Questionnaire
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
            listener?.onStopQuestioner(Questionnaire(questionnaire.name, adapter.list))
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
        //Do Nothing
    }

    override fun onOptionClicked(question: Question, selectedOption: Question.Option) {
        adapter.list.firstOrNull { it.id == question.id }?.let { q ->
            q.options.map { option ->
                option.checked = option.answer == selectedOption.answer
            }
        }

        adapter.notifyDataSetChanged()
    }
}