package com.labed.todo.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.labed.todo.R
import com.labed.todo.data.models.ToDoData
import com.labed.todo.data.viewmodel.ToDoViewModel
import com.labed.todo.databinding.FragmentAddBinding
import com.labed.todo.fragments.SharedViewModel


class AddFragment : Fragment() {


    private lateinit var binding: FragmentAddBinding
    private val toDoViewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)
        // set Menu
        setHasOptionsMenu(true)

        binding.prioritiesSpinner.onItemSelectedListener = sharedViewModel.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = binding.titleEditText.text.toString()
        val mPriority = binding.prioritiesSpinner.selectedItem.toString()
        val mDescription = binding.descriptionEditTextMultiLine.text.toString()

        val validation = sharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation) {

            val newData =
                ToDoData(
                    0,
                    mTitle,
                    sharedViewModel.parsePriority(mPriority),
                    mDescription
                )

            toDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()
            //Navigate Back 
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()
        }
    }


}