/*
 * Copyright (c) 2021. Cognizant Softvision
 * Krisztian Bokor
 *
 */

package com.softvision.krisztianbokor.presentation.ui.person

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softvision.krisztianbokor.R
import com.softvision.krisztianbokor.domain.model.PersonModel
import kotlinx.android.synthetic.main.person_item.view.*

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PersonModel>() {
    override fun areItemsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean =
        oldItem == newItem
}

class PersonAdapter : ListAdapter<PersonModel, BaseViewHolder<PersonModel>>(DIFF_CALLBACK) {
    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<PersonModel> {
        when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.person_item_header, parent, false)
                return HeaderViewHolder(view)
            }
            TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.person_item, parent, false)
                return PersonViewHolder(view)
            }
        }
        throw RuntimeException("Wrong view type $viewType")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<PersonModel>, position: Int) {
        when (getItemViewType(position)) {
            TYPE_HEADER -> holder.bind(PersonModel.getDefault())
            TYPE_ITEM -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return if (currentList.size > 0) currentList.size + 1 else currentList.size
    }

    override fun getItem(position: Int): PersonModel {
        return currentList[position - 1]
    }
}

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}

class PersonViewHolder(private val view: View) : BaseViewHolder<PersonModel>(view) {
    override fun bind(item: PersonModel) =
        with(view) {
            first_name.text = item.firstName
            last_name.text = item.lastName
            issue_count.text = item.issueCount.toString()
            date_of_birth.text = item.dateOfBirth
        }
}

class HeaderViewHolder(private val view: View) : BaseViewHolder<PersonModel>(view) {
    override fun bind(item: PersonModel) =
        with(view) {
            first_name.text = PersonModel.FIRST_NAME
            last_name.text = PersonModel.LAST_NAME
            issue_count.text = PersonModel.ISSUE_COUNT
            date_of_birth.text = PersonModel.DATE_OF_BIRTH
        }
}
