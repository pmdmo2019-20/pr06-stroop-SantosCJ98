package es.iessaladillo.pedrojoya.stroop.ui.player.edit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.avatars
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_player_add_item.*

typealias OnItemClickListener3 = (position: Int) -> Unit

class PlayerEditFragmentAdapter : RecyclerView.Adapter<PlayerEditFragmentAdapter.ViewHolder>() {

    private var data: List<Int> = avatars

    private var OnItemClickListener3: OnItemClickListener3? = null

    fun setOnItemClickListener3(OnItemClickListener3: OnItemClickListener3) {

        this.OnItemClickListener3 = OnItemClickListener3

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val itemView = layoutInflater.inflate(R.layout.fragment_player_edit_item, parent, false)

        return ViewHolder(
            itemView,
            OnItemClickListener3
        )

    }

    init {

        setHasStableIds(true)

    }
    fun getItem(position: Int) = data.get(position)

    override fun getItemId(position: Int): Long = data.get(position).toLong()

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    class ViewHolder(override val containerView: View, OnItemClickListener3: OnItemClickListener3?) :

        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {

            itemView.setOnClickListener {

                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {

                    OnItemClickListener3?.invoke(position)

                }

            }

        }

        fun bind(avatar: Int) {

            imgAvatar.setImageResource(avatar)

        }


    }

}