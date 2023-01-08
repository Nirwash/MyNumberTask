package com.nirwashh.android.mynumbertask.numbers.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nirwashh.android.mynumbertask.databinding.NumberLayoutBinding
import com.nirwashh.android.mynumbertask.numbers.presentation.NumbersAdapter.NumbersViewHolder

class NumbersAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<NumbersViewHolder>(), Mapper.Unit<List<NumberUi>> {

    private val list = mutableListOf<NumberUi>()

    inner class NumbersViewHolder(
        binding: NumberLayoutBinding,
        private val clickListener: ClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private val title = binding.titleTextView
        private val subTitle = binding.subTitleTextView
        private val mapper = ListItemUi(title, subTitle)
        fun bind(model: NumberUi) {
            model.map(mapper)
            itemView.setOnClickListener {
                clickListener.click(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersViewHolder {
        val binding = NumberLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NumbersViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: NumbersViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount() = list.size
    override fun map(source: List<NumberUi>) {
        val diffUtil = DiffUtilCallback(list, source)
        val result = DiffUtil.calculateDiff(diffUtil)
        list.clear()
        list.addAll(source)
        result.dispatchUpdatesTo(this)
    }
}

interface ClickListener {
    fun click(item: NumberUi)
}

class DiffUtilCallback(
    private val oldList: List<NumberUi>,
    private val newList: List<NumberUi>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].map(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

}