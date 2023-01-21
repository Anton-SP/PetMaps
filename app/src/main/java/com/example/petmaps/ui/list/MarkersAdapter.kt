package com.example.petmaps.ui.list

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.petmaps.data.Mark
import com.example.petmaps.databinding.ItemMarkerBinding

class MarkersAdapter(
    private val onClickDelete: (Mark) -> Unit
) : RecyclerView.Adapter<MarkersAdapter.MarkerViewHolder>() {

    private val markers = ArrayList<Mark>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerViewHolder {
        val binding = ItemMarkerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val holder = MarkerViewHolder(binding = binding, onClickDelete = onClickDelete)
        holder.setListeners()
        return holder
    }

    override fun getItemCount(): Int {
        return markers.size
    }

    override fun onBindViewHolder(holder: MarkerViewHolder, position: Int) {
        //    holder.updatePositions(position = position)
        holder.bind(marker = markers[position])
    }

    fun remove(position: Int) {
        markers.removeAt(position)
        notifyItemRemoved(position)
    }

    fun submitList(data: List<Mark>) {
        markers.clear()
        markers.addAll(data)
        notifyDataSetChanged()
    }

    inner class MarkerViewHolder(
        private val binding: ItemMarkerBinding, private val onClickDelete: (Mark) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val edMarkerNoteListener = object : TextWatcher {
            var itemPosition: Int = 0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotBlank()) {
                    markers[itemPosition].note = s.toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        fun setListeners() {
            with(binding) {
                edMarkerNote.addTextChangedListener(edMarkerNoteListener)
            }
        }

        fun bind(marker: Mark) {
            with(binding) {
                btnDelete.setOnClickListener {
                    onClickDelete.invoke(marker)
                }

                tvMarkerNumberValue.text = marker.id.toString()
                tvMarkerNumberLatitudeValue.text = marker.coordinates.latitude.toString()
                tvMarkerNumberLongitudeValue.text = marker.coordinates.longitude.toString()
                edMarkerNote.setText(marker.note.toString())
            }
        }
    }

    private class DiffUtilsItemCallbackImpl : DiffUtil.ItemCallback<Mark>() {
        override fun areItemsTheSame(oldItem: Mark, newItem: Mark): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Mark, newItem: Mark): Boolean {
            return oldItem == newItem
        }
    }

}