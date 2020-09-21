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

class QuestionnaireFragment : Fragment(), ItemListener {

    private lateinit var listing: RecyclerView
    private lateinit var adapter: ListAdapter

    private var questions: List<Question> = listOf()
    var listener: Listener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getParcelableArrayList<Question>(DATA)?.let {
            questions = it.toList()
        }

        adapter = ListAdapter(
            questions
        )

        adapter.listener = this

        (activity as AppCompatActivity).supportActionBar?.title =
            questions.first().category.questionnaire

        retainInstance = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_done -> {
            listener?.onStopQuestioner(ArrayList(adapter.list))
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
            questions: ArrayList<Question>?
        ): QuestionnaireFragment {
            val fragment = QuestionnaireFragment()

            val args = Bundle()

            args.putParcelableArrayList(DATA, questions)

            fragment.arguments = args

            return fragment
        }
    }

    interface Listener {
        fun onStopQuestioner(questions: ArrayList<Question>)
    }

    override fun onClick(question: Question) {
        //Do Nothing
    }

    override fun onOptionClicked(question: Question, selectedOption: Question.Option) {
        if (question.createOption) {
            adapter.list.map {
                it.createOption = false
            }
            adapter.notifyDataSetChanged()
        }

        adapter.list.firstOrNull { it.id == question.id }?.let { q ->
            q.options.map { option ->
                option.checked = option.answer == selectedOption.answer
            }
        }

        adapter.notifyDataSetChanged()
    }
}