package es.iessaladillo.pedrojoya.stroop.ui.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.entity.Player
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_player_item.*

typealias OnItemCLickListener = (position: Int) -> Unit

class PlayerFragmentAdapter : RecyclerView.Adapter<PlayerFragmentAdapter.ViewHolder>() {

    private var data: List<Player> = emptyList()

    private var OnItemCLickListener: OnItemCLickListener? = null

    fun setOnItemCLickListener(OnItemCLickListener: OnItemCLickListener) {

        this.OnItemCLickListener = OnItemCLickListener

    }

    fun getItem(position: Int) = data.get(position)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val itemView = layoutInflater.inflate(R.layout.fragment_player_item, parent, false)

        return ViewHolder(itemView, OnItemCLickListener)

    }

    fun submitList(newData: List<Player>) {

        data = newData

        notifyDataSetChanged()

    }

    init {

        setHasStableIds(true);

    }


    override fun getItemId(position: Int): Long = data[position].idPlayer

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(override val containerView: View,  OnItemCLickListener: OnItemCLickListener?) :

        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {

            itemView.setOnClickListener {

                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {

                    OnItemCLickListener?.invoke(position)

                }

            }

        }

        fun bind(player: Player) {

            lblPlayer.text = player.nombre

            imgPlayer.setImageResource(player.avatar)

        }


    }

}