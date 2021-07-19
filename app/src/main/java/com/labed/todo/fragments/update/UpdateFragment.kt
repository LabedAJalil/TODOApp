package com.labed.todo.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.labed.todo.R
import com.labed.todo.data.models.ToDoData
import com.labed.todo.data.viewmodel.ToDoViewModel
import com.labed.todo.databinding.FragmentUpdateBinding
import com.labed.todo.fragments.SharedViewModel


class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding

    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val toDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args

        //setMenu
        setHasOptionsMenu(true)

        binding.currentPrioritiesSpinner.onItemSelectedListener = sharedViewModel.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            toDoViewModel.deleteToDoData(args.currentItem)
            Toast.makeText(
                requireContext(),
                "Successfully Removed: ${args.currentItem.title}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentItem.title}?")
        builder.setMessage("Are you sure you want to remove ${args.currentItem.title}?")
        builder.create().show()
    }

    private fun updateItem() {
        val title = binding.currentTitleEditText.text.toString()
        val description = binding.currentDescriptionEditTextMultiLine.text.toString()
        val getPriority = binding.currentPrioritiesSpinner.selectedItem.toString()

        val validation = sharedViewModel.verifyDataFromUser(title, description)

        if (validation) {
            val updatedItem =
                ToDoData(
                    args.currentItem.id,
                    title,
                    sharedViewModel.parsePriority(getPriority),
                    description
                )
            toDoViewModel.updateToDoData(updatedItem)
            Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()

        }

    }
}